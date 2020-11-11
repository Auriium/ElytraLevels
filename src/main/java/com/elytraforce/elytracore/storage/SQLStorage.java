package com.elytraforce.elytracore.storage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.config.PluginConfig;
import com.elytraforce.elytracore.player.ElytraPlayer;
import com.elytraforce.elytracore.player.PlayerController;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import dev.magicmq.rappu.Database;

public class SQLStorage {

    private static SQLStorage instance;

    private final Database database;
    private final Gson gson;
    
    private SQLStorage() {
    	
        ConfigurationSection config = PluginConfig.getSqlInfo();
        database = Database.newDatabase()
                .withPluginUsing(Main.get())
                .withUsername(config.getString("username"))
                .withPassword(config.getString("password"))
                .withConnectionInfo(config.getString("host"), config.getInt("port"), config.getString("database"), false)
                .withDefaultProperties()
                .open();

        try {
            database.createTableFromFile("table.sql", Main.class);
        } catch (SQLException | IOException e) {
            Main.get().getLogger().log(Level.SEVERE, "Error when initializing the ElytraLevels SQL table! See this error:");
            e.printStackTrace();
        }

        gson = new Gson();
    }

    public void shutdown() {
        for (ElytraPlayer player : PlayerController.get().getPlayers()) {
            if (player.isInDatabase())
                updatePlayer(player, false);
            else
                insertPlayer(player, false);
        }
        database.close();
    }
    
    public void loadPlayer(UUID uuid) {
    	String sql = "SELECT * FROM `levels_player` ";
    	sql += "WHERE `player_uuid` = ?;";
        database.queryAsync(sql, new Object[]{uuid.toString()}, resultSet -> {
            if (resultSet.next()) {
                PlayerController.get().joinCallback(
                        null,
                        resultSet.getInt("level"),
                        resultSet.getInt("experience"),
                        resultSet.getInt("money"),
                        gson.fromJson(resultSet.getString("unlocked_rewards"), new TypeToken<ArrayList<Integer>>() {}.getType()),
                        true);
            } else {
                throw new SQLException("Player does not exist or has never joined!");
            }
        });
    }

    public void loadPlayer(Player player) {
        String sql = "SELECT * FROM `levels_player` ";
        sql += "WHERE `player_uuid` = ?;";
        database.queryAsync(sql, new Object[]{player.getUniqueId().toString()}, resultSet -> {
            if (resultSet.next()) {
                PlayerController.get().joinCallback(
                        player,
                        resultSet.getInt("level"),
                        resultSet.getInt("experience"),
                        resultSet.getInt("money"),
                        gson.fromJson(resultSet.getString("unlocked_rewards"), new TypeToken<ArrayList<Integer>>() {}.getType()),
                        true);
            } else {
                PlayerController.get().joinCallback(player, 0, 0, 0, new ArrayList<>(), false);
            }
        });
    }

    public void insertPlayer(ElytraPlayer player, boolean async) {
        String sql = "INSERT INTO `levels_player` ";
        sql += "(`player_uuid`, `level`, `experience`, `money`, `unlocked_rewards`) ";
        sql += "VALUES (?, ?, ?, ?, ?);";
        Object[] toSet = new Object[]{
                player.getUUID().toString(),
                player.getLevel(),
                player.getExperience(),
                player.getMoney(),
                gson.toJson(player.getUnlockedRewards())
        };
        executeUpdate(player, sql, toSet, async);
    }

    public void updatePlayer(ElytraPlayer player, boolean async) {
        String sql = "UPDATE `levels_player` SET ";
        sql += "`level` = ?, `experience` = ?, `money` = ?, `unlocked_rewards` = ? ";
        sql += "WHERE `player_uuid` = ?;";
        Object[] toSet = new Object[]{
                player.getLevel(),
                player.getExperience(),
                player.getMoney(),
                gson.toJson(player.getUnlockedRewards()),
                player.getUUID().toString()
        };
        executeUpdate(player, sql, toSet, async);
    }

    private void executeUpdate(ElytraPlayer player, String sql, Object[] toSet, boolean async) {
        if (async)
            database.updateAsync(sql, toSet, integer -> player.setInDatabase(true));
        else {
            try {
                database.update(sql, toSet);
                player.setInDatabase(true);
            } catch (SQLException e) {
                Main.get().getLogger().log(Level.SEVERE, "There was an error when saving a player's data to the Duels SQL table! See this error:");
                e.printStackTrace();
            }
        }
    }

    public static SQLStorage get() {
        if (instance == null) {
            instance = new SQLStorage();
        }
        return instance;
    }
}
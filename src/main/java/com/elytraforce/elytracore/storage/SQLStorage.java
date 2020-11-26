package com.elytraforce.elytracore.storage;

import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.config.Config;
import com.elytraforce.elytracore.player.ElytraPlayer;
import com.elytraforce.elytracore.player.PlayerController;
import com.elytraforce.elytracore.player.redis.enums.ValueEnum;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dev.magicmq.rappu.Database;
import org.bukkit.OfflinePlayer;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.logging.Level;

public class SQLStorage {

    private static SQLStorage instance;

    private final Database database;
    private final Gson gson;
    
    private SQLStorage() {
        Config config = Main.getAConfig();

        database = Database.newDatabase()
                .withPluginUsing(Main.get())
                .withUsername(config.databaseUsername)
                .withPassword(config.databasePassword)
                .withConnectionInfo(config.databaseHost, config.databasePort, config.databaseName, false)
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

    public Boolean playerExists(OfflinePlayer player) {

        //TODO: this is shit and cum someone please fix this
        String sql = "SELECT * FROM `levels_player` ";
        sql += "WHERE `player_uuid` = ?;";

        try {
            return database.query(sql, new Object[]{player.getUniqueId().toString()}).next();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
            return false;
        }
    }

    // // // // // // // // // // // // /// // // // // // // //

    public HashMap<OfflinePlayer, ElytraPlayer> playerCache = new HashMap<>();

    public void removePlayerCached(OfflinePlayer player) {
        playerCache.remove(player);
    }

    public ElytraPlayer loadPlayerCached(OfflinePlayer player) throws SQLException {
        //TODO: this is shit someone needs to fix this who knows more about databases than me

        if (playerCache.containsKey(player)) { return playerCache.get(player); } else {
            String sql = "SELECT * FROM `levels_player` ";
            sql += "WHERE `player_uuid` = ?;";

            ResultSet resultSet = database.query(sql, new Object[]{player.getUniqueId().toString()} );
            ElytraPlayer cachedPlayer;

            if (resultSet.next()) {
                cachedPlayer = new ElytraPlayer(
                        player,
                        resultSet.getInt("level"),
                        resultSet.getInt("experience"),
                        resultSet.getInt("money"),
                        gson.fromJson(resultSet.getString("unlocked_rewards"), new TypeToken<ArrayList<Integer>>() {}.getType()),
                        true);
            } else {
                cachedPlayer = new ElytraPlayer(player, 0, 0, 0, new ArrayList<>(), false);
            }

            playerCache.put(player,cachedPlayer);
            return cachedPlayer;
        }

    }

    /*
        changedPlayer must be the player after all changes occur to it
     */
    public void updatePlayerCached(ElytraPlayer changedPlayer) {
        this.playerCache.put(changedPlayer.asOfflinePlayer(), changedPlayer);
        this.updatePlayer(changedPlayer, true);
    }

    // // // // // // // // // // // // /// // // // // // // //

    public void loadPlayer(OfflinePlayer player) {
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
        sql += "`level` = `level` + ?, `experience` = `experience` + ?, `money` = `money` + ?, `unlocked_rewards` = ? ";
        sql += "WHERE `player_uuid` = ?;";

        int level = player.getCachedDeltaData(ValueEnum.LEVEL);
        int money = player.getCachedDeltaData(ValueEnum.MONEY);
        int exp = player.getCachedDeltaData(ValueEnum.XP);

        Object[] toSet = new Object[]{
                level,
                exp,
                money,
                gson.toJson(player.getUnlockedRewards()),
                player.getUUID().toString()
        };
        executeUpdate(player, sql, toSet, async);
    }

    @Deprecated
    //public void updatePlayer(ElytraPlayer player, boolean async) {
    //    String sql = "UPDATE `levels_player` SET ";
    //    sql += "`level` = ?, `experience` = ?, `money` = ?, `unlocked_rewards` = ? ";
    //    sql += "WHERE `player_uuid` = ?;";
    //
    //    Object[] toSet = new Object[]{
    //            player.getLevel(),
    //            player.getExperience(),
    //            player.getMoney(),
    //            gson.toJson(player.getUnlockedRewards()),
    //            player.getUUID().toString()
    //    };
    //    executeUpdate(player, sql, toSet, async);
    //}

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
        return Objects.requireNonNullElseGet(instance, () -> instance = new SQLStorage());
    }
}
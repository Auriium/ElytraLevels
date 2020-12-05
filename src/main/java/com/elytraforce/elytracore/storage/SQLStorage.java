package com.elytraforce.elytracore.storage;

import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.player.ElytraPlayer;
import com.elytraforce.elytracore.player.PlayerController;
import com.elytraforce.elytracore.player.redis.Delta;
import com.elytraforce.elytracore.player.redis.enums.ValueEnum;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import dev.magicmq.rappu.Database;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.logging.Level;

public class SQLStorage {

    private static SQLStorage instance;

    private final dev.magicmq.rappu.Database database;
    private final Gson gson;
    private final ArrayList<ElytraPlayer> cache;

    public void clearFromQueue(ElytraPlayer player) {
        cache.remove(player);
    }

    private SQLStorage() {

        database = Database.newDatabase()
                .withPluginUsing(Main.get())
                .withUsername(Main.getAConfig().databaseUsername)
                .withPassword(Main.getAConfig().databasePassword)
                .withConnectionInfo(Main.getAConfig().databaseHost, Main.getAConfig().databasePort, Main.getAConfig().databaseName, false)
                .withDefaultProperties()
                .open();

        try {
            database.createTableFromFile("table.sql", Main.class);
        } catch (SQLException | IOException e) {
            Main.get().getLogger().log(Level.SEVERE, "Error when initializing the ElytraLevels SQL table! See this error:");
            e.printStackTrace();
        }

        gson = new Gson();
        cache = new ArrayList<>();
    }

    public CompletableFuture<UUID> getIDFromUsername(String name) {
        CompletableFuture<UUID> future = new CompletableFuture<>();

        if (name == null) { future.complete(null); return future; }

        Player online = Main.get().getServer().getPlayer(name);
        if (online != null) {
            future.complete(online.getUniqueId()); return future;
        }

        String sql = "SELECT id FROM player_login WHERE name = ? ORDER BY time DESC LIMIT 1;";

        database.queryAsync(sql, new Object[]{name}, resultSet -> {
            if (resultSet.next()) {
                try {
                    future.complete(UUID.fromString(resultSet.getString("id")));
                } catch (SQLException e) {
                    e.printStackTrace();
                    future.complete(null);
                }
            } else {
                future.complete(null);
            }
        });

        return future;
    }


    //TODO: use views instead of this autism
    public CompletableFuture<ElytraPlayer> getOrDefaultPlayer(UUID player, boolean cached) {
        CompletableFuture<ElytraPlayer> future = new CompletableFuture<>();

        if (player == null) { future.complete(null);return future; }

        if (cached) {
            for (ElytraPlayer player1 : cache) {
                if (player1.getUUID().equals(player)) {
                    future.complete(player1);
                    return future;
                }
            };
        }

        String sql = "SELECT * FROM `levels_player` ";
        sql += "WHERE `player_uuid` = ?;";

        database.queryAsync(sql, new Object[]{player.toString()},resultSet -> {
            int level;
            int exp;
            int money;
            ArrayList<Integer> rewards;
            boolean inDatabase;

            if (resultSet.next()) {
                level = resultSet.getInt("level");
                exp = resultSet.getInt("experience");
                money = resultSet.getInt("money");
                rewards = gson.fromJson(resultSet.getString("unlocked_rewards"), new TypeToken<ArrayList<Integer>>() {}.getType());
                inDatabase = true;
            } else {
                level = 0;
                exp = 0;
                money = 0;
                rewards = new ArrayList<>();
                inDatabase = false;
            }

            String sql2 = "SELECT * FROM `player_info` ";
            sql2 += "WHERE `id` = ?;";


            database.queryAsync(sql2, new Object[]{player.toString()},rs -> {
                String nick;
                boolean pms;
                boolean discord_out;
                boolean discord_in;
                ChatColor color;

                if (rs.next()) {
                    nick = rs.getString("nickname");
                    pms = rs.getBoolean("pms");
                    discord_out = rs.getBoolean("discord_out");
                    discord_in = rs.getBoolean("discord_in");
                    color = ChatColor.valueOf(rs.getString("chat_color"));
                } else {
                    nick = null;
                    pms = true;
                    discord_in = true;
                    discord_out = true;
                    color = ChatColor.WHITE;
                }

                String sql3 = "SELECT name FROM player_login WHERE id = ? ORDER BY time DESC LIMIT 1;";
                database.queryAsync(sql3,new Object[]{player.toString()},rs1 -> {
                    String name;
                    if (rs.next()) {
                        name = rs.getString("name");
                    } else {
                        name = "";
                    }
                    ElytraPlayer player1 = new ElytraPlayer(player,level,exp,money,rewards,inDatabase,nick,discord_in,discord_out,pms,color,name);
                    if (cached) {
                        cache.add(player1);
                    }

                    future.complete(player1);
                });
            });
        });
        return future;
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

    public void insertPlayer(ElytraPlayer player) {
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

        database.updateAsync(sql, toSet, integer -> player.setInDatabase(true));
    }

    public void updatePlayer(ElytraPlayer player) {
        String sql = "UPDATE `levels_player` SET ";
        sql += "`level` = `level` + ?, `experience` = `experience` + ?, `money` = `money` + ?, `unlocked_rewards` = ? ";
        sql += "WHERE `player_uuid` = ?;";

        int level = player.getCachedDeltaData(ValueEnum.LEVEL);
        int money = player.getCachedDeltaData(ValueEnum.MONEY);
        int exp = player.getCachedDeltaData(ValueEnum.XP);

        ArrayList<Delta> toRemove = new ArrayList<>();
        player.getChanges().forEach(del -> {
            player.adjust(del);
            toRemove.add(del);
        }); player.getChanges().removeAll(toRemove);

        Object[] toSet = new Object[]{
                level,
                exp,
                money,
                gson.toJson(player.getUnlockedRewards()),
                player.getUUID().toString()
        };

        database.updateAsync(sql, toSet, integer -> player.setInDatabase(true));
    }

    public static SQLStorage get() {
        if (instance == null) {
            instance = new SQLStorage();
        }
        return instance;
    }

    public void shutdown() {
        PlayerController.get().getPlayers().forEach(ElytraPlayer::update);
        database.close();
    }
}
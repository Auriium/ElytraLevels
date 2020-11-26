package com.elytraforce.elytracore.player.redis;

import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.config.Config;
import com.elytraforce.elytracore.player.ElytraPlayer;
import com.elytraforce.elytracore.player.PlayerController;
import com.elytraforce.elytracore.player.redis.enums.DeltaEnum;
import com.elytraforce.elytracore.player.redis.enums.ValueEnum;
import com.elytraforce.elytracore.utils.AuriUtils;
import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Objects;
import java.util.UUID;

public class RedisController{

    private static RedisController instance;
    private final PlayerController playerController;
    private final JedisPool pool;

    private final String CHANNEL = "channel_elytralevels";
    private final String COMMAND_CHANNEL = "channel_commands";
    private final String BUNGEE_CHANNEL = "channel_bungee";

    private RedisController() {
        //Initiate connection to pool
        Config config = Main.getAConfig();
        String ip = config.redisIP;
        int port = config.redisPort;
        String password = config.redisPassword;
        if (password == null || password.equals(""))
            pool = new JedisPool(new JedisPoolConfig(), ip, port, 0);
        else
            pool = new JedisPool(new JedisPoolConfig(), ip, port, 0, password);

        new BukkitRunnable() {
            public void run() {
                try (Jedis jedis = pool.getResource()){
                    jedis.subscribe(new RedisListener(),CHANNEL);
                    jedis.subscribe(new CommandListener(),COMMAND_CHANNEL);
                } catch (Exception e) {
                    AuriUtils.logError("Error connecting to redis - " + e.getMessage());
                    AuriUtils.logError("Broken redis pool");
                }
            }
        }.runTaskAsynchronously(Main.get());

        this.playerController = PlayerController.get();
    }

    public String encryptDelta(Delta delta) {
        return delta.getAmount() + ":" + delta.getChange().name() + ":" + delta.getType().name();
    }

    public Delta decryptDelta(String string) {
        String[] parts = string.split(":");
        UUID id = UUID.fromString(parts[0]);
        int amount = Integer.parseInt(parts[1]);
        DeltaEnum change = DeltaEnum.valueOf(parts[2]);
        ValueEnum value = ValueEnum.valueOf(parts[3]);
        return new Delta(id,amount,change,value);
    }

    //THESE MUST ALL BE OF THE SAME TYPE AND UUID (please clean up this shitshow of a utility method)
    public Delta combineDelta(ArrayList<Delta> deltas) {

        int amount = 0;
        DeltaEnum type = DeltaEnum.INCREASE;

        for (Delta d : deltas) {
            if (d.getChange().equals(DeltaEnum.DECREASE)) amount = amount - d.getAmount(); else amount = amount + d.getAmount();
        }

        if (amount < 0) {
            amount = Math.negateExact(amount);
            type = DeltaEnum.DECREASE;
        }

        return new Delta(deltas.get(0).getTarget(),amount,type,deltas.get(0).getType());
    }

    public void broadcastCommand(String command) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try (Jedis jedis = pool.getResource()) {
                    jedis.publish(COMMAND_CHANNEL,command);
                }
            }
        }.runTaskAsynchronously(Main.get());
    }

    public void redisPushToBungee(Delta delta) {
        new BukkitRunnable() {
            @Override
            public void run() {
                try (Jedis jedis = pool.getResource()) {
                        jedis.publish(BUNGEE_CHANNEL,encryptDelta(delta));
                }
            }
        }.runTaskAsynchronously(Main.get());
    }

    public void redisPushChanges(ElytraPlayer player) {
        HashSet<String> updates = new HashSet<>();
        HashSet<Delta> gathered = new HashSet<>();
        player.getChanges().forEach(d -> {
           updates.add(this.encryptDelta(d));
           gathered.add(d);
        }); player.getChanges().removeAll(gathered);

        new BukkitRunnable() {
            @Override
            public void run() {
                try (Jedis jedis = pool.getResource()) {
                    for (String string : updates) {
                        jedis.publish(CHANNEL,string);
                    }
                }
            }
        }.runTaskAsynchronously(Main.get());
    }

    public class RedisListener extends JedisPubSub {
        @Override
        public void onMessage(String channel, final String msg) {
            Delta del = decryptDelta(msg);
            ElytraPlayer target = PlayerController.get().getElytraPlayer(del.getTarget());
            if (target != null) { target.adjust(del); }
        }
    }

    public class CommandListener extends JedisPubSub {
        @Override
        public void onMessage(String channel, final String msg) {
            new BukkitRunnable() {
                public void run() {
                    Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), msg);
                }
            }.runTask(Main.get());
        }
    }

    public static RedisController get() {
        return Objects.requireNonNullElseGet(instance, () -> instance = new RedisController());
    }


}

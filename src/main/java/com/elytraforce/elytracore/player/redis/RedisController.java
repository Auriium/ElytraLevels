package com.elytraforce.elytracore.player.redis;

import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.config.PluginConfig;
import com.elytraforce.elytracore.player.ElytraPlayer;
import com.elytraforce.elytracore.player.PlayerController;
import com.elytraforce.elytracore.player.redis.enums.DeltaEnum;
import com.elytraforce.elytracore.player.redis.enums.ValueEnum;
import com.elytraforce.elytracore.utils.AuriUtils;
import org.bukkit.scheduler.BukkitRunnable;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;
import redis.clients.jedis.JedisPubSub;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.UUID;

public class RedisController{

    private static RedisController instance;
    private PlayerController playerController;
    private JedisPool pool;

    private final String CHANNEL = "channel_elytralevels";

    private RedisController() {
        //Initiate connection to pool
        String ip = PluginConfig.getRedisIP();
        int port = PluginConfig.getRedisPort();
        String password = PluginConfig.getRedisPassword();
        if (password == null || password.equals(""))
            pool = new JedisPool(new JedisPoolConfig(), ip, port, 0);
        else
            pool = new JedisPool(new JedisPoolConfig(), ip, port, 0, password);

        new BukkitRunnable() {
            public void run() {
                Jedis jedis = pool.getResource();
                try {
                    jedis.subscribe(new RedisListener(),CHANNEL);
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
                Jedis jedis = pool.getResource();
                //push updates
                for (String string : updates) {
                    jedis.publish(CHANNEL,string);
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

    public static RedisController get() {
        return Objects.requireNonNullElseGet(instance, () -> instance = new RedisController());
    }


}

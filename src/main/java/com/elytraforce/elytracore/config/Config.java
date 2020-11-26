package com.elytraforce.elytracore.config;

import com.elytraforce.aUtils.config.AConfig;

public class Config extends AConfig {
    @Override
    public String filePosition() {
        return "config.yml";
    }

    @ConfigField(location = "database.username")
    public static String databaseUsername = "owners";
    @ConfigField(location = "database.database")
    public static String databaseName = "minecraft";
    @ConfigField(location = "database.host")
    public static String databaseHost = "172.17.0.1";
    @ConfigField(location = "database.port")
    public static int databasePort = 3306;
    @ConfigField(location = "database.password")
    public static String databasePassword = "cum";
    @ConfigField(location = "database-interval")
    public static long autosaveInterval = 2;
    @ConfigField(location = "is-hub")
    public static boolean isHub = false;
    @ConfigField(location = "is-lobby")
    public static boolean isLobby = true;
    @ConfigField(location = "is-minigame")
    public static boolean isMinigame = false;
    @ConfigField(location = "is-economy-enabled")
    public static boolean isEconomyEnabled = true;
    @ConfigField(location = "replace-essentials")
    public static boolean isReplaceEssentials = false;
    @ConfigField(location = "server-name")
    public static String serverName = "Hub";
    @ConfigField(location = "max-level")
    public static int maxLevel = 300;
    @ConfigField(location = "messages.plugin-prefix")
    public static String pluginPrefix = "&9&lCORE &7>> &f";
    @ConfigField(location = "redis.ip")
    public static String redisIP = "redis-11702.c60.us-west-1-2.ec2.cloud.redislabs.com";
    @ConfigField(location = "redis.port")
    public static int redisPort = 11702;
    @ConfigField(location = "redis.password")
    public static String redisPassword = "q6g56bbvnS8jQRdmHm3Kp65qGteQ7kCH";



}

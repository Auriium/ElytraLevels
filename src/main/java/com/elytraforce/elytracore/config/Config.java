package com.elytraforce.elytracore.config;

import com.elytraforce.aUtils.config.AConfig;

public class Config extends AConfig {
    @Override
    public String filePosition() {
        return "config.yml";
    }

    @ConfigField(location = "database.username")
    public String databaseUsername = "owners";
    @ConfigField(location = "database.database")
    public String databaseName = "minecraft";
    @ConfigField(location = "database.host")
    public String databaseHost = "172.17.0.1";
    @ConfigField(location = "database.port")
    public int databasePort = 3306;
    @ConfigField(location = "database.password")
    public String databasePassword = "cum";
    @ConfigField(location = "database-interval")
    public long autosaveInterval = 2;
    @ConfigField(location = "is-hub")
    public boolean isHub = false;
    @ConfigField(location = "is-lobby")
    public boolean isLobby = true;
    @ConfigField(location = "is-minigame")
    public boolean isMinigame = false;
    @ConfigField(location = "is-economy-enabled")
    public boolean isEconomyEnabled = true;
    @ConfigField(location = "replace-essentials")
    public boolean isReplaceEssentials = false;
    @ConfigField(location = "server-name")
    public String serverName = "Hub";
    @ConfigField(location = "max-level")
    public int maxLevel = 300;
    @ConfigField(location = "messages.plugin-prefix")
    public String pluginPrefix = "&9&lCORE &7>> &f";
    @ConfigField(location = "redis.ip")
    public String redisIP = "redis-11702.c60.us-west-1-2.ec2.cloud.redislabs.com";
    @ConfigField(location = "redis.port")
    public int redisPort = 11702;
    @ConfigField(location = "redis.password")
    public String redisPassword = "q6g56bbvnS8jQRdmHm3Kp65qGteQ7kCH";



}

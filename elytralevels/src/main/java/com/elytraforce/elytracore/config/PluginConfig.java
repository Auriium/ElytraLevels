package com.elytraforce.elytracore.config;

import org.bukkit.ChatColor;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;

import com.elytraforce.elytracore.Main;

public class PluginConfig {

    private static final FileConfiguration config;

    static {
        config = Main.get().getConfig();
    }

    public static ConfigurationSection getSqlInfo() {
        return config.getConfigurationSection("database");
    }

    public static long getAutosaveInterval() {
        return config.getLong("database-interval");
    }
    
    public static String getMessage(String key) {
        return ChatColor.translateAlternateColorCodes('&', config.getString("messages.plugin-prefix") + config.getString("messages." + key));
    }

    public static String getBareMessage(String key) {
        return ChatColor.translateAlternateColorCodes('&', config.getString("messages." + key));
    }
    
    public static boolean isLobby() {
    	return config.getBoolean("is-lobby");
    }
    
    public static String getServerName() {
    	return config.getString("server-name");
    }
    
    public static Integer getMaxLevel() {
    	return config.getInt("max-level");
    }
    
    public static boolean isEconomyEnabled() {
    	return config.getBoolean("is-economy-enabled");
    }

}


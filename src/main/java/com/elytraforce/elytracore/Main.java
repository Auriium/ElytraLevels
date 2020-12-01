package com.elytraforce.elytracore;

import co.aikar.commands.MessageType;
import co.aikar.commands.PaperCommandManager;
import com.elytraforce.aUtils.ALogger;
import com.elytraforce.aUtils.AUtil;
import com.elytraforce.elytracore.bossbar.MatchBarController;
import com.elytraforce.elytracore.commands.*;
import com.elytraforce.elytracore.config.Config;
import com.elytraforce.elytracore.hooks.ElytraEconomy;
import com.elytraforce.elytracore.hooks.ElytraPlaceholder;
import com.elytraforce.elytracore.matchtracker.TrackerController;
import com.elytraforce.elytracore.matchtracker.TrackerListener;
import com.elytraforce.elytracore.player.ElytraPlayer;
import com.elytraforce.elytracore.player.GUIController;
import com.elytraforce.elytracore.player.PlayerController;
import com.elytraforce.elytracore.player.redis.RedisController;
import com.elytraforce.elytracore.rewards.RewardController;
import com.elytraforce.elytracore.storage.SQLStorage;
import com.elytraforce.elytracore.timedrestart.RestartController;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.ipvp.canvas.MenuFunctionListener;


public class Main extends JavaPlugin {

    //cum     good

	public static String pluginName;
	private static Main instance;
	private static Config config;

	public static ElytraEconomy economyImplementation;
	
	public static Main get() {
		return instance;
	}
	public static Config getAConfig() { return config; }

	public static AUtil util;
	
	@Override
	public void onEnable() {
		Main.instance = this;
		Main.pluginName = "[ElytraLevels]";

        AUtil.newUtil().using(this);
        ALogger.logError("test");
		
		/*getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        reloadConfig();*/

        config = new Config();
        config.create().load();

        SQLStorage.get();
        PlayerController.get();
        RewardController.get();
        TrackerController.get();
        GUIController.get();
        MatchBarController.get();
        RestartController.get();
        RestartController.get().onEnable();

        for (Player player : this.getServer().getOnlinePlayers()) {
        	if (PlayerController.get().getElytraPlayer(player) == null) {
                PlayerController.get().playerJoined(player);
            }
        }
        
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {

            for (ElytraPlayer player : PlayerController.get().getPlayers()) {
                SQLStorage.get().playerUpdate(player);
            }
        }, config.autosaveInterval * 60L * 20L, config.autosaveInterval * 60L * 20L);
        
        if (config.isLobby) {
        	Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
                for (ElytraPlayer player : PlayerController.get().getPlayers()) {
                    try {
                        player.asBukkitPlayer().setLevel(player.getLevel());
                        player.asBukkitPlayer().setExp(0);
                    } catch (NullPointerException e) {
                        ALogger.logError("The Main Class has an error regarding setting level and xp (NPE)");
                        ALogger.logError(e.toString());
                        
                    }

                }
            }, 20L, 20L);
        }

        

        PaperCommandManager commandManager = new PaperCommandManager(this);
        commandManager.setFormat(MessageType.ERROR, ChatColor.RED, ChatColor.AQUA);
        commandManager.setFormat(MessageType.SYNTAX, ChatColor.GRAY,ChatColor.AQUA,ChatColor.GRAY);
        commandManager.setFormat(MessageType.INFO,ChatColor.GRAY,ChatColor.AQUA,ChatColor.GRAY);
        commandManager.setFormat(MessageType.HELP, ChatColor.GRAY,ChatColor.AQUA,ChatColor.GRAY);

        commandManager.registerCommand(new LevelCommand(this));
        commandManager.registerCommand(new BalanceCommand(this));
        
        commandManager.registerCommand(new MatchCommand(this));
        commandManager.registerCommand(new SocialCommand(this));
        
        commandManager.registerCommand(new UtilityCommand(this));
        
        Bukkit.getPluginManager().registerEvents(new PluginListener(), this);
        Bukkit.getPluginManager().registerEvents(new TrackerListener(), this);
        Bukkit.getPluginManager().registerEvents(new MenuFunctionListener(), this);
        
        if (config.isEconomyEnabled) {
        	if (getServer().getPluginManager().getPlugin("Vault") != null) {
        		Bukkit.getServer().getServicesManager().register(Economy.class, economyImplementation = new ElytraEconomy(), getServer().getPluginManager().getPlugin("Vault"), ServicePriority.High);
        		commandManager.registerCommand(new EcoCommand(this));
        	}
        }
        
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
          new ElytraPlaceholder(this).register();
        }  

	}
	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);

        SQLStorage.get().shutdown();
        TrackerController.get().shutdown();
        RestartController.get().onDisable();
	}
	
	
	public void onReload() {
		RestartController.get().onReload();
	}
	
}

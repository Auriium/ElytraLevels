package com.elytraforce.elytracore;

import com.elytraforce.elytracore.player.redis.RedisController;
import com.elytraforce.elytracore.utils.AuriUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.ipvp.canvas.MenuFunctionListener;

import com.elytraforce.elytracore.commands.BalanceCommand;
import com.elytraforce.elytracore.bossbar.MatchBarController;
import com.elytraforce.elytracore.commands.*;
import com.elytraforce.elytracore.config.PluginConfig;
import com.elytraforce.elytracore.hooks.ElytraEconomy;
import com.elytraforce.elytracore.hooks.ElytraPlaceholder;
import com.elytraforce.elytracore.matchtracker.TrackerController;
import com.elytraforce.elytracore.matchtracker.TrackerListener;
import com.elytraforce.elytracore.player.ElytraPlayer;
import com.elytraforce.elytracore.player.GUIController;
import com.elytraforce.elytracore.player.PlayerController;
import com.elytraforce.elytracore.rewards.RewardController;
import com.elytraforce.elytracore.storage.SQLStorage;
import com.elytraforce.elytracore.timedrestart.RestartController;

import co.aikar.commands.PaperCommandManager;
import net.milkbowl.vault.economy.Economy;


public class Main extends JavaPlugin {

    //cum     good

	public static String pluginName;
	private static Main instance;

	public static ElytraEconomy economyImplementation;
	
	public static Main get() {
		return instance;
	}
	
	@Override
	public void onEnable() {
		Main.instance = this;
		Main.pluginName = "[ElytraLevels]";
		
		getConfig().options().copyDefaults(true);
        saveDefaultConfig();
        reloadConfig();

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
                RedisController.get().redisPushChanges(player);

                if (player.isInDatabase())
                    SQLStorage.get().updatePlayer(player, true);
                else
                    SQLStorage.get().insertPlayer(player, true);
            }
        }, PluginConfig.getAutosaveInterval() * 60L * 20L, PluginConfig.getAutosaveInterval() * 60L * 20L);
        
        if (PluginConfig.isLobby()) {
        	Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
                for (ElytraPlayer player : PlayerController.get().getPlayers()) {
                    try {
                        player.asBukkitPlayer().setLevel(player.getLevel());
                        player.asBukkitPlayer().setExp(0);
                    } catch (NullPointerException e) {
                        AuriUtils.logError("The Main Class has an error regarding setting level and xp (NPE)");
                        AuriUtils.logError(e.toString());
                    }

                    //PEEPEE POOPOO SHIT CUM AND DIE

                    //consume leper toes

                }
            }, 20L, 20L);
        }
        

        PaperCommandManager commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new LevelCommand(this));
        commandManager.registerCommand(new BalanceCommand(this));
        
        commandManager.registerCommand(new MatchCommand(this));
        commandManager.registerCommand(new SocialCommand(this));
        
        commandManager.registerCommand(new UtilityCommand(this));
        
        Bukkit.getPluginManager().registerEvents(new PluginListener(), this);
        Bukkit.getPluginManager().registerEvents(new TrackerListener(), this);
        Bukkit.getPluginManager().registerEvents(new MenuFunctionListener(), this);
        
        if (PluginConfig.isEconomyEnabled()) {
        	if (getServer().getPluginManager().getPlugin("Vault") != null) {
        		Bukkit.getServer().getServicesManager().register(Economy.class, economyImplementation = new ElytraEconomy(), getServer().getPluginManager().getPlugin("Vault"), ServicePriority.High);
        		commandManager.registerCommand(new EcoCommand(this));
        	}
        }
        
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
          new ElytraPlaceholder(this).register();
        }  
        
        //Now enable bungee incoming
        //RedisAccepter.get();
        //RedisAccepter.get().sendRequest("test");
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

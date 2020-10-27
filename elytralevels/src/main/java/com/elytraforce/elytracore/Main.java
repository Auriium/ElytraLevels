package com.elytraforce.elytracore;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.ServicePriority;
import org.bukkit.plugin.java.JavaPlugin;
import org.ipvp.canvas.MenuFunctionListener;

import com.elytraforce.elytracore.commands.BalanceCommand;
import com.elytraforce.elytracore.commands.EcoCommand;
import com.elytraforce.elytracore.commands.LevelCommand;
import com.elytraforce.elytracore.config.PluginConfig;
import com.elytraforce.elytracore.hooks.ElytraEconomy;
import com.elytraforce.elytracore.hooks.ElytraPlaceholder;
import com.elytraforce.elytracore.matchtracker.TrackerController;
import com.elytraforce.elytracore.matchtracker.TrackerListener;
import com.elytraforce.elytracore.player.ElytraPlayer;
import com.elytraforce.elytracore.player.PlayerController;
import com.elytraforce.elytracore.rewards.RewardController;
import com.elytraforce.elytracore.storage.SQLStorage;
import com.elytraforce.elytracore.utils.MessageController;

import co.aikar.commands.PaperCommandManager;
import me.clip.placeholderapi.PlaceholderAPI;
import net.milkbowl.vault.economy.Economy;

public class Main extends JavaPlugin {

	public static String pluginName;
	private static Main instance;
	
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
        MessageController.get();
        RewardController.get();
        TrackerController.get();
        
        for (Player player : this.getServer().getOnlinePlayers()) {
        	if (PlayerController.get().getLevelPlayer(player) == null) {
                PlayerController.get().playerJoined(player);
            }
        }
        
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (ElytraPlayer player : PlayerController.get().getPlayers()) {
                if (player.isInDatabase())
                    SQLStorage.get().updatePlayer(player, true);
                else
                    SQLStorage.get().insertPlayer(player, true);
            }
        }, PluginConfig.getAutosaveInterval() * 60L * 20L, PluginConfig.getAutosaveInterval() * 60L * 20L);
        
        if (PluginConfig.isLobby()) {
        	Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            	
                for (ElytraPlayer player : PlayerController.get().getPlayers()) {
                	player.asBukkitPlayer().setLevel(player.getLevel());
                }
            }, 20L, 20L);
        }
        

        PaperCommandManager commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new LevelCommand(this));
        commandManager.registerCommand(new BalanceCommand(this));
        commandManager.registerCommand(new EcoCommand(this));
        
        Bukkit.getPluginManager().registerEvents(new PluginListener(), this);
        Bukkit.getPluginManager().registerEvents(new TrackerListener(), this);
        Bukkit.getPluginManager().registerEvents(new MenuFunctionListener(), this);
        
        if (PluginConfig.isEconomyEnabled()) {
        	if (getServer().getPluginManager().getPlugin("Vault") != null) {
        		Bukkit.getServer().getServicesManager().register(Economy.class, new ElytraEconomy(), getServer().getPluginManager().getPlugin("Vault"), ServicePriority.High);
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
	}
	
}

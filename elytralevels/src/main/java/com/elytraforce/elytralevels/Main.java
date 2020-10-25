package com.elytraforce.elytralevels;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.ipvp.canvas.MenuFunctionListener;

import com.elytraforce.elytralevels.commands.LevelCommand;
import com.elytraforce.elytralevels.config.PluginConfig;
import com.elytraforce.elytralevels.player.LevelPlayer;
import com.elytraforce.elytralevels.player.PlayerController;
import com.elytraforce.elytralevels.rewards.RewardController;
import com.elytraforce.elytralevels.storage.SQLStorage;
import com.elytraforce.elytralevels.utils.MessageController;

import co.aikar.commands.PaperCommandManager;

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
        
        for (Player player : this.getServer().getOnlinePlayers()) {
        	PlayerController.get().playerJoined(player);
        }
        
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            for (LevelPlayer player : PlayerController.get().getPlayers()) {
                if (player.isInDatabase())
                    SQLStorage.get().updatePlayer(player, true);
                else
                    SQLStorage.get().insertPlayer(player, true);
            }
        }, PluginConfig.getAutosaveInterval() * 60L * 20L, PluginConfig.getAutosaveInterval() * 60L * 20L);
        
        if (PluginConfig.isLobby()) {
        	Bukkit.getScheduler().scheduleSyncRepeatingTask(this, () -> {
            	
                for (LevelPlayer player : PlayerController.get().getPlayers()) {
                	player.asBukkitPlayer().setLevel(player.getLevel());
                }
            }, 20L, 20L);
        }
        

        PaperCommandManager commandManager = new PaperCommandManager(this);
        commandManager.registerCommand(new LevelCommand(this));
        
        Bukkit.getServer().getPluginManager().registerEvents(new PluginListener(), this);
        Bukkit.getPluginManager().registerEvents(new MenuFunctionListener(), this);
        
	}
	
	@Override
	public void onDisable() {
		Bukkit.getScheduler().cancelTasks(this);

        SQLStorage.get().shutdown();
	}
	
}

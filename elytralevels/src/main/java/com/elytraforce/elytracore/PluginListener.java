package com.elytraforce.elytracore;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import com.elytraforce.elytracore.player.PlayerController;


public class PluginListener implements Listener {
	
	@EventHandler
    public void onJoin(PlayerJoinEvent event) {
        if (PlayerController.get().getLevelPlayer(event.getPlayer()) == null) {
            PlayerController.get().playerJoined(event.getPlayer());
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        if (PlayerController.get().getLevelPlayer(event.getPlayer()) != null) {
            PlayerController.get().playerQuit(PlayerController.get().getLevelPlayer(event.getPlayer()));
        }
    }
	

}

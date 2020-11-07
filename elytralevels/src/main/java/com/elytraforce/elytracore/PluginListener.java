package com.elytraforce.elytracore;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.server.TabCompleteEvent;

import com.elytraforce.elytracore.bossbar.DamageBossbar;
import com.elytraforce.elytracore.player.ElytraPlayer;
import com.elytraforce.elytracore.player.PlayerController;
import com.elytraforce.elytracore.player.UtilityController;


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
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageEvent event) {
    	if (!(event.getEntity() instanceof Player)) { return; }
    	
    	ElytraPlayer player = PlayerController.get().getLevelPlayer((Player) event.getEntity());
    	
    	if (UtilityController.get().isGodMode(player)) {
    		event.setCancelled(true);
    	}
    }
    
    @EventHandler
    public void onHeal(EntityRegainHealthEvent event) {
    	DamageBossbar.get().onHeal(event);
    }
    
    @EventHandler
    public void onDamageEntity(EntityDamageByEntityEvent event) {
    	DamageBossbar.get().onDamage(event);
    }
    
    

}

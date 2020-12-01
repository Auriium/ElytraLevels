package com.elytraforce.elytracore;

import com.elytraforce.elytracore.bossbar.MatchBarController;
import com.elytraforce.elytracore.player.ElytraPlayer;
import com.elytraforce.elytracore.player.PlayerController;
import com.elytraforce.elytracore.player.UtilityController;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class PluginListener implements Listener {
	
	@EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        if (PlayerController.get().getElytraPlayer(event.getPlayer()) == null) {
            PlayerController.get().playerJoined(event.getPlayer());
        }
    }

    @EventHandler
    public void onQuit(final PlayerQuitEvent event) {
        if (PlayerController.get().getElytraPlayer(event.getPlayer()) != null) {
            PlayerController.get().playerQuit(PlayerController.get().getElytraPlayer(event.getPlayer()));
        }
    }
    
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onDamage(EntityDamageEvent event) {
    	if (!(event.getEntity() instanceof Player)) { return; }
    	
    	ElytraPlayer player = PlayerController.get().getElytraPlayer((Player) event.getEntity());
    	
    	if (UtilityController.get().isGodMode(player)) {
    		event.setCancelled(true);
    	}
    }
    
    @EventHandler
    public void onHeal(EntityRegainHealthEvent event) {
    	MatchBarController.get().onHeal(event);
    }
    
    @EventHandler
    public void onDamageEntity(EntityDamageByEntityEvent event) {
    	MatchBarController.get().onDamage(event);
    }
}

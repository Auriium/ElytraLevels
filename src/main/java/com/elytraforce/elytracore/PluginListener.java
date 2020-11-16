package com.elytraforce.elytracore;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import com.elytraforce.elytracore.bossbar.MatchBarController;
import com.elytraforce.elytracore.player.ElytraPlayer;
import com.elytraforce.elytracore.player.PlayerController;
import com.elytraforce.elytracore.player.UtilityController;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Async;


public class PluginListener implements Listener {

    @EventHandler
    public void onAsyncJoin(AsyncPlayerPreLoginEvent event) throws InterruptedException {
        //TODO: another shit
        PlayerController.get().playerJoined(Bukkit.getOfflinePlayer(event.getUniqueId()));
        Thread.sleep(200L);
    }
	
	@EventHandler
    public void onJoin(final PlayerJoinEvent event) {
        //if (PlayerController.get().getElytraPlayer(event.getPlayer()) == null) {
        //    //TODO: shitty fix for mysql loading problems, investigate and patch later when more brainpower
        //    new BukkitRunnable() {
        //        @Override
        //        public void run() {
        //            PlayerController.get().playerJoined(event.getPlayer());
        //        }
        //    }.runTaskLaterAsynchronously(Main.get(), 4L);
        //
        //}
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

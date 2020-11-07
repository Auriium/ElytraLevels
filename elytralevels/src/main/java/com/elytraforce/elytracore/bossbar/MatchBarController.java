package com.elytraforce.elytracore.bossbar;

import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.HashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.entity.PlayerDeathEvent;

import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.matchtracker.TrackablePlayer;
import com.elytraforce.elytracore.matchtracker.TrackerController;
import com.elytraforce.elytracore.player.ElytraPlayer;
import com.elytraforce.elytracore.player.PlayerController;
import com.elytraforce.elytracore.utils.AuriUtils;

public class MatchBarController {
	
	private static MatchBarController instance;
	
	HashMap<LivingEntity, TrackablePlayer> initiatedBars;
	
	private MatchBarController() {
		this.initiatedBars = new HashMap<>();
	}
	
	public void onKill(EntityDamageByEntityEvent event) {
		if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
			
			
			
			
			
		}
	}
	
	public void onHeal(EntityRegainHealthEvent event) {
		if (!(event.getEntity() instanceof LivingEntity)) { return; }
		LivingEntity damaged = (LivingEntity) event.getEntity();
		
		String name;
		
		double hp = (double)Math.round((damaged.getHealth() + event.getAmount()) * 10d) / 10d;
		if (hp < 0) { hp = 0; }
		double maxHp = (double)Math.round(damaged.getMaxHealth() * 10d) / 10d;
		
		//TODO: nickname registrater
		
		if (damaged instanceof Player) {
			name = damaged.getName();
		} else {
			if (damaged.getCustomName() != null) {
				name = damaged.getCustomName();
			} else {
				name = damaged.getName();
			}
			
		}
		
		if (this.initiatedBars.containsKey(damaged)) {
			TrackablePlayer otherPlayer = this.initiatedBars.get(damaged);
			
			TrackerController.get().getMatch(otherPlayer).displayBar(otherPlayer, AuriUtils.colorString(
					"&7" + name + " &f" + hp + "&7/" + "&a" + maxHp),hp/maxHp);
		}
	}
	
	public void onDamage(EntityDamageByEntityEvent event) {
		
		if (!(event.getEntity() instanceof LivingEntity)) { return; }
		
		//p is the damager.
		ElytraPlayer p;
		if (event.getDamager() instanceof Player) {
			p = PlayerController.get().getLevelPlayer((Player)event.getDamager());
		} else if (event.getDamager() instanceof Projectile) {
			Projectile proj = (Projectile) event.getDamager();
			if (!(proj.getShooter() instanceof Player)) { return; }
			p = PlayerController.get().getLevelPlayer(proj.getShooter() instanceof Player ? (Player)proj.getShooter() : null);
		} else { return; }
		
		
		
		if (TrackerController.get().isTracked(p)) {
			
			LivingEntity damaged = (LivingEntity) event.getEntity();
			String name;
			
			//TODO: nickname registrater
			
			if (damaged instanceof Player) {
				name = damaged.getName();
			} else {
				if (damaged.getCustomName() != null) {
					name = damaged.getCustomName();
				} else {
					name = damaged.getName();
				}
				
			}
			double hp = (double)Math.round((damaged.getHealth() - event.getFinalDamage()) * 10d) / 10d;
			if (hp < 0) { hp = 0; }
			double maxHp = (double)Math.round(damaged.getMaxHealth() * 10d) / 10d;
			
			TrackablePlayer player = TrackerController.get().getTrackablePlayer(p);
			TrackerController.get().getMatch(player).displayBar(player, AuriUtils.colorString(
					"&7" + name + " &f" + hp + "&7/" + "&a" + maxHp),hp/maxHp);
			
			
			//this here means that they are already being displayed to some player
			if (this.initiatedBars.containsKey(damaged)) {
				TrackablePlayer otherPlayer = this.initiatedBars.get(damaged);
				
				TrackerController.get().getMatch(otherPlayer).displayBar(otherPlayer, AuriUtils.colorString(
						"&7" + name + " &f" + hp + "&7/" + "&a" + maxHp),hp/maxHp);
			} else {
				//otherwise add them to the displayed list under the damager, and remove them in a bit.
				this.initiatedBars.put(damaged, player);
				Bukkit.getScheduler().scheduleSyncDelayedTask(Main.get(), () -> {
					this.initiatedBars.remove(damaged);
				}, 20L);
			}
		}
	}
	
	public static MatchBarController get() {
		if (instance == null) {
			return instance = new MatchBarController();
		} else {
			return instance;
		}
	}
}

package com.elytraforce.elytracore.player;

import com.elytraforce.elytracore.utils.MessageUtils;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashMap;

public class UtilityController {
	
	private static UtilityController instance;
	
	private final HashMap<ElytraPlayer, Boolean> godModeEnabled;
	
	private UtilityController() {
		this.godModeEnabled = new HashMap<>();
	}
	
	public void teleportHere(ElytraPlayer sender, ElytraPlayer player, ElytraPlayer target) {
		target.asBukkitPlayer().teleport(player.asBukkitPlayer());
		MessageUtils.teleportHere(sender,player,target);
	}
	
	public void teleport(ElytraPlayer sender, ElytraPlayer player, ElytraPlayer target) {
		player.asBukkitPlayer().teleport(target.asBukkitPlayer());
		MessageUtils.teleport(sender,player,target);
	}
	
	public void teleportCoords(ElytraPlayer player, int x, int y, int z) {
		player.asBukkitPlayer().teleport(new Location(player.asBukkitPlayer().getWorld(),x,y,z));
	}
	
	public void kill(ElytraPlayer player, ElytraPlayer target) {
		target.asBukkitPlayer().setHealth(0);
		MessageUtils.killMessage(player, target);
	}
	
	public void setGamemode(ElytraPlayer player, ElytraPlayer target, GameMode mode) {
		if (target.asBukkitPlayer().getGameMode().equals(mode)) {
			MessageUtils.gamemodeMessage(player, target, mode, true);
		} else {
			target.asBukkitPlayer().setGameMode(mode);
			MessageUtils.gamemodeMessage(player, target, mode, false);
		}
	}
	
	public void setFlying(ElytraPlayer player, ElytraPlayer target) {
		if (target.asBukkitPlayer().isFlying()) {
			MessageUtils.flyMessage(player, target, false);
			target.asBukkitPlayer().setAllowFlight(false);
			target.asBukkitPlayer().setFlying(false);
		} else {
			MessageUtils.flyMessage(player, target, true);
			target.asBukkitPlayer().setAllowFlight(true);
			target.asBukkitPlayer().setFlying(true);
		}
	}
	
	public void heal(ElytraPlayer player, ElytraPlayer target) {
		MessageUtils.healMessage(player, target);
		target.asBukkitPlayer().setHealth(player.asBukkitPlayer().getMaxHealth());
		target.asBukkitPlayer().setFoodLevel(20);
	}
	
	public void feed(ElytraPlayer player, ElytraPlayer target) {
		MessageUtils.feedMessage(player, target);
		target.asBukkitPlayer().setFoodLevel(20);
	}
	
	public void invsee(ElytraPlayer player, ElytraPlayer target) {
		target.asBukkitPlayer().openInventory(target.asBukkitPlayer().getInventory());
	}
	
	public void toggleGodMode(ElytraPlayer player, ElytraPlayer target) {
		if (isGodMode(target)) {
			this.godModeEnabled.put(target, false);
			MessageUtils.godMessage(player, target, false);
		} else {
			this.godModeEnabled.put(target, true);
			MessageUtils.godMessage(player, target, true);
		}
	}

	public void speed(ElytraPlayer player, ElytraPlayer target,int amount) {
		Player ptarget = target.asBukkitPlayer();
		MessageUtils.speedMessage(player,target,amount);
		if (ptarget.isFlying()) {
			ptarget.setFlySpeed(amount);
		} else {
			ptarget.setWalkSpeed(amount);
		}
	}
	
	public boolean isGodMode(ElytraPlayer player) {
		if (this.godModeEnabled.containsKey(player)) {
			return this.godModeEnabled.get(player);
		} else {
			return false;
		}
	}
	
	public static UtilityController get() {
		if (instance == null) {
			return instance = new UtilityController();
		} else {
			return instance;
		}
	}

}

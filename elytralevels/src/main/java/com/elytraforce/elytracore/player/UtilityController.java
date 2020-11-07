package com.elytraforce.elytracore.player;

import java.util.HashMap;

import org.bukkit.GameMode;

import com.elytraforce.elytracore.utils.MessageController;

public class UtilityController {
	
	private static UtilityController instance;
	
	private HashMap<ElytraPlayer, Boolean> godModeEnabled;
	
	private UtilityController() {
		this.godModeEnabled = new HashMap<>();
	}
	
	public void setGamemode(ElytraPlayer player, GameMode mode) {
		if (player.asBukkitPlayer().getGameMode().equals(mode)) {
			MessageController.gamemodeMessage(player, mode, true);
		} else {
			player.asBukkitPlayer().setGameMode(mode);
			MessageController.gamemodeMessage(player, mode, false);
		}
	}
	
	public void setFlying(ElytraPlayer player) {
		if (player.asBukkitPlayer().isFlying()) {
			MessageController.flyMessage(player, false);
			player.asBukkitPlayer().setAllowFlight(false);
			player.asBukkitPlayer().setFlying(false);
		} else {
			MessageController.flyMessage(player, true);
			player.asBukkitPlayer().setAllowFlight(true);
			player.asBukkitPlayer().setFlying(true);
		}
	}
	
	public void heal(ElytraPlayer player) {
		MessageController.healMessage(player);
		player.asBukkitPlayer().setHealth(player.asBukkitPlayer().getMaxHealth());
		player.asBukkitPlayer().setFoodLevel(20);
	}
	
	public void feed(ElytraPlayer player) {
		MessageController.feedMessage(player);
		player.asBukkitPlayer().setFoodLevel(20);
	}
	
	public void invsee(ElytraPlayer player, ElytraPlayer target) {
		player.asBukkitPlayer().openInventory(target.asBukkitPlayer().getInventory());
	}
	
	public void toggleGodMode(ElytraPlayer player) {
		if (isGodMode(player)) {
			this.godModeEnabled.put(player, false);
			MessageController.godMessage(player, false);
		} else {
			this.godModeEnabled.put(player, true);
			MessageController.godMessage(player, true);
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

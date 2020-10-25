package com.elytraforce.elytralevels.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.elytraforce.elytralevels.player.LevelPlayer;

public class MessageController {
	
	private static MessageController instance;
	
	
	public static void addXPMessage(LevelPlayer player, int amount, boolean title) {
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&7+ &c" + amount + " Experience!"));
		if (title) {
			//TODO: XP animation
		}
	}
	
	public static void removeXPMessage(LevelPlayer player, int amount, boolean title) {
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&7- &c" + amount + " Experience!"));
		if (title) {
			//TODO: XP animation
		}
	}
	
	public static void removeLevelMessage(LevelPlayer player) {
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&7&m-----------------------------------------------------&r"));
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&r"));
		AuriUtils.sendCenteredMessage(player, AuriUtils.colorString("&7&k&l0&r &c&lLEVEL DOWN! &7&k&l0"));
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&r"));
		AuriUtils.sendCenteredMessage(player, AuriUtils.colorString("&7You are now &c&lLevel &e&l" + player.getLevel() + "&7!"));
		AuriUtils.sendCenteredMessage(player, AuriUtils.colorString("&7" + player.getExperience() + "/" + player.getRequiredXPToNextLevel() + "&cxp&7 to reach &c&lLevel &e&l" + player.getNextLevel()));
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&r"));
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&7&m-----------------------------------------------------&r"));
		
		TitleController.sendTitle(player, AuriUtils.colorString("&c&lLEVEL DOWN!"), AuriUtils.colorString("&7" + (player.getLevel() - 1) + " -> &e" + player.getLevel()));
	}
	
	//TODO: use a config so peopel who dont code can use this
	public static void addLevelMessage(LevelPlayer player) {
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&7&m-----------------------------------------------------&r"));
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&r"));
		AuriUtils.sendCenteredMessage(player, AuriUtils.colorString("&7&k&l0&r &c&lLEVEL UP! &7&k&l0"));
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&r"));
		AuriUtils.sendCenteredMessage(player, AuriUtils.colorString("&7You are now &c&lLevel &e&l" + player.getLevel() + "&7!"));
		AuriUtils.sendCenteredMessage(player, AuriUtils.colorString("&7" + player.getExperience() + "/" + player.getRequiredXPToNextLevel() + "&cxp&7 to reach &c&lLevel &e&l" + player.getNextLevel()));
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&r"));
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&7&m-----------------------------------------------------&r"));
		
		TitleController.sendTitle(player, AuriUtils.colorString("&c&lLEVEL UP!"), AuriUtils.colorString("&7" + (player.getLevel() - 1) + " -> &e" + player.getLevel()));
	}
	
	
	
	public static MessageController get() {
		if (instance == null) {
			return instance = new MessageController();
		} else {
			return instance;
		}
	}
}

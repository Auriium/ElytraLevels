package com.elytraforce.elytracore.utils;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import com.elytraforce.elytracore.player.ElytraPlayer;
import com.elytraforce.elytracore.rewards.RewardController;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class MessageController {
	
	private static MessageController instance;
	
	public static void matchBeginTrackingMessage(ElytraPlayer player, String uuid) {
		AuriUtils.sendCenteredMessage(player, AuriUtils.colorString("&7Beginning tracking manual match with UUID: &6" + uuid + "!"));
	}
	
	public static void matchEndTrackingMessage(ElytraPlayer player, String uuid) {
		AuriUtils.sendCenteredMessage(player, AuriUtils.colorString("&7Ending tracking UUID: &6" + uuid + "!"));
	}
	
	public static void balanceMessage(ElytraPlayer player) {
		AuriUtils.sendCenteredMessage(player, AuriUtils.colorString("&7Your balance: &6" + player.getMoney() + " ⛃"));
	}
	
	public static void setMoneyMessage(ElytraPlayer player, int amount) {
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&7Balance set to &6" + amount + " ⛃"));
	}
	
	public static void addMoneyMessage(ElytraPlayer player, int amount) {
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&7+ &6" + amount + " ⛃"));
	}
	
	public static void removeMoneyMessage(ElytraPlayer player, int amount) {
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&7- &6" + amount + " ⛃"));
	}
	
	public static void addXPMessage(ElytraPlayer player, int amount) {
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&7+ &c" + amount + " ❂"));
	}
	
	public static void removeXPMessage(ElytraPlayer player, int amount) {
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&7- &c" + amount + " ❂"));
	}
	
	public static void removeLevelMessage(ElytraPlayer player) {
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&7&m-----------------------------------------------------&r"));
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&r"));
		AuriUtils.sendCenteredMessage(player, AuriUtils.colorString("&7&k&l0&r &c&lLEVEL DOWN! &7&k&l0"));
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&r"));
		AuriUtils.sendCenteredMessage(player, AuriUtils.colorString("&7You are now &c&lLevel &e&l" + player.getLevel() + "&7!"));
		AuriUtils.sendCenteredMessage(player, AuriUtils.colorString("&7" + player.getExperience() + "/" + player.getRequiredXPToNextLevel() + "&cxp&7 to reach &c&lLevel &e&l" + player.getNextLevel()));
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&r"));
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&7&m-----------------------------------------------------&r"));
	}
	
	//TODO: use a config so peopel who dont code can use this
	//TODO:Fix sloppy shitcode
	public static void addLevelMessage(ElytraPlayer player) {
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&7&m-----------------------------------------------------&r"));
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&r"));
		AuriUtils.sendCenteredMessage(player, AuriUtils.colorString("&7&k&l0&r &c&lLEVEL UP! &7&k&l0"));
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&r"));
		AuriUtils.sendCenteredMessage(player, AuriUtils.colorString("&7You are now &c&lLevel &e&l" + player.getLevel() + "&7!"));
		AuriUtils.sendCenteredMessage(player, AuriUtils.colorString("&7" + player.getExperience() + "/" + player.getRequiredXPToNextLevel() + "&cxp&7 to reach &c&lLevel &e&l" + player.getNextLevel()));
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&r"));
		
		if (RewardController.get().getInteger().contains(player.getLevel())) {
			
			
			TextComponent startMsg = new TextComponent(AuriUtils.centerMessage("UNLOCKED REWARDS"));
			startMsg.setColor(net.md_5.bungee.api.ChatColor.YELLOW);
			startMsg.setBold(true);
			startMsg.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/levels rewards"));
			startMsg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, 
					TextComponent.fromLegacyText(org.bukkit.ChatColor.GRAY + "Click here to view unlocked rewards!")));
			
			player.asBukkitPlayer().spigot().sendMessage(startMsg);
			player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&r"));
		}
		
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&7&m-----------------------------------------------------&r"));
	}
	
	public static void maxLevelMessage(ElytraPlayer player) {
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&7&m-----------------------------------------------------&r"));
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&r"));
		AuriUtils.sendCenteredMessage(player, AuriUtils.colorString("&7&k&l0&r &c&lLEVEL UP! &7&k&l0"));
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&r"));
		AuriUtils.sendCenteredMessage(player, AuriUtils.colorString("&7You are now &c&l MAX LEVEL &e&l(" + player.getLevel() + ")&7!"));
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&r"));
		
		if (RewardController.get().getInteger().contains(player.getLevel())) {
			
			
			TextComponent startMsg = new TextComponent(AuriUtils.centerMessage("UNLOCKED REWARDS"));
			startMsg.setColor(net.md_5.bungee.api.ChatColor.YELLOW);
			startMsg.setBold(true);
			startMsg.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/levels rewards"));
			startMsg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, 
					TextComponent.fromLegacyText(org.bukkit.ChatColor.GRAY + "Click here to view unlocked rewards!")));
			
			player.asBukkitPlayer().spigot().sendMessage(startMsg);
			player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&r"));
		}
		
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&7&m-----------------------------------------------------&r"));
	}
	
	public static MessageController get() {
		if (instance == null) {
			return instance = new MessageController();
		} else {
			return instance;
		}
	}
}

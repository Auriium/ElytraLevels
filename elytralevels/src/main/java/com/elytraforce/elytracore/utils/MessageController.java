package com.elytraforce.elytracore.utils;

import org.bukkit.GameMode;
import com.elytraforce.elytracore.config.PluginConfig;
import com.elytraforce.elytracore.player.ElytraPlayer;
import com.elytraforce.elytracore.rewards.RewardController;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class MessageController {
	
	private static MessageController instance;
	
	public static void godMessage(ElytraPlayer player, Boolean bool) {
		if (bool) {
			AuriUtils.sendMessage(player, PluginConfig.getPrefix() + "&fSet god mode to &aON");
		} else {
			AuriUtils.sendMessage(player, PluginConfig.getPrefix() + "&fSet god mode to &aOFF");
		}
	}
	
	public static void healMessage(ElytraPlayer player) {
		AuriUtils.sendMessage(player, PluginConfig.getPrefix() + "&fYou have been healed!");
	}
	
	public static void feedMessage(ElytraPlayer player) {
		AuriUtils.sendMessage(player, PluginConfig.getPrefix() + "&fYou have been fed!");
	}
	
	public static void flyMessage(ElytraPlayer player, boolean on) {
		if (on) {
			AuriUtils.sendMessage(player, PluginConfig.getPrefix() + "&fSet fly mode to &aON");
		} else {
			AuriUtils.sendMessage(player, PluginConfig.getPrefix() + "&fSet fly mode to &aOFF");
		}
	}
	
	public static void gamemodeMessage(ElytraPlayer player, GameMode mode, boolean already) {
		if (already) {
			AuriUtils.sendMessage(player, PluginConfig.getPrefix() + "&fYou already are in gamemode &a" + mode.name().toUpperCase());
		} else {
			AuriUtils.sendMessage(player, PluginConfig.getPrefix() + "&fSet gamemode to &a" + mode.name().toUpperCase());
		}
	}
	
	public static void discordMessage(ElytraPlayer player) {
		AuriUtils.sendCenteredMessage(player, "&7Our discord is at &9&ldiscord.elytraforce.com!");
	}
	
	public static void websiteMessage(ElytraPlayer player) {
		AuriUtils.sendCenteredMessage(player, "&7Our site is at &9&lelytraforce.com!");
	}
	
	public static void storeMessage(ElytraPlayer player) {
		AuriUtils.sendCenteredMessage(player, "&7Visit our store at &e&lstore.elytraforce.com!");
	}
	
	public static void matchBeginTrackingMessage(ElytraPlayer player, String uuid) {
		AuriUtils.sendCenteredMessage(player, AuriUtils.colorString("&7Beginning tracking manual match with UUID: &6" + uuid + "!"));
	}
	
	public static void matchEndTrackingMessage(ElytraPlayer player, String uuid) {
		AuriUtils.sendCenteredMessage(player, AuriUtils.colorString("&7Ending tracking UUID: &6" + uuid + "!"));
	}
	
	public static void balanceMessage(ElytraPlayer player) {
		AuriUtils.sendCenteredMessage(player, AuriUtils.colorString("&7Your balance: &e" + player.getMoney() + " ⛃"));
	}
	
	public static void setMoneyMessage(ElytraPlayer player, int amount) {
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&7Balance set to &e" + amount + " ⛃"));
	}
	
	public static void addMoneyMessage(ElytraPlayer player, int amount) {
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&7+ &e" + amount + " ⛃"));
	}
	
	public static void removeMoneyMessage(ElytraPlayer player, int amount) {
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&7- &e" + amount + " ⛃"));
	}
	
	public static void addXPMessage(ElytraPlayer player, int amount) {
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&7+ &b" + amount + " ❂"));
	}
	
	public static void removeXPMessage(ElytraPlayer player, int amount) {
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&7- &b" + amount + " ❂"));
	}
	
	public static void removeLevelMessage(ElytraPlayer player) {
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&7&m-----------------------------------------------------&r"));
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&r"));
		AuriUtils.sendCenteredMessage(player, AuriUtils.colorString("&7&k&l0&r &9&lLEVEL DOWN! &7&k&l0"));
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&r"));
		AuriUtils.sendCenteredMessage(player, AuriUtils.colorString("&7You are now &b&lLevel &e&l" + player.getLevel() + "&7!"));
		AuriUtils.sendCenteredMessage(player, AuriUtils.colorString("&b" + player.getExperience() + "&7/" + player.getRequiredXPToNextLevel() + "&b XP&7 to reach &b&lLevel &e&l" + player.getNextLevel()));
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&r"));
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&7&m-----------------------------------------------------&r"));
	}
	
	//TODO: use a config so peopel who dont code can use this
	//TODO:Fix sloppy shitcode
	public static void addLevelMessage(ElytraPlayer player) {
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&7&m-----------------------------------------------------&r"));
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&r"));
		AuriUtils.sendCenteredMessage(player, AuriUtils.colorString("&7&k&l0&r &9&lLEVEL UP! &7&k&l0"));
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&r"));
		AuriUtils.sendCenteredMessage(player, AuriUtils.colorString("&7You are now &b&lLevel &e&l" + player.getLevel() + "&7!"));
		AuriUtils.sendCenteredMessage(player, AuriUtils.colorString("&b" + player.getExperience() + "&7/" + player.getRequiredXPToNextLevel() + "&b XP&7 to reach &b&lLevel &e&l" + player.getNextLevel()));
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
		AuriUtils.sendCenteredMessage(player, AuriUtils.colorString("&7&k&l0&r &9&lLEVEL UP! &7&k&l0"));
		player.asBukkitPlayer().sendMessage(AuriUtils.colorString("&r"));
		AuriUtils.sendCenteredMessage(player, AuriUtils.colorString("&7You are now &b&l MAX LEVEL &e&l(" + player.getLevel() + ")&7!"));
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

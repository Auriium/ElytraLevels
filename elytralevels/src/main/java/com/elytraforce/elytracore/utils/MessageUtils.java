package com.elytraforce.elytracore.utils;

import org.bukkit.GameMode;

import com.elytraforce.elytracore.config.PluginConfig;
import com.elytraforce.elytracore.player.ElytraPlayer;
import com.elytraforce.elytracore.rewards.RewardController;

import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;

public class MessageUtils{
	
	//TODO: read from config(easy to do, but i dont want to have to rewrite the configs so do it later)
	
	public static void godMessage(ElytraPlayer sender, ElytraPlayer target, Boolean bool) {
		if (sender == target) {
			if (bool) {
				AuriUtils.sendMessage(sender, PluginConfig.getPrefix() + "&fSet god mode to &aON");
			} else {
				AuriUtils.sendMessage(sender, PluginConfig.getPrefix() + "&fSet god mode to &aOFF");
			}
		} else {
			if (bool) {
				AuriUtils.sendMessage(target, PluginConfig.getPrefix() + "&fSet god mode to &aON");
				AuriUtils.sendMessage(sender, PluginConfig.getPrefix() + "&fSet god mode to &aON&f for &a" + target.asBukkitPlayer().getName());
			} else {
				AuriUtils.sendMessage(target, PluginConfig.getPrefix() + "&fSet god mode to &aOFF");
				AuriUtils.sendMessage(sender, PluginConfig.getPrefix() + "&fSet god mode to &aOFF&f for &a" + target.asBukkitPlayer().getName());
			}
		}
		
	}
	
	public static void teleport(ElytraPlayer sender, ElytraPlayer target) {
		AuriUtils.sendMessage(sender, PluginConfig.getPrefix() + "&fTeleported you to &a" + target.asBukkitPlayer().getName() + "&f!");
	}
	
	public static void teleportHere(ElytraPlayer sender, ElytraPlayer target) { 
		AuriUtils.sendMessage(sender, PluginConfig.getPrefix() + "&fTeleported &a" + target.asBukkitPlayer().getName() + " &fto you!");
	}
	
	public static void killMessage(ElytraPlayer sender, ElytraPlayer target) {
		if (sender == target) {
			AuriUtils.sendMessage(sender, PluginConfig.getPrefix() + "&fYou suicided!");
		} else {
			AuriUtils.sendMessage(sender, PluginConfig.getPrefix() + "&a" + target.asBukkitPlayer().getName() + "&f has been killed!");
		}
		
	}
	
	public static void healMessage(ElytraPlayer sender, ElytraPlayer target) {
		if (sender == target) {
			AuriUtils.sendMessage(sender, PluginConfig.getPrefix() + "&fYou have been healed!");
		} else {
			AuriUtils.sendMessage(target, PluginConfig.getPrefix() + "&fYou have been healed!");
			AuriUtils.sendMessage(sender, PluginConfig.getPrefix() + "&a" + target.asBukkitPlayer().getName() + "&f has been healed!");
		}
		
	}
	
	public static void feedMessage(ElytraPlayer sender, ElytraPlayer target) {
		if (sender == target) {
			AuriUtils.sendMessage(sender, PluginConfig.getPrefix() + "&fYou have been fed!");
		} else {
			AuriUtils.sendMessage(target, PluginConfig.getPrefix() + "&fYou have been fed!");
			AuriUtils.sendMessage(sender, PluginConfig.getPrefix() + "&a" + target.asBukkitPlayer().getName() + "&f has been fed!");
		}
	}
	
	public static void flyMessage(ElytraPlayer sender, ElytraPlayer target, boolean on) {
		if (sender == target) {
			if (on) {
				AuriUtils.sendMessage(sender, PluginConfig.getPrefix() + "&fSet fly mode to &aON");
			} else {
				AuriUtils.sendMessage(sender, PluginConfig.getPrefix() + "&fSet fly mode to &aOFF");
			}
		} else {
			if (on) {
				AuriUtils.sendMessage(target, PluginConfig.getPrefix() + "&fSet fly mode to &aON");
				AuriUtils.sendMessage(sender, PluginConfig.getPrefix() + "&fSet fly mode to &aON&f for &a" + target.getName());
			} else {
				AuriUtils.sendMessage(target, PluginConfig.getPrefix() + "&fSet fly mode to &aOFF");
				AuriUtils.sendMessage(sender, PluginConfig.getPrefix() + "&fSet fly mode to &aOFF&f for &a" + target.getName());
			}
		}
		
	}
	
	public static void gamemodeMessage(ElytraPlayer player, ElytraPlayer target, GameMode mode, boolean already) {
		if (player == target) {
			if (already) {
				AuriUtils.sendMessage(player, PluginConfig.getPrefix() + "&fYou already are in gamemode &a" + mode.name().toUpperCase());
			} else {
				AuriUtils.sendMessage(player, PluginConfig.getPrefix() + "&fSet gamemode to &a" + mode.name().toUpperCase());
			}
		} else {
			if (already) {
				AuriUtils.sendMessage(player, PluginConfig.getPrefix() + "&fTarget is already in gamemode &a" + mode.name().toUpperCase());
				AuriUtils.sendMessage(target, PluginConfig.getPrefix() + "&fYou already are in gamemode &a" + mode.name().toUpperCase());
			} else {
				AuriUtils.sendMessage(player, PluginConfig.getPrefix() + "&fSet target to gamemode &a" + mode.name().toUpperCase());
				AuriUtils.sendMessage(target, PluginConfig.getPrefix() + "&fSet gamemode to &a" + mode.name().toUpperCase());
			}
		}
		
	}
	
	public static void invalidTarget(ElytraPlayer player) {
		AuriUtils.sendMessage(player, PluginConfig.getPrefix() + "&cInvalid Target! Please choose an actual player!");
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
	
}

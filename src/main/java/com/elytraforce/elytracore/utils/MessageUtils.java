package com.elytraforce.elytracore.utils;

import com.elytraforce.aUtils.chat.AChat;
import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.config.Config;
import com.elytraforce.elytracore.player.ElytraPlayer;
import com.elytraforce.elytracore.rewards.RewardController;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ClickEvent.Action;
import net.md_5.bungee.api.chat.HoverEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.GameMode;

import java.text.NumberFormat;
import java.util.Locale;

public class MessageUtils{

	private static final Config config;

	static {
		config = Main.getAConfig();
	}
	
	//TODO: read from config(easy to do, but i dont want to have to rewrite the configs so do it later)

	public static String formatNumber(int number) {
		return AChat.colorString(NumberFormat.getNumberInstance(Locale.US).format(number));
	}
	
	public static void godMessage(ElytraPlayer sender, ElytraPlayer target, Boolean bool) {
		if (sender == target) {
			if (bool) {
				sender.asBukkitPlayer().sendMessage(config.pluginPrefix + "&fSet god mode to &aON");
			} else {
				sender.asBukkitPlayer().sendMessage(config.pluginPrefix + "&fSet god mode to &aOFF");
			}
		} else {
			if (bool) {
				target.asBukkitPlayer().sendMessage(config.pluginPrefix + "&fSet god mode to &aON");
				sender.asBukkitPlayer().sendMessage(config.pluginPrefix + "&fSet god mode to &aON&f for &a" + target.asBukkitPlayer().getName());
			} else {
				target.asBukkitPlayer().sendMessage(config.pluginPrefix + "&fSet god mode to &aOFF");
				sender.asBukkitPlayer().sendMessage(config.pluginPrefix + "&fSet god mode to &aOFF&f for &a" + target.asBukkitPlayer().getName());
			}
		}
		
	}
	
	public static void teleport(ElytraPlayer sender, ElytraPlayer target) {
		sender.asBukkitPlayer().sendMessage(config.pluginPrefix + "&fTeleported you to &a" + target.asBukkitPlayer().getName() + "&f!");
	}
	
	public static void teleportHere(ElytraPlayer sender, ElytraPlayer target) {
		sender.asBukkitPlayer().sendMessage(config.pluginPrefix + "&fTeleported &a" + target.asBukkitPlayer().getName() + " &fto you!");
	}
	
	public static void killMessage(ElytraPlayer sender, ElytraPlayer target) {
		if (sender == target) {
			sender.asBukkitPlayer().sendMessage(config.pluginPrefix + "&fYou suicided!");
		} else {
			sender.asBukkitPlayer().sendMessage(config.pluginPrefix + "&a" + target.asBukkitPlayer().getName() + "&f has been killed!");
		}
		
	}

	public static void speedMessage(ElytraPlayer sender, ElytraPlayer target, int amount) {
		if (sender == target) {
			sender.asBukkitPlayer().sendMessage(config.pluginPrefix + "&fSet your speed to &a" + amount);
		} else {
			target.asBukkitPlayer().sendMessage(config.pluginPrefix + "&fSet your speed to &a" + amount);
			sender.asBukkitPlayer().sendMessage(config.pluginPrefix + "&a" + target.asBukkitPlayer().getName() + "&f's speed has been set to &a" + amount);
		}

	}
	
	public static void healMessage(ElytraPlayer sender, ElytraPlayer target) {
		if (sender == target) {
			sender.asBukkitPlayer().sendMessage(config.pluginPrefix + "&fYou have been healed!");
		} else {
			target.asBukkitPlayer().sendMessage(config.pluginPrefix + "&fYou have been healed!");
			sender.asBukkitPlayer().sendMessage(config.pluginPrefix + "&a" + target.asBukkitPlayer().getName() + "&f has been healed!");
		}
		
	}
	
	public static void feedMessage(ElytraPlayer sender, ElytraPlayer target) {
		if (sender == target) {
			sender.asBukkitPlayer().sendMessage(config.pluginPrefix + "&fYou have been fed!");
		} else {
			target.asBukkitPlayer().sendMessage(config.pluginPrefix + "&fYou have been fed!");
			sender.asBukkitPlayer().sendMessage(config.pluginPrefix + "&a" + target.asBukkitPlayer().getName() + "&f has been fed!");
		}
	}
	
	public static void flyMessage(ElytraPlayer sender, ElytraPlayer target, boolean on) {
		if (sender == target) {
			if (on) {
				sender.asBukkitPlayer().sendMessage(config.pluginPrefix + "&fSet fly mode to &aON");
			} else {
				sender.asBukkitPlayer().sendMessage(config.pluginPrefix + "&fSet fly mode to &aOFF");
			}
		} else {
			if (on) {
				target.asBukkitPlayer().sendMessage(config.pluginPrefix + "&fSet fly mode to &aON");
				sender.asBukkitPlayer().sendMessage(config.pluginPrefix + "&fSet fly mode to &aON&f for &a" + target.getName());
			} else {
				target.asBukkitPlayer().sendMessage(config.pluginPrefix + "&fSet fly mode to &aOFF");
				sender.asBukkitPlayer().sendMessage(config.pluginPrefix + "&fSet fly mode to &aOFF&f for &a" + target.getName());
			}
		}
		
	}
	
	public static void gamemodeMessage(ElytraPlayer player, ElytraPlayer target, GameMode mode, boolean already) {
		if (player == target) {
			if (already) {
				player.asBukkitPlayer().sendMessage(config.pluginPrefix + "&fYou already are in gamemode &a" + mode.name().toUpperCase());
			} else {
				player.asBukkitPlayer().sendMessage(config.pluginPrefix + "&fSet gamemode to &a" + mode.name().toUpperCase());
			}
		} else {
			if (already) {
				player.asBukkitPlayer().sendMessage(config.pluginPrefix + "&fTarget is already in gamemode &a" + mode.name().toUpperCase());
				target.asBukkitPlayer().sendMessage(config.pluginPrefix + "&fYou already are in gamemode &a" + mode.name().toUpperCase());
			} else {
				player.asBukkitPlayer().sendMessage(config.pluginPrefix + "&fSet target to gamemode &a" + mode.name().toUpperCase());
				target.asBukkitPlayer().sendMessage(config.pluginPrefix + "&fSet gamemode to &a" + mode.name().toUpperCase());
			}
		}

	}

	public static void invalidTarget(ElytraPlayer player) {
		player.asBukkitPlayer().sendMessage(config.pluginPrefix + "&cInvalid Target! Please choose an actual player!");
	}

	public static void discordMessage(ElytraPlayer player) {
		player.asBukkitPlayer().sendMessage(AChat.centerMessage("&7Our discord is at &9&ldiscord.elytraforce.com!"));
	}

	public static void websiteMessage(ElytraPlayer player) {
		player.asBukkitPlayer().sendMessage(AChat.centerMessage("&7Our site is at &9&lelytraforce.com!"));
	}

	public static void storeMessage(ElytraPlayer player) {
		player.asBukkitPlayer().sendMessage(AChat.centerMessage("&7Visit our store at &e&lstore.elytraforce.com!"));
	}

	public static void matchBeginTrackingMessage(ElytraPlayer player, String uuid) {
		player.asBukkitPlayer().sendMessage(AChat.centerMessage(AChat.colorString("&7Beginning tracking manual match with UUID: &6" + uuid + "!")));
	}

	public static void matchEndTrackingMessage(ElytraPlayer player, String uuid) {
		player.asBukkitPlayer().sendMessage(AChat.centerMessage(AChat.colorString("&7Ending tracking UUID: &6" + uuid + "!")));
	}

	public static void balanceMessage(ElytraPlayer player) {

		String test = AChat.centerMessage(AChat.colorString("&e" + player.getName() + "&7's balance: &e" + formatNumber(player.getMoney()) + " ⛃"));
		player.asBukkitPlayer().sendMessage(test);
	}

	public static void setMoneyMessage(ElytraPlayer player, int amount) {
		player.asBukkitPlayer().sendMessage(AChat.colorString("&7Balance set to &e" + formatNumber(amount) + " ⛃"));
	}

	public static void addMoneyMessage(ElytraPlayer player, int amount) {
		player.asBukkitPlayer().sendMessage(AChat.colorString("&7+ &e" + formatNumber(amount) + " ⛃"));
	}

	public static void removeMoneyMessage(ElytraPlayer player, int amount) {
		player.asBukkitPlayer().sendMessage(AChat.colorString("&7- &e" + formatNumber(amount) + " ⛃"));
	}

	public static void addXPMessage(ElytraPlayer player, int amount) {
		player.asBukkitPlayer().sendMessage(AChat.colorString("&7+ &b" + formatNumber(amount) + " ❂"));
	}

	public static void removeXPMessage(ElytraPlayer player, int amount) {
		player.asBukkitPlayer().sendMessage(AChat.colorString("&7- &b" + formatNumber(amount) + " ❂"));
	}

	public static void removeLevelMessage(ElytraPlayer player) {
		player.asBukkitPlayer().sendMessage(AChat.colorString("&7&m-----------------------------------------------------&r"));
		player.asBukkitPlayer().sendMessage(AChat.colorString("&r"));
		player.asBukkitPlayer().sendMessage(AChat.centerMessage("&7&k&l0&r &9&lLEVEL DOWN! &7&k&l0"));
		player.asBukkitPlayer().sendMessage(AChat.colorString("&r"));
		player.asBukkitPlayer().sendMessage(AChat.centerMessage("&7You are now &b&lLevel &e&l" + player.getLevel() + "&7!"));
		player.asBukkitPlayer().sendMessage(AChat.centerMessage("&b" + formatNumber(player.getExperience()) + "&7/" + formatNumber(player.getRequiredXPToNextLevel()) + "&b XP&7 to reach &b&lLevel &e&l" + player.getNextLevel()));
		player.asBukkitPlayer().sendMessage(AChat.colorString("&r"));
		player.asBukkitPlayer().sendMessage(AChat.colorString("&7&m-----------------------------------------------------&r"));
	}

	//TODO: use a config so peopel who dont code can use this
	//TODO:Fix sloppy shitcode
	public static void addLevelMessage(ElytraPlayer player) {
		player.asBukkitPlayer().sendMessage(AChat.colorString("&7&m-----------------------------------------------------&r"));
		player.asBukkitPlayer().sendMessage(AChat.colorString("&r"));
		player.asBukkitPlayer().sendMessage(AChat.centerMessage("&7&k&l0&r &9&lLEVEL UP! &7&k&l0"));
		player.asBukkitPlayer().sendMessage(AChat.colorString("&r"));
		player.asBukkitPlayer().sendMessage(AChat.centerMessage("&7You are now &b&lLevel &e&l" + player.getLevel() + "&7!"));
		player.asBukkitPlayer().sendMessage(AChat.centerMessage("&b" + formatNumber(player.getExperience()) + "&7/" + formatNumber(player.getRequiredXPToNextLevel()) + "&b XP&7 to reach &b&lLevel &e&l" + player.getNextLevel()));
		player.asBukkitPlayer().sendMessage(AChat.colorString("&r"));

		if (RewardController.get().getInteger().contains(player.getLevel())) {


			TextComponent startMsg = new TextComponent(AChat.centerMessage("UNLOCKED REWARDS"));
			startMsg.setColor(net.md_5.bungee.api.ChatColor.YELLOW);
			startMsg.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/levels rewards"));
			startMsg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT,
					TextComponent.fromLegacyText(org.bukkit.ChatColor.GRAY + "Click here to view unlocked rewards!")));

			player.asBukkitPlayer().spigot().sendMessage(startMsg);
			player.asBukkitPlayer().sendMessage(AChat.colorString("&r"));
		}

		player.asBukkitPlayer().sendMessage(AChat.colorString("&7&m-----------------------------------------------------&r"));
	}

	public static void maxLevelMessage(ElytraPlayer player) {
		player.asBukkitPlayer().sendMessage(AChat.colorString("&7&m-----------------------------------------------------&r"));
		player.asBukkitPlayer().sendMessage(AChat.colorString("&r"));
		player.asBukkitPlayer().sendMessage(AChat.centerMessage("&7&k&l0&r &9&lLEVEL UP! &7&k&l0"));
		player.asBukkitPlayer().sendMessage(AChat.colorString("&r"));
		player.asBukkitPlayer().sendMessage(AChat.centerMessage("&7You are now &b&l MAX LEVEL &e&l(" + player.getLevel() + ")&7!"));
		player.asBukkitPlayer().sendMessage(AChat.colorString("&r"));
		
		if (RewardController.get().getInteger().contains(player.getLevel())) {
			
			
			TextComponent startMsg = new TextComponent(AChat.centerMessage("UNLOCKED REWARDS"));
			startMsg.setColor(net.md_5.bungee.api.ChatColor.YELLOW);
			startMsg.setClickEvent(new ClickEvent(Action.RUN_COMMAND, "/levels rewards"));
			startMsg.setHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, 
					TextComponent.fromLegacyText(org.bukkit.ChatColor.GRAY + "Click here to view unlocked rewards!")));
			
			player.asBukkitPlayer().spigot().sendMessage(startMsg);
			player.asBukkitPlayer().sendMessage(AChat.colorString("&r"));
		}
		
		player.asBukkitPlayer().sendMessage(AChat.colorString("&7&m-----------------------------------------------------&r"));
	}
	
}

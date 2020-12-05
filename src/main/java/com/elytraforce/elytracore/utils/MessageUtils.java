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
import org.bukkit.entity.Player;

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
		String string = AChat.colorString(String.format(config.pluginPrefix +"&fSet god mode to &a%s &f for %s",bool.toString().toUpperCase(),target.getName()));
		sender.asBukkitPlayer().sendMessage(string);
	}
	
	public static void teleport(ElytraPlayer sender, ElytraPlayer target1, ElytraPlayer target2) {
		String string = AChat.colorString(String.format(config.pluginPrefix +"&fTeleported &a%s &f to &a%s",target1.getName(),target2.getName()));
		sender.asBukkitPlayer().sendMessage(string);
	}
	
	public static void teleportHere(ElytraPlayer sender, ElytraPlayer target1, ElytraPlayer target2) {
		String string = AChat.colorString(String.format(config.pluginPrefix +"&fTeleported &a%s &f to &a%s",target2.getName(),target1.getName()));
		sender.asBukkitPlayer().sendMessage(string);
	}
	
	public static void killMessage(ElytraPlayer sender, ElytraPlayer target) {
		String string = AChat.colorString(String.format(config.pluginPrefix +"&fKilled &a%s",target.getName()));
		sender.asBukkitPlayer().sendMessage(string);
		
	}

	public static void speedMessage(ElytraPlayer sender, ElytraPlayer target, int amount) {
		String string = AChat.colorString(String.format(config.pluginPrefix +"&fSet speed of &a%s&f to &a%s",target.getName(),amount));
		sender.asBukkitPlayer().sendMessage(string);
	}
	
	public static void healMessage(ElytraPlayer sender, ElytraPlayer target) {
		String string = AChat.colorString(String.format(config.pluginPrefix +"&fHealed &a%s",target.getName()));
		sender.asBukkitPlayer().sendMessage(string);
	}
	
	public static void feedMessage(ElytraPlayer sender, ElytraPlayer target) {
		String string = AChat.colorString(String.format(config.pluginPrefix +"&fFed &a%s",target.getName()));
		sender.asBukkitPlayer().sendMessage(string);
	}
	
	public static void flyMessage(ElytraPlayer sender, ElytraPlayer target, Boolean on) {
		String string = AChat.colorString(String.format(config.pluginPrefix +"&fSet fly to &a%s &ffor %s",on.toString().toUpperCase(),target.getName()));
		sender.asBukkitPlayer().sendMessage(string);
	}
	
	public static void gamemodeMessage(ElytraPlayer sender, ElytraPlayer target, GameMode mode, Boolean already) {
		String string;
		if (already) {
			string = AChat.colorString(String.format(config.pluginPrefix +"&fSet &a%s's&f gamemode to &a%s",target.getName(),mode.name().toUpperCase()));
		} else {
			string = AChat.colorString(String.format(config.pluginPrefix +"&a%s's&f gamemode is already &a%s",target.getName(),mode.name().toUpperCase()));
		}

		sender.asBukkitPlayer().sendMessage(string);

	}

	public static void invalidTarget(Player player, String chosen) {
		String string = AChat.colorString(String.format(config.pluginPrefix +"&c%s is an invalid target!",chosen));
		player.sendMessage(string);
	}

	public static void invalidTarget(ElytraPlayer player, String chosen) {
		String string = AChat.colorString(String.format(config.pluginPrefix +"&c%s is an invalid target!",chosen));
		player.asBukkitPlayer().sendMessage(string);
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

	public static void balanceMessage(ElytraPlayer sender, ElytraPlayer target) {

		String test = AChat.centerMessage(AChat.colorString("&e" + target.getName() + "&7's balance: &e" + formatNumber(target.getMoney()) + " ⛃"));
		sender.asBukkitPlayer().sendMessage(test);
	}

	public static void setMoneyMessage(ElytraPlayer sender, ElytraPlayer target, int amount) {
		String string = AChat.colorString(String.format("&7Balance set to &e%s ⛃",target.getMoney()));
		sender.asBukkitPlayer().sendMessage(string);
		target.asBukkitPlayer().sendMessage(string);
	}

	public static void addMoneyMessage(ElytraPlayer sender, ElytraPlayer target, int amount) {
		String string = AChat.colorString(String.format("&7+ &e%s ⛃",amount));
		String string2 = AChat.colorString(String.format("&7(%s)+ &e%s ⛃",target.getName(),amount));
		sender.asBukkitPlayer().sendMessage(string2);
		target.asBukkitPlayer().sendMessage(string);
	}

	public static void removeMoneyMessage(ElytraPlayer sender, ElytraPlayer target, int amount) {
		String string = AChat.colorString(String.format("&7- &e%s ⛃",amount));
		String string2 = AChat.colorString(String.format("&7(%s)- &e%s ⛃",target.getName(),amount));
		sender.asBukkitPlayer().sendMessage(string2);
		target.asBukkitPlayer().sendMessage(string);
	}

	public static void addXPMessage(ElytraPlayer sender, ElytraPlayer target, int amount) {
		String string = AChat.colorString(String.format("&7+ &b%s ❂",amount));
		String string2 = AChat.colorString(String.format("&7(%s)+ &b%s ❂",target.getName(),amount));
		sender.asBukkitPlayer().sendMessage(string2);
		target.asBukkitPlayer().sendMessage(string);
	}

	public static void removeXPMessage(ElytraPlayer sender, ElytraPlayer target, int amount) {
		String string = AChat.colorString(String.format("&7- &b%s ❂",amount));
		String string2 = AChat.colorString(String.format("&7(%s)- &b%s ❂",target.getName(),amount));
		sender.asBukkitPlayer().sendMessage(string2);
		target.asBukkitPlayer().sendMessage(string);
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

package com.elytraforce.elytralevels.utils;

import org.bukkit.entity.Player;

import com.elytraforce.elytralevels.player.LevelPlayer;

public class TitleController {
	
	public static void sendTitle(LevelPlayer player, String top, String bottom) {
		sendTitle(player.asBukkitPlayer(), top, bottom);
	}
	
	public static void sendTitle(Player player, String top, String bottom) {
		player.sendTitle(AuriUtils.colorString(top), AuriUtils.colorString(bottom), 10, 10, 10);
	}
	
	

}

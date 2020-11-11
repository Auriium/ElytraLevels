package com.elytraforce.elytracore.utils;

import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.player.ElytraPlayer;

public class TitleUtils {
	
	public static void sendTitle(ElytraPlayer player, String top, String bottom) {
		if (player.getDisplayedTitleTask() != null) {
			player.getDisplayedTitleTask().cancel();
		}
		player.asBukkitPlayer().sendTitle(AuriUtils.colorString(top), AuriUtils.colorString(bottom), 10, 10, 10);
	}
	
	public static void sendAnimatedSideTitle(ElytraPlayer player, String top, String bottom, int length) {
		
		if (player.getDisplayedTitleTask() == null) {
			BukkitTask task = new BukkitRunnable() {
				private int i = length;
				@Override
				public void run() {
					if (i == 0) { 
						this.cancel(); 
						player.setDisplayedTitleTask(null);
					}
					StringBuilder sb = new StringBuilder();
					for (int j = 0; j < i; ++j) {
			        	sb.append("      ");
			        }
					StringBuilder sb2 = sb;
					
					player.asBukkitPlayer().sendTitle(AuriUtils.colorString(sb.append(top).toString()), AuriUtils.colorString(sb2.append(bottom).toString()), 0, 10, 10);
					
					i--;
				}
				
			}.runTaskTimer(Main.get(), 0L, 1L);
			player.setDisplayedTitleTask(task);
		} else {
			player.getDisplayedTitleTask().cancel();
			BukkitTask task = new BukkitRunnable() {
				private int i = length;
				@Override
				public void run() {
					if (i == 0) { 
						this.cancel(); 
						player.setDisplayedTitleTask(null);
					}
					StringBuilder sb = new StringBuilder();
					for (int j = 0; j < i; ++j) {
			        	sb.append("      ");
			        }
					StringBuilder sb2 = sb;
					
					player.asBukkitPlayer().sendTitle(AuriUtils.colorString(sb.append(top).toString()), AuriUtils.colorString(sb2.append(bottom).toString()), 0, 10, 10);
					
					i--;
				}
				
			}.runTaskTimer(Main.get(), 0L, 1L);
			player.setDisplayedTitleTask(task);
		}
		
		
	}
	
	

}

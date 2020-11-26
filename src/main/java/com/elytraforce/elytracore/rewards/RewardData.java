package com.elytraforce.elytracore.rewards;

import java.util.List;

import com.elytraforce.elytracore.Main;
import org.bukkit.Bukkit;
import com.elytraforce.elytracore.player.ElytraPlayer;
import com.elytraforce.elytracore.utils.AuriUtils;

public class RewardData {
	
	private final List<String> description;
	private final List<String> commands;
	private final List<String> servers;
	private final String name;
	private final Integer level;
	
	public List<String> getDescription() { return this.description; }
	public List<String> getCommands() { return this.commands; } 
	public List<String> getServers() { return this.servers; }
	public String getName() { return this.name; }
	public String getNameFormatted() { return this.name.replaceAll("%level%", AuriUtils.colorString("&e&l") + this.level.toString()); }
	public int getLevel() { return this.level; }
	
	public RewardData(Integer level, String name, List<String> description, List<String> servers, List<String> commands) {
		this.level = level;
		this.name = name;
		this.description = description;
		this.servers = servers;
		this.commands = commands;
	}
	
	public boolean isCorrectServer() {
		if (this.servers.contains(Main.getAConfig().serverName)) {
			return true;
		} else return this.servers.contains("all");
	}
	
	public boolean canUnlock(ElytraPlayer player) {
		return player.getLevel() >= level;
	}
	
	public boolean hasUnlocked(ElytraPlayer player) {
		return player.getUnlockedRewards().contains(this.level);
	}
	
	public void unlock(ElytraPlayer player) {
		List<Integer> reward = player.getUnlockedRewards();
		if (reward.contains(this.level)) {
			return;
		}
		reward.add(this.level);
		player.setUnlockedRewards(reward);
		
		for (String string : this.commands) {
			String command = string.replaceAll("%player%", player.getName());
			Bukkit.dispatchCommand(Bukkit.getServer().getConsoleSender(), command);
		}
		
	}
	
}

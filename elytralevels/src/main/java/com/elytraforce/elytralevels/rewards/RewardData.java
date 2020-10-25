package com.elytraforce.elytralevels.rewards;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.ConsoleCommandSender;

import com.elytraforce.elytralevels.player.LevelPlayer;
import com.elytraforce.elytralevels.utils.AuriUtils;

public class RewardData {
	
	private List<String> description;
	private List<String> commands;
	private List<String> servers;
	private String name;
	private Integer level;
	
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
	
	public boolean canUnlock(LevelPlayer player) {
		if (player.getLevel() >= level) {
			return true;
		} else {
			return false;
		}
	}
	
	public boolean hasUnlocked(LevelPlayer player) {
		if (player.getUnlockedRewards().contains(this.level)) { return true; }
		
		return false;
	}
	
	public void unlock(LevelPlayer player) {
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

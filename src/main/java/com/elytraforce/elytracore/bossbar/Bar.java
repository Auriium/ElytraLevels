package com.elytraforce.elytracore.bossbar;

import org.bukkit.boss.BossBar;
import org.bukkit.scheduler.BukkitTask;

import com.elytraforce.elytracore.matchtracker.TrackablePlayer;

public class Bar {
	
	private TrackablePlayer player;
	private BossBar bar;
	private BukkitTask task;
	
	public TrackablePlayer getPlayer() { return this.player; }
	public BossBar getBar() { return this.bar; }
	public BukkitTask getTask() { return this.task; }
	
	public void setTask(BukkitTask task) { this.task = task; }
	public boolean isTaskActive() { return task != null; }
	
	public Bar(TrackablePlayer player, BossBar bar, BukkitTask task) {
		this.player = player;
		this.bar = bar;
		this.task = task;
	}
	
}

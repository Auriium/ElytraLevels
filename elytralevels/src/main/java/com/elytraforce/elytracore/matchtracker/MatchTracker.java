package com.elytraforce.elytracore.matchtracker;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.bossbar.Bar;
import com.elytraforce.elytracore.player.ElytraPlayer;

public class MatchTracker {
	
	private ArrayList<Bar> bossBarCollection;
	private ArrayList<TrackablePlayer> trackablePlayers;
	private int timeElapsed;
	
	public int getTimeElapsed() { return this.timeElapsed; }
	
	public MatchTracker() {
		this.timeElapsed = 0;
		this.trackablePlayers = new ArrayList<TrackablePlayer>();
		this.bossBarCollection = new ArrayList<>();
	}
	
	public void broadcastBar(String string, double progress) {
		BossBar bar = Bukkit.createBossBar(string, BarColor.WHITE, BarStyle.SOLID);
		bar.setProgress(1.0);
		for (TrackablePlayer player : this.trackablePlayers) {
			bar.addPlayer(player.asEPlayer().asBukkitPlayer());
		}
		
		new BukkitRunnable() {
			@Override
			public void run() {
				bar.removeAll();
				this.cancel();
			}
		}.runTaskLater(Main.get(), 40L);	
	}
	
	public void displayBar(TrackablePlayer track, String string, double progress) {
		Bar bar = this.getFromTrackablePlayer(track);
		BossBar bossBar = bar.getBar();
		
		bossBar.setColor(BarColor.GREEN);
		bossBar.setTitle(string);
		bossBar.setProgress(progress);
		//if it hasnt been cancelled
		
		if (bar.isTaskActive()) {
			bar.getTask().cancel();
		}
		
		BukkitTask task = new BukkitRunnable() {
			@Override
			public void run() {
				bossBar.setColor(BarColor.WHITE);
				bossBar.setTitle("");
				bossBar.setProgress(1.0);
				
				this.cancel();
				bar.setTask(null);
			}
		}.runTaskLater(Main.get(), 20L);	
		bar.setTask(task);

	}
	
	public void addPlayer(ElytraPlayer player) {
		if (this.getTrackablePlayer(player) != null) { return; }
		TrackablePlayer p = new TrackablePlayer(player);
		
		this.trackablePlayers.add(p);
		this.bossBarCollection.add(new Bar(p,Bukkit.createBossBar("", BarColor.WHITE, BarStyle.SOLID),null));
		
		Bar bar = this.getFromTrackablePlayer(p);
		bar.getBar().addPlayer(p.asEPlayer().asBukkitPlayer());
		
		//add to bossbar
		
	}
	
	public void removePlayer(ElytraPlayer player) {
		if (this.getTrackablePlayer(player) == null) { return; }
		
		Bar bar = this.getFromTrackablePlayer(this.getTrackablePlayer(player));
		bar.getBar().removeAll();
		this.bossBarCollection.remove(bar);

		this.trackablePlayers.remove(this.getTrackablePlayer(player));
		
	}
	
	public void start(ArrayList<ElytraPlayer> players) {
		for (ElytraPlayer player : players) { 
			this.addPlayer(player);
		}
	}
	
	public void stop(MatchMenu menu) {
		for (TrackablePlayer player : trackablePlayers) {
			player.showGUI(menu);
			//THIS MUST BE FUNCTIONALLY IDENTICAL TO #removePlayer
			
			Bar bar = this.getFromTrackablePlayer(player);
			bar.getBar().removeAll();
			
			this.bossBarCollection.remove(bar);
		}
		
		trackablePlayers.clear();
	}
	
	public void tick() {
		this.timeElapsed++;
	}
	
	public boolean isTracked(TrackablePlayer player) {
		return this.isTracked(player.asEPlayer());
	}
	
	public boolean isTracked(ElytraPlayer player) {
		if (this.getTrackablePlayer(player) != null) {
			return true;
		} else {
			return false;
		}
	}
	
	public TrackablePlayer getTrackablePlayer(ElytraPlayer player) {
		for (TrackablePlayer p : this.trackablePlayers) {
			if (p.asEPlayer() == player) { return p; }
		}
		return null;
	}
	
	public Bar getFromTrackablePlayer(TrackablePlayer player) {
		for (Bar bar : this.bossBarCollection) {
			if (bar.getPlayer().equals(player)) {
				return bar;
			}
		}
		return null;
	}
	
	
}

package com.elytraforce.elytracore.matchtracker;

import java.util.ArrayList;

import org.bukkit.entity.Player;

import com.elytraforce.elytracore.player.ElytraPlayer;

public class MatchTracker {
	private ArrayList<TrackablePlayer> trackablePlayers;
	private int timeElapsed;
	
	public int getTimeElapsed() { return this.timeElapsed; }
	
	public MatchTracker() {
		this.timeElapsed = 0;
	}
	
	public void start(ArrayList<ElytraPlayer> players) {
		for (ElytraPlayer player : players) { this.trackablePlayers.add(new TrackablePlayer(player)); }
	}
	
	public void stop() {
		for (TrackablePlayer player : trackablePlayers) {
			player.showGUI();
		}
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
	
	
}

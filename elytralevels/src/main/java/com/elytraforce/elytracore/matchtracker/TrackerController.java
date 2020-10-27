package com.elytraforce.elytracore.matchtracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.scheduler.BukkitRunnable;

import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.player.ElytraPlayer;

public class TrackerController {

	private static TrackerController instance;
	private HashMap<UUID, MatchTracker> trackers;
	
	private TrackerController() {
		this.trackers = new HashMap<>();
		
		//Tick every tracker every 1 second
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.get(), () -> {
			for (MatchTracker track : this.trackers.values()) {
				track.tick();
			}
		}, 20L, 20L);
		
	}
	
	public void beginTracker(UUID gameID, ArrayList<ElytraPlayer> players) {
		new MatchTracker().start(players);
	}
	
	public void endTracker(UUID gameID) {
		trackers.get(gameID).stop();
		this.trackers.remove(gameID);
	}
	
	public boolean isTracked(ElytraPlayer player) {
		for (MatchTracker tracker : this.trackers.values()) {
			if (tracker.isTracked(player)) {
				return true;
			}
		}
		return false;
	}
	
	public TrackablePlayer getTrackablePlayer(ElytraPlayer player) {
		for (MatchTracker tracker : this.trackers.values()) {
			if (tracker.isTracked(player) ) {
				return tracker.getTrackablePlayer(player);
			}
		}
		return null;
	}
	
	public static TrackerController get() {
		if (instance == null) {
			return instance = new TrackerController();
		} else {
			return instance;
		}
	}
}

package com.elytraforce.elytracore.matchtracker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Objects;
import java.util.UUID;

import org.bukkit.Bukkit;
import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.player.ElytraPlayer;
import com.elytraforce.elytracore.utils.AuriUtils;

public class TrackerController {
	
	//HOW TO USE: gen a random UUID every match, then store this uuid and create a match with it.
	//CALL THE CORRECT EVENTS when ever something happens.

	private static TrackerController instance;
	private final HashMap<UUID, MatchTracker> trackers;
	
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
		if (trackers.containsKey(gameID)) {
			AuriUtils.logError("Tracker attempted to track a game id that already exists");
			return;
		}
		
		MatchTracker tracker = new MatchTracker();
		trackers.put(gameID, tracker);
		tracker.start(players);
	}
	
	public void addToTracker(UUID gameID, ElytraPlayer player) {
		if (!trackers.containsKey(gameID)) {
			AuriUtils.logError("Tracker attempted to add a user to game id that does not exists");
		}
		
		trackers.get(gameID).addPlayer(player);
	}
	
	public void removeFromTracker(UUID gameID, ElytraPlayer player) {
		if (!trackers.containsKey(gameID)) {
			AuriUtils.logError("Tracker attempted to remove a user to game id that does not exists");
		}
		
		trackers.get(gameID).removePlayer(player);
	}
	
	public void endTracker(UUID gameID,MatchMenu menu) {
		if (!trackers.containsKey(gameID)) {
			AuriUtils.logError("Tracker attempted to end a game id that does not exists");
			return;
		}
		
		trackers.get(gameID).stop(menu);
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
	
	public MatchTracker getMatch(TrackablePlayer player) {
		for (MatchTracker tracker : this.trackers.values()) {
			if (tracker.isTracked(player) ) {
				return tracker;
			}
		}
		
		return null;
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
		return Objects.requireNonNullElseGet(instance, () -> instance = new TrackerController());
	}
	
	public void shutdown() {
		for (MatchTracker tarcker : this.trackers.values()) {
			tarcker.stop(null);
		}
	}
}

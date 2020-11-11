package com.elytraforce.elytracore.matchtracker;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import com.elytraforce.elytracore.events.MoneyEvent;
import com.elytraforce.elytracore.events.XPEvent;

public class TrackerListener implements Listener{
	
	private TrackerController controllerInstance;
	
	public TrackerListener() {
		this.controllerInstance = TrackerController.get();
	}
	
	@EventHandler
	public void onXPEvent(XPEvent event) {
		if (controllerInstance.isTracked(event.getElytraPlayer())) {
			controllerInstance.getTrackablePlayer(event.getElytraPlayer()).addMatchXP(event.getNewXP() - event.getOldXP());
		}
	}
	
	@EventHandler
	public void onMoneyEvent(MoneyEvent event) {
		if (controllerInstance.isTracked(event.getElytraPlayer())) {
			controllerInstance.getTrackablePlayer(event.getElytraPlayer()).addMatchCoins(event.getNewMoney() - event.getOldMoney());
		}
	}
	
	

}

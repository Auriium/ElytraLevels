package com.elytraforce.elytracore.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.elytraforce.elytracore.player.ElytraPlayer;

public class LevelEvent extends Event{
	
	private final ElytraPlayer player;
	private final int oldLevel;
	private final int newLevel;
	private final ChangeEnum changeType;
	
	public ElytraPlayer getElytraPlayer() { return this.player; }
	public int getOldLevel() { return this.oldLevel; }
	public int getNewLevel() { return this.newLevel; }
	public ChangeEnum getChangeType() { return this.changeType; }
	
	public LevelEvent(ElytraPlayer player, int oldLevel, int newLevel, ChangeEnum change) {
		this.player = player;
		this.oldLevel = oldLevel;
		this.newLevel = newLevel;
		this.changeType = change;
    }
	
	private static final HandlerList HANDLERS = new HandlerList();

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}

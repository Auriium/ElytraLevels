package com.elytraforce.elytracore.events;

import com.elytraforce.elytracore.player.ElytraPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class XPEvent extends Event {
	
	private final ElytraPlayer player;
	private final int oldXP;
	private final int newXP;
	private final ChangeEnum change;
	
	public ElytraPlayer getElytraPlayer() { return this.player; }
	public int getOldXP() { return this.oldXP; }
	public int getNewXP() { return this.newXP; }
	public ChangeEnum getChangeType() { return this.change; }
	
	
	public XPEvent(ElytraPlayer player, int oldXP, int newXP, ChangeEnum change) {
		this.player = player;
		this.oldXP = oldXP;
		this.newXP = newXP;
		this.change = change;
    }
	
	private static final HandlerList HANDLERS = new HandlerList();

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }

}

package com.elytraforce.elytracore.events;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

import com.elytraforce.elytracore.player.ElytraPlayer;

public class MoneyEvent extends Event{
	
	private ElytraPlayer player;
	private int oldMoney;
	private int newMoney;
	private ChangeEnum change;
	
	public ElytraPlayer getElytraPlayer() { return this.player; }
	public int getOldMoney() { return this.oldMoney; }
	public int getNewMoney() { return this.newMoney; }
	public ChangeEnum getChangeType() { return this.change; }
	
	
	public MoneyEvent(ElytraPlayer player, int oldMoney, int newMoney, ChangeEnum change) {
		this.player = player;
		this.oldMoney = oldMoney;
		this.newMoney = newMoney;
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

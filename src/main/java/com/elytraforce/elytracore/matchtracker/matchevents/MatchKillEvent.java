package com.elytraforce.elytracore.matchtracker.matchevents;

import com.elytraforce.elytracore.events.ChangeEnum;
import com.elytraforce.elytracore.player.ElytraPlayer;
import com.elytraforce.elytracore.utils.AuriUtils;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MatchKillEvent extends Event {

    private ElytraPlayer killer;
    private ElytraPlayer killed;
    private int oldKills;
    private int newKills;
    private ChangeEnum change;
    private String reason;

    public ElytraPlayer getKiller() { return this.killer; }
    public ElytraPlayer getKilled() { return this.killed; }
    public int getOldKills() { return this.oldKills; }
    public int getNewKills() { return this.newKills; }
    public ChangeEnum getChangeType() { return this.change; }
    public String getKillReason() { return this.reason; }
    public String getFormattedKillReason() {
        return AuriUtils.colorString(reason);
    }

    
    public MatchKillEvent(ElytraPlayer killer, ElytraPlayer killed, int oldKills, int newKills, ChangeEnum change, String reason) {
        this.killed = killed;
        this.killer = killer;
        this.oldKills = oldKills;
        this.newKills = newKills;
        this.change = change;
        this.reason = reason;
    }

    private static final HandlerList HANDLERS = new HandlerList();

    public HandlerList getHandlers() {
        return HANDLERS;
    }

    public static HandlerList getHandlerList() {
        return HANDLERS;
    }
}
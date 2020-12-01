package com.elytraforce.elytracore.matchtracker.matchevents;

import com.elytraforce.aUtils.chat.AChat;
import com.elytraforce.elytracore.events.ChangeEnum;
import com.elytraforce.elytracore.player.ElytraPlayer;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class MatchKillEvent extends Event {

    private final ElytraPlayer killer;
    private final ElytraPlayer killed;
    private final int oldKills;
    private final int newKills;
    private final ChangeEnum change;
    private final String reason;

    public ElytraPlayer getKiller() { return this.killer; }
    public ElytraPlayer getKilled() { return this.killed; }
    public int getOldKills() { return this.oldKills; }
    public int getNewKills() { return this.newKills; }
    public ChangeEnum getChangeType() { return this.change; }
    public String getKillReason() { return this.reason; }
    public String getFormattedKillReason() {
        return AChat.colorString(reason);
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
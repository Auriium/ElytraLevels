package com.elytraforce.elytracore.matchtracker;

import com.elytraforce.elytracore.player.ElytraPlayer;

public class TrackablePlayer {
	
	private ElytraPlayer player;
	private int matchKills;
	private int matchDeaths;
	private int matchAssists;
	private int shotsLanded;
	private int shotsTotal;
	
	private int matchXP;
	private int matchCoins;
	
	public ElytraPlayer asEPlayer() { return this.player; }
	public int getMatchKills() { return this.matchKills; }
	public int getMatchDeaths() { return this.matchDeaths; }
	public int getMatchAssists() { return this.matchAssists; }
	public int getShotsLanded() { return this.shotsLanded; }
	public int getShotsTotal() { return this.shotsTotal; }
	public double getKdr() { return matchKills / matchDeaths; }
	
	public int getGainedXP() { return this.matchXP; }
	public int getGainedCoins() { return this.matchCoins; }
	
	public TrackablePlayer(ElytraPlayer player) {
		this.player = player;
		this.matchKills = 0;
		this.matchDeaths = 0;
		this.matchAssists = 0;
		this.shotsLanded = 0;
		this.shotsTotal = 0;
		
		this.matchXP = 0;
		this.matchCoins = 0;
	}
	
	public void addKills(int i) { this.matchKills = this.matchKills + i; }
	public void addDeaths(int i) { this.matchDeaths = this.matchDeaths + i; }
	public void addAssists(int i) { this.matchAssists = this.matchAssists + i; }
	public void addShotsLanded(int i) { this.shotsLanded = this.shotsLanded + i; }
	public void addShotsTotal(int i) { this.shotsTotal = this.shotsTotal + i; }
	public void addMatchXP(int i) { this.matchXP = this.matchXP + i; }
	public void addMatchCoins(int i) { this.matchCoins = this.matchCoins + i; }
	
	public void showGUI() {
		player.asBukkitPlayer().sendMessage(this.matchXP + this.matchCoins + "");
	}
}

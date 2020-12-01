package com.elytraforce.elytracore.player;

import com.elytraforce.aUtils.ALogger;
import com.elytraforce.elytracore.player.redis.Delta;
import com.elytraforce.elytracore.player.redis.RedisController;
import com.elytraforce.elytracore.player.redis.enums.DeltaEnum;
import com.elytraforce.elytracore.player.redis.enums.ValueEnum;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;


public class ElytraPlayer {

	private final OfflinePlayer player;
    private final UUID uniqueId;
    private final String name;
    private int level;
    private int experience;
    private int money;
    private List<Integer> unlockedRewards;
    private boolean inDatabase;
    
    private boolean displayingTitle;
    private int displayedXP;
    private BukkitTask displayedTask;
    private BukkitTask displayedTitleTask;

    private final ArrayList<Delta> queuedChanges;
    
    public Player asBukkitPlayer() {
    	if (player.isOnline()) {
    		return player.getPlayer();
		}
    	return null;
    }
    public OfflinePlayer asOfflinePlayer() {
    	return player;
	}

	//make these relay delta related information. ( e.g. getBalance needs to return balance PLUS the combined balances stored in all deltas)
    public UUID getUUID() { return uniqueId; }
    public String getName() { return name; }
    public Integer getLevel() { return level + this.getCachedDeltaData( ValueEnum.LEVEL); }
    public Integer getExperience() { return experience + this.getCachedDeltaData( ValueEnum.XP); }
    public List<Integer> getUnlockedRewards() { return this.unlockedRewards; }
    public void setUnlockedRewards(List<Integer> list) { this.unlockedRewards = list; }
    public boolean isInDatabase() { return inDatabase; }
    public void setInDatabase(boolean choice) { this.inDatabase = choice; }
    public Integer getMoney() { return this.money + this.getCachedDeltaData( ValueEnum.MONEY); }
    
    public boolean isDisplayingTitle() { return this.displayingTitle; }
    public void setDisplayingTitle(boolean bool) { this.displayingTitle = bool; }
    public int getDisplayedXP() { return this.displayedXP; }
    public void setDisplayedXP(int xp) { this.displayedXP = xp; }
    public BukkitTask getDisplayedTask() { return this.displayedTask; }
    public void setDisplayedTask(BukkitTask task) { this.displayedTask = task; }
    public BukkitTask getDisplayedTitleTask() { return this.displayedTitleTask; }
    public void setDisplayedTitleTask(BukkitTask task) { this.displayedTitleTask = task; }

    public ArrayList<Delta> getChanges() { return this.queuedChanges; }
    
    @Override
    public boolean equals(Object toCompare) {
        if (!(toCompare instanceof ElytraPlayer))
            return false;
        return uniqueId.equals(((ElytraPlayer) toCompare).uniqueId);
    }
	
	public ElytraPlayer(OfflinePlayer player, Integer level, Integer experience, Integer money, List<Integer> unlockedRewards, boolean inDatabase) {
		this.uniqueId = player.getUniqueId();
		this.player = player;
		this.level = level;
		this.experience = experience;
		this.unlockedRewards = unlockedRewards;
		this.name = player.getName();
		this.inDatabase = inDatabase;
		this.money = money;
		
		this.displayingTitle = false;
		this.displayedXP = 0;
		this.displayedTask = null;

		this.queuedChanges = new ArrayList<>();
	}

	public void adjust(Delta delta) {
    	int amount = delta.getAmount(); if (delta.getChange().equals(DeltaEnum.DECREASE)) { amount = Math.negateExact(amount); }
		//interpret delta and adjust based on it
		switch (delta.getType()) {
			case XP:
				this.experience = this.experience + amount;
				break;
			case LEVEL:
				this.level = this.level + amount;
				break;
			case MONEY:
				this.money = this.money + amount;
				break;
		}
	}

	public void adjustFromPlayer(ElytraPlayer player) {
    	this.level = player.getLevel();
    	this.experience = player.getExperience();
    	this.money = player.getMoney();
    	this.unlockedRewards = player.getUnlockedRewards();
	}

	public void addChange(Delta delta) {
		this.queuedChanges.add(delta);
		RedisController.get().redisPushToBungee(delta);
	}

	public void removeChange(Delta delta) {
    	this.queuedChanges.remove(delta);
	}

	public String getProgressBar() {
		int bars = (int)Math.floor(this.getExperience() * (9 * 1.0) / this.getRequiredXPToNextLevel());
		StringBuilder stringBuild = new StringBuilder();
		
		stringBuild.append(ChatColor.YELLOW);
        for (int j = 0; j < bars; ++j) {
        	stringBuild.append("░");
        }
        stringBuild.append(ChatColor.GRAY);
        for (int j = 0; j < 9 - bars; ++j) {
        	stringBuild.append("░");
        }
		
        return stringBuild.toString(); 
	}   
	
	public int getProgressBarInt() {
		return (int)Math.floor(this.getExperience() * (9 * 1.0) / this.getRequiredXPToNextLevel());
	}
	
	public String getPercent() {
		double v = this.getExperience() / this.getRequiredXPToNextLevel();
		double sex = (v * 100); 
		return sex + "%";
	}
	
	public boolean canLevelUp() {
		return this.getExperience() >= this.getRequiredXPToNextLevel();
	}
	
	public int getRequiredXPToNextLevel() {
		return (this.getLevel() + 1)*1000;
	}
	
	public int getNextLevel() {
		return this.getLevel() + 1;
	}
	
	public boolean isDonator() {
    	//TODO: mysql time ;) (aka fix this stupidity)
		if (this.asBukkitPlayer() == null) { return false; } else {
			return this.asBukkitPlayer().hasPermission("elytraforce.donator");
		}
	}

	public int getCachedDeltaData(ValueEnum type) {
    	int total = 0;
    	Set<Delta> filter = this.queuedChanges.stream().filter(d -> d.getType() == type).collect(Collectors.toSet());
    	for (Delta delta : filter) {
    		if (delta.getChange().equals(DeltaEnum.INCREASE)) { total = total + delta.getAmount();
			} else { total = total - delta.getAmount(); }
		}

    	return total;
	}
	
}

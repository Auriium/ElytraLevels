package com.elytraforce.elytralevels.player;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import com.elytraforce.elytralevels.utils.MessageController;


public class LevelPlayer {

	private final Player player;
    private final UUID uniqueId;
    private final String name;
    private int level;
    private int experience;
    private int money;
    private List<Integer> unlockedRewards;
    private boolean inDatabase;
    
    public Player asBukkitPlayer() { return player; }
    public UUID getUUID() { return uniqueId; }
    public String getName() { return name; }
    public Integer getLevel() { return level; }
    public void setLevel(int level) { this.level = level; }
    public Integer getExperience() { return experience; }
    public void setExperience(int exp) { this.experience = exp; }
    public List<Integer> getUnlockedRewards() { return this.unlockedRewards; }
    public void setUnlockedRewards(List<Integer> list) { this.unlockedRewards = list; }
    public boolean isInDatabase() { return inDatabase; }
    public void setInDatabase(boolean choice) { this.inDatabase = choice; }
    public Integer getMoney() { return this.money; }
    
    @Override
    public boolean equals(Object toCompare) {
        if (!(toCompare instanceof LevelPlayer))
            return false;
        return uniqueId.equals(((LevelPlayer) toCompare).uniqueId);
    }
	
	public LevelPlayer(Player player, Integer level, Integer experience, Integer money, List<Integer> unlockedRewards, boolean inDatabase) {
		this.uniqueId = player.getUniqueId();
		this.player = player;
		this.level = level;
		this.experience = experience;
		this.unlockedRewards = unlockedRewards;
		this.name = player.getName();
		this.inDatabase = inDatabase;
		this.money = money;
	}
	
	public void addLevel(int amount) {
		this.level = this.level + amount;
		MessageController.addLevelMessage(this);
	}
	
	public void removeLevel(int amount) {
		this.level = this.level - amount;
		if (this.level < 0) {
			this.level = 0;
		}
		MessageController.removeLevelMessage(this);
	}
	
	public void addExperience(int exp, boolean title) {
		
		//current xp is 0, player is level 1. next level xp is 2000 and then 3000, player got 6000.
		
		MessageController.addXPMessage(this, exp, title);
		this.experience = this.experience + exp;
		
		while (this.canLevelUp()) {
			this.experience = this.experience - getRequiredXPToNextLevel();
			addLevel(1);
		}
	}
	
	public void removeExperience(int exp, boolean title) {
		
		MessageController.removeXPMessage(this, exp, title);
		
		this.experience = this.experience - exp;
		
		if (this.experience < 0) {
			this.experience = 0;
		}
	}
	
	private boolean canLevelUp() {
		if (this.experience >= this.getRequiredXPToNextLevel()) {
			return true;
		}
		return false;
	}
	
	public int getRequiredXPToNextLevel() {
		int next = (this.level + 1)*1000;
		return next;
	}
	
	public int getNextLevel() {
		return this.level + 1;
	}
	
}

package com.elytraforce.elytracore.player;

import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.config.PluginConfig;
import com.elytraforce.elytracore.events.ChangeEnum;
import com.elytraforce.elytracore.events.LevelEvent;
import com.elytraforce.elytracore.events.MoneyEvent;
import com.elytraforce.elytracore.events.XPEvent;
import com.elytraforce.elytracore.utils.AuriUtils;
import com.elytraforce.elytracore.utils.MessageUtils;
import com.elytraforce.elytracore.utils.TitleUtils;


public class ElytraPlayer {

	private final Player player;
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
    
    public boolean isDisplayingTitle() { return this.displayingTitle; }
    public int getDisplayedXP() { return this.displayedXP; }
    
    public BukkitTask getDisplayedTitleTask() { return this.displayedTitleTask; }
    public void setDisplayedTitleTask(BukkitTask task) { this.displayedTitleTask = task; }
    
    @Override
    public boolean equals(Object toCompare) {
        if (!(toCompare instanceof ElytraPlayer))
            return false;
        return uniqueId.equals(((ElytraPlayer) toCompare).uniqueId);
    }
	
	public ElytraPlayer(Player player, Integer level, Integer experience, Integer money, List<Integer> unlockedRewards, boolean inDatabase) {
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
	}
	
	public void addLevel(int amount, boolean title, boolean sendMessage) {
		
		int oldLevel = this.level;
		
		this.level = this.level + amount;
		
		if (this.level == PluginConfig.getMaxLevel()) {
			MessageUtils.maxLevelMessage(this);
			TitleUtils.sendTitle(this, AuriUtils.colorString("&9&lLEVEL UP!"), AuriUtils.colorString("&7" + (this.getLevel() - 1) + " -> &e" + this.getLevel()));
			return;
		}
		
		if (sendMessage) {
			MessageUtils.addLevelMessage(this);
		}
		
		if (title) {
			TitleUtils.sendTitle(this, AuriUtils.colorString("&9&lLEVEL UP!"), AuriUtils.colorString("&7" + (this.getLevel() - 1) + " -> &e" + this.getLevel()));
		}
		
		Bukkit.getPluginManager().callEvent(new LevelEvent(this, oldLevel, this.level, ChangeEnum.INCREASE));
		
	}
	
	public void removeLevel(int amount, boolean title, boolean sendMessage) {
		int oldLevel = this.level;
		
		this.level = this.level - amount;
		if (this.level < 0) {
			this.level = 0;
		}
		
		if (sendMessage) {
			MessageUtils.removeLevelMessage(this);
		}
		
		if (title) {
			TitleUtils.sendTitle(this, "&9&lLEVEL DOWN!", "&7" + (this.getLevel() - 1) + " -> &e" + this.getLevel());
		}
		
		Bukkit.getPluginManager().callEvent(new LevelEvent(this, oldLevel, this.level, ChangeEnum.DECREASE));
	}
	
	public void addExperience(int exp, boolean title, boolean sendMessage) {
		
		//current xp is 0, player is level 1. next level xp is 2000 and then 3000, player got 6000.
		
		int oldXP = this.experience;
		
		if (this.level > PluginConfig.getMaxLevel()) {
			return;
		}
		
		if (isDonator()) {
			exp = (int) (exp * 1.2);
		}
		
		if (sendMessage) {
			MessageUtils.addXPMessage(this, exp);
		}
				
		if (title) {
			if (this.displayingTitle) {

				this.displayedXP = this.displayedXP + exp;
				this.displayedTask.cancel();
				this.displayedTask = new BukkitRunnable() {
		            public void run() {
		                displayingTitle = false;
		                displayedXP = 0;
		                displayedTask = null;
		            }
		        }.runTaskLater(Main.get(), (long)40L);
			} else {
				this.displayedXP = exp;
				this.displayingTitle = true;
		        
				this.displayedTask = new BukkitRunnable() {
		            public void run() {
		                displayingTitle = false;
		                displayedXP = 0;
		                displayedTask = null;
		            }
		        }.runTaskLater(Main.get(), (long)40L);
			}
			
			TitleUtils.sendAnimatedSideTitle(this, "", "         &b+" + this.displayedXP + "❂", 10);
		}
		
		this.experience = this.experience + exp;
		
		Bukkit.getPluginManager().callEvent(new XPEvent(this, oldXP, this.experience, ChangeEnum.INCREASE));
		
		while (this.canLevelUp()) {
			this.experience = this.experience - getRequiredXPToNextLevel();
			addLevel(1,true,true);
		}
	}
	
	public void removeExperience(int exp, boolean title, boolean sendMessage) {
		
		int oldXP = this.experience;
		
		if (sendMessage) {
			MessageUtils.removeXPMessage(this, exp);
		}
		
		if (title) {
			//TODO: title
		}

		this.experience = this.experience - exp;
		
		if (this.experience < 0) {
			this.experience = 0;
		}
		
		Bukkit.getPluginManager().callEvent(new XPEvent(this, oldXP, this.experience, ChangeEnum.DECREASE));
	}
	
	public void addMoney(int money, boolean sendMessage) {
		
		int oldMoney = this.money;
		
		if (sendMessage) {
			MessageUtils.addMoneyMessage(this, money);
		}
		
		this.money = this.money + money;
		
		if (this.money > Integer.MAX_VALUE) {
			this.money = Integer.MAX_VALUE;
		}
		
		Bukkit.getPluginManager().callEvent(new MoneyEvent(this, oldMoney, this.experience, ChangeEnum.INCREASE));
	}
	
	public void removeMoney(int money, boolean sendMessage) {
		
		int oldMoney = this.money;
		
		if (sendMessage) {
			MessageUtils.removeMoneyMessage(this, money);
		}
		
		this.money = this.money - money;
		
		if (this.money < 0) {
			this.money = 0;
		}
		
		Bukkit.getPluginManager().callEvent(new MoneyEvent(this, oldMoney, this.experience, ChangeEnum.DECREASE));
		
	}
	
	public void setMoney(int money, boolean sendMessage) {
		int oldMoney = this.money;
		
		if (sendMessage) {
			MessageUtils.setMoneyMessage(this, money);
		}
		
		this.money = money;
		
		if (this.money < 0) {
			this.money = 0;
		}
		
		if (this.money > Integer.MAX_VALUE) {
			this.money = Integer.MAX_VALUE;
		}
		
		Bukkit.getPluginManager().callEvent(new MoneyEvent(this, oldMoney, this.experience, ChangeEnum.SET));
	}
	
	//TODO: xboxsignouts style level up at the end of a round gui with animation, using this! (good maths)
	//TODO: add symbols!
	public String getProgressBar() {
		int bars = (int)Math.floor(this.experience * (9 * 1.0) / this.getRequiredXPToNextLevel());
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
		int bars = (int)Math.floor(this.experience * (9 * 1.0) / this.getRequiredXPToNextLevel());
		return bars;
	}
	
	public String getPercent() {
		double v = this.experience / this.getRequiredXPToNextLevel();
		double sex = (v * 100); 
		return sex + "%";
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
	
	public boolean isDonator() {
		if (this.asBukkitPlayer().hasPermission("elytraforce.donator")) {
			return true;
		} else {
			return false;
		}
	}
	
}

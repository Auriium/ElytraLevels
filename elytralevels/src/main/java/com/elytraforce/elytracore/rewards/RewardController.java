package com.elytraforce.elytracore.rewards;

import java.io.File;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.mask.BinaryMask;
import org.ipvp.canvas.mask.Mask;
import org.ipvp.canvas.paginate.PaginatedMenuBuilder;
import org.ipvp.canvas.slot.Slot;
import org.ipvp.canvas.slot.SlotSettings;
import org.ipvp.canvas.template.ItemStackTemplate;
import org.ipvp.canvas.template.StaticItemTemplate;
import org.ipvp.canvas.type.ChestMenu;

import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.player.ElytraPlayer;
import com.elytraforce.elytracore.player.PlayerController;
import com.elytraforce.elytracore.utils.AuriUtils;
import com.elytraforce.elytracore.utils.ItemBuilder;



public class RewardController {
	
	private List<RewardData> rewards;
	private ArrayList<Integer> rewardIntegers = new ArrayList<>();
	
	private static RewardController instance;
	private final File configFile;
    private final FileConfiguration config;
    
    public List<RewardData> getRewards() { return this.rewards; }
    public ArrayList<Integer> getInteger() { return this.rewardIntegers; }
    
    public List<Menu> pages;
    public LinkedHashMap<RewardData, SlotSettings> slotSettings;
    
    
    
    private RewardController() {
		this.configFile = new File(Main.get().getDataFolder(), "rewards.yml");
		
		if (!this.configFile.exists()) {
			configFile.getParentFile().mkdirs();
			Main.get().saveResource("rewards.yml", false);
		}
		
		this.config = YamlConfiguration.loadConfiguration(configFile);
		
		//initialize reward list
		
		ConfigurationSection rewardSection = config.getConfigurationSection("Rewards");
		
		ArrayList<RewardData> preRewardData = new ArrayList<RewardData>();
		
		if (rewardSection != null) {
			for (String key : rewardSection.getKeys(false)) {
				ConfigurationSection thisReward = rewardSection.getConfigurationSection(key);
				
				int level = thisReward.getInt("Level");
				String name = AuriUtils.colorString(thisReward.getString("Name"));
				List<String> description = AuriUtils.colorString(thisReward.getStringList("Description"));
				List<String> servers = (List<String>)thisReward.getStringList("Servers");
				if (thisReward.getStringList("Servers").isEmpty()) {
					servers.add("all");
				}
				
                List<String> commands = (List<String>)thisReward.getStringList("Commands");
             
                this.rewardIntegers.add(level);
                
                preRewardData.add(new RewardData(level, name, description, servers, commands)); 
                
			}
		} else {
			AuriUtils.logError("Reward section of rewards.yml does not exist!");
		}
		
		this.rewards = preRewardData;
		
		this.registerMenu();
		
	}
    
    private void registerMenu() {

    	ChestMenu.Builder menu = ChestMenu.builder(3)
                .title(AuriUtils.colorString("&c&lYour Rewards!")).redraw(true);
    	
    	BinaryMask itemSlots = BinaryMask.builder(menu.getDimensions())
    			.pattern("111111111")
		        .pattern("011111110")
		        .pattern("000000000").build();
    	
    	LinkedHashMap<RewardData, SlotSettings> clickableItemList = new LinkedHashMap<>();
    	
    	for (RewardData data : this.rewards) {
    		
    		String dataName = data.getNameFormatted();
    			
    			SlotSettings clickableItem = SlotSettings.builder()
    				.itemTemplate(p -> { 
        	            	ItemStack dataItem;
        	            	
        	            	ArrayList<String> finalLore = new ArrayList<String>();
        	        		finalLore.addAll(data.getDescription());
        	            	
        	            	if (data.hasUnlocked(PlayerController.get().getLevelPlayer(p))) {
        	            		finalLore.add("&r ");
        	        			finalLore.add("&a&lYOU HAVE ALREADY CLAIMED THIS");
        	        			
        	        			dataItem = new ItemBuilder(Material.MINECART)
            	    					.setDisplayName(dataName)
            	    					.setLore((ArrayList<String>)AuriUtils.colorString(finalLore)).build();
        	            	} else if (data.canUnlock(PlayerController.get().getLevelPlayer(p))) {
        	            		if (!data.isCorrectServer()) {
            	            		finalLore.add("&r ");
            	            		finalLore.add("&c&lCLAIM THIS ON &e&l" + data.getServers().get(0));
            	        			
            	        			dataItem = new ItemBuilder(Material.CHEST_MINECART)
            	        					.setDisplayName(dataName)
            	        					.setLore((ArrayList<String>)AuriUtils.colorString(finalLore))
            	        					.setGlow(true).build();
            	            	} else {
            	            		finalLore.add("&r ");
            	        			finalLore.add("&e&lCLICK TO CLAIM");
            	        			
            	        			dataItem = new ItemBuilder(Material.CHEST_MINECART)
            	        					.setDisplayName(dataName)
            	        					.setLore((ArrayList<String>)AuriUtils.colorString(finalLore))
            	        					.setGlow(true).build();
            	            	}
        	            	} else {
        	            		finalLore.add("&r ");
        	        			finalLore.add("&c&lREACH LEVEL &e&l" + data.getLevel() + "&c&l TO CLAIM");
        	        			
        	        			dataItem = new ItemBuilder(Material.CHEST_MINECART)
        	        					.setDisplayName(dataName)
        	        					.setLore((ArrayList<String>)AuriUtils.colorString(finalLore)).build();
        	            	}
        	            	
        	            	return dataItem; })
        	            
        	            .clickHandler((p, c) -> {
        	                if (data.hasUnlocked(PlayerController.get().getLevelPlayer(p))) {
        	                	
        	                } else if (data.canUnlock(PlayerController.get().getLevelPlayer(p))) {
        	                	if (!data.isCorrectServer()) {
        	                		
        	                	} else {
        	                		data.unlock(PlayerController.get().getLevelPlayer(p));
            	                	c.getClickedMenu().update(p);
        	                	}	
        	                } else if (!data.isCorrectServer()) {
        	                	
        	                } else {
        	                	
        	                }
        	            }).build();
    			
        	    clickableItemList.put(data, clickableItem);
    	}
    	
    	this.slotSettings = clickableItemList;
    	
    	
    	List<Menu> pages = PaginatedMenuBuilder.builder(menu)
		        .slots(itemSlots)
		        .addSlotSettings(clickableItemList.values())
		        .nextButton(new ItemStack(Material.ARROW))
		        .nextButtonSlot(26)
		        .previousButton(new ItemStack(Material.ARROW))
		        .previousButtonSlot(18)
		        .build();
    	

    	this.pages = pages;
    	
    }
    
    public void showMenu(ElytraPlayer player) {
    	
    	this.pages.get(0).open(player.asBukkitPlayer());
    	
    }
    
    public int getHighestUnlockedReward(ElytraPlayer player) {
    	return AuriUtils.closestInteger(Collections.max(player.getUnlockedRewards()), this.getRewardLevels());
    }
    
    private ArrayList<Integer> getRewardIntsBelowLevel(int level) {
    	ArrayList<Integer> returnable = new ArrayList<>();
    	for (Integer reward : this.rewardIntegers) {
    		if (reward <= level) {
    			returnable.add(reward);
    		}
    	}
    	return returnable;
    }
    
    public RewardData getHighestUnlockedRewardData(ElytraPlayer player) {
    	int level = AuriUtils.closestInteger(Collections.max(player.getUnlockedRewards()), this.getRewardLevels());
    	for (RewardData data : this.rewards) {
    		if (data.getLevel() == level) {
    			return data;
    		}
    	}
    	return null;
    }
    
    private ArrayList<Integer> getRewardLevels() {
    	ArrayList<Integer> list = new ArrayList<>(); 
    	
    	for (RewardData reward : this.rewards) {
    		list.add(reward.getLevel());
    	}
    	
    	return list;
    }
    
    public boolean hasRewards(ElytraPlayer player) {
    	Bukkit.broadcastMessage(player.getUnlockedRewards().toString());
    	Bukkit.broadcastMessage(this.getRewardIntsBelowLevel(player.getLevel()).toString());
    	if (player.getUnlockedRewards().containsAll(this.getRewardIntsBelowLevel(player.getLevel())) ) {
    		return false;
    	} else {
    		return true;
    	}
    }
	
    private boolean containsLevel(RewardData input) {
    	for (RewardData data : this.rewards) {
    		if (data.getLevel() == input.getLevel()) {
    			return true;
    		}
    	}
		return false;
    }
    
	public static RewardController get() {
		if (instance == null) {
			return new RewardController();
		} else {
			return instance;
		}
	}
	
}

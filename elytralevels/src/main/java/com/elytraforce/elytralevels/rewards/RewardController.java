package com.elytraforce.elytralevels.rewards;

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

import com.elytraforce.elytralevels.Main;
import com.elytraforce.elytralevels.player.LevelPlayer;
import com.elytraforce.elytralevels.player.PlayerController;
import com.elytraforce.elytralevels.utils.AuriUtils;
import com.elytraforce.elytralevels.utils.ItemBuilder;



public class RewardController {
	
	private List<RewardData> rewards;
	
	private static RewardController instance;
	private final File configFile;
    private final FileConfiguration config;
    
    public List<RewardData> getRewards() { return this.rewards; }
    
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
                List<String> commands = (List<String>)thisReward.getStringList("Commands");
             
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
        	            		finalLore.add("&r ");
        	        			finalLore.add("&e&lCLICK TO CLAIM");
        	        			
        	        			dataItem = new ItemBuilder(Material.CHEST_MINECART)
        	        					.setDisplayName(dataName)
        	        					.setLore((ArrayList<String>)AuriUtils.colorString(finalLore))
        	        					.setGlow(true).build();
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
        	                	data.unlock(PlayerController.get().getLevelPlayer(p));
        	                	showMenu(PlayerController.get().getLevelPlayer(p));
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
    
    public void showMenu(LevelPlayer player) {
    	
    	this.pages.get(0).open(player.asBukkitPlayer());
    }
    
    public int getHighestUnlockedReward(LevelPlayer player) {
    	return AuriUtils.closestInteger(Collections.max(player.getUnlockedRewards()), this.getRewardLevels());
    }
    
    public RewardData getHighestUnlockedRewardData(LevelPlayer player) {
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

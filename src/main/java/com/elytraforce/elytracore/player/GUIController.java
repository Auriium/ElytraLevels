package com.elytraforce.elytracore.player;

import com.elytraforce.aUtils.chat.AChat;
import com.elytraforce.aUtils.item.AItemBuilder;
import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.rewards.RewardController;
import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Material;
import org.ipvp.canvas.mask.BinaryMask;
import org.ipvp.canvas.slot.SlotSettings;
import org.ipvp.canvas.type.ChestMenu;

import java.util.ArrayList;

public class GUIController {
	
	private static GUIController instance;

	private GUIController(Main main) {
		
	}
	
	public void showProfile(ElytraPlayer viewer, ElytraPlayer target) {
    	ChestMenu menu = ChestMenu.builder(6).redraw(true).title(AChat.colorString("&9Player Statistics")).build();

    	BinaryMask itemSlots = BinaryMask.builder(menu.getDimensions())
    			.item(new AItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("").build())
    			.pattern("011111111")
    			.pattern("000100000")
    			.pattern("000100000")
    			.pattern("000100000")
		        .pattern("000100000")
		        .pattern("000100000").build();
    	
    	menu.getSlot(1, 1).setSettings(SlotSettings.builder().itemTemplate(p -> new AItemBuilder(Material.BARRIER).setDisplayName(AChat.colorString("&cClose!")).build()).clickHandler((p, c) -> p.closeInventory()).build());
    	
    	menu.getSlot(3, 2).setSettings(SlotSettings.builder().itemTemplate(p -> {
			ArrayList<String> lore = new ArrayList<>();
    		lore.add(AChat.colorString(""));
    		lore.add(AChat.colorString("&bProgress &7» " + target.getProgressBar()));
    		lore.add(AChat.colorString("&bEXP &7» " + target.getExperience() + "&7/" + target.getRequiredXPToNextLevel() + " &b❂"));
    		lore.add(AChat.colorString(""));
    		lore.add(AChat.colorString("&bYour level &7» &b" + target.getLevel()));
    		
    		return new AItemBuilder(Material.LEGACY_SKULL_ITEM, (short) 3).setHead(p.getName()).setLore(lore).setDisplayName(AChat.colorString("&9&lPLAYER STATISTICS")).build();
    	}).clickHandler((p, c) -> {
    		//row, collum
    	}).build());
    	
    	menu.getSlot(5, 2).setSettings(SlotSettings.builder().itemTemplate(p -> {
			ArrayList<String> lore = new ArrayList<>();
    		lore.add(AChat.colorString(""));
    		lore.add(AChat.colorString("&bYour balance &7» &e" + PlaceholderAPI.setPlaceholders(target.asBukkitPlayer(), "%vault_eco_balance_commas% ⛃")));
    		if (RewardController.get().hasRewards(target)) {
    			lore.add(AChat.colorString(""));
    			lore.add(AChat.colorString("&e&lYou have rewards!&7 Do /rewards to claim them!"));
    		}
    		
    		return new AItemBuilder(Material.SUNFLOWER).setLore(lore).setDisplayName(AChat.colorString("&9&lMONEY STATISTICS")).build();
    	}).clickHandler((p, c) -> {

    	}).build());
    	
    	
    	
    	
    	itemSlots.apply(menu);
    	
    	menu.open(viewer.asBukkitPlayer());
    }
	
	public static GUIController get() {
		if (instance == null) {
			return instance = new GUIController(Main.get());
		} else {
			return instance;
		}
	}
	
}

package com.elytraforce.elytracore.player;

import java.util.ArrayList;

import com.elytraforce.aUtils.item.AItemBuilder;
import org.bukkit.Material;
import org.ipvp.canvas.mask.BinaryMask;
import org.ipvp.canvas.slot.SlotSettings;
import org.ipvp.canvas.type.ChestMenu;

import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.rewards.RewardController;
import com.elytraforce.elytracore.utils.AuriUtils;

import me.clip.placeholderapi.PlaceholderAPI;

public class GUIController {
	
	private static GUIController instance;

	private GUIController(Main main) {
		
	}
	
	public void showProfile(ElytraPlayer viewer, ElytraPlayer target) {
    	ChestMenu menu = ChestMenu.builder(6).redraw(true).title(AuriUtils.colorString("&9Player Statistics")).build();

    	BinaryMask itemSlots = BinaryMask.builder(menu.getDimensions())
    			.item(new AItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("").build())
    			.pattern("011111111")
    			.pattern("000100000")
    			.pattern("000100000")
    			.pattern("000100000")
		        .pattern("000100000")
		        .pattern("000100000").build();
    	
    	menu.getSlot(1, 1).setSettings(SlotSettings.builder().itemTemplate(p -> new AItemBuilder(Material.BARRIER).setDisplayName(AuriUtils.colorString("&cClose!")).build()).clickHandler((p, c) -> p.closeInventory()).build());
    	
    	menu.getSlot(3, 2).setSettings(SlotSettings.builder().itemTemplate(p -> {
			ArrayList<String> lore = new ArrayList<>();
    		lore.add(AuriUtils.colorString(""));
    		lore.add(AuriUtils.colorString("&bProgress &7» " + target.getProgressBar()));
    		lore.add(AuriUtils.colorString("&bEXP &7» " + target.getExperience() + "&7/" + target.getRequiredXPToNextLevel() + " &b❂"));
    		lore.add(AuriUtils.colorString(""));
    		lore.add(AuriUtils.colorString("&bYour level &7» &b" + target.getLevel()));
    		
    		return new AItemBuilder(Material.LEGACY_SKULL_ITEM, (short) 3).setHead(p.getName()).setLore(lore).setDisplayName(AuriUtils.colorString("&9&lPLAYER STATISTICS")).build();
    	}).clickHandler((p, c) -> {
    		//row, collum
    	}).build());
    	
    	menu.getSlot(5, 2).setSettings(SlotSettings.builder().itemTemplate(p -> {
			ArrayList<String> lore = new ArrayList<>();
    		lore.add(AuriUtils.colorString(""));
    		lore.add(AuriUtils.colorString("&bYour balance &7» &e" + PlaceholderAPI.setPlaceholders(target.asBukkitPlayer(), "%vault_eco_balance_commas% ⛃")));
    		if (RewardController.get().hasRewards(target)) {
    			lore.add(AuriUtils.colorString(""));
    			lore.add(AuriUtils.colorString("&e&lYou have rewards!&7 Do /rewards to claim them!"));
    		}
    		
    		return new AItemBuilder(Material.SUNFLOWER).setLore(lore).setDisplayName(AuriUtils.colorString("&9&lMONEY STATISTICS")).build();
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

package com.elytraforce.elytracore.player;

import java.util.ArrayList;

import org.bukkit.Material;
import org.ipvp.canvas.mask.BinaryMask;
import org.ipvp.canvas.slot.SlotSettings;
import org.ipvp.canvas.type.ChestMenu;

import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.rewards.RewardController;
import com.elytraforce.elytracore.utils.AuriUtils;
import com.elytraforce.elytracore.utils.ItemBuilder;

import me.clip.placeholderapi.PlaceholderAPI;

public class GUIController {
	
	private Main main;
	private static GUIController instance;

	private GUIController(Main main) {
		this.main = main;
	}
	
	public void showProfile(ElytraPlayer viewer, ElytraPlayer target) {
    	ChestMenu menu = ChestMenu.builder(6).redraw(true).title(AuriUtils.colorString("&9Statistics")).build();

    	BinaryMask itemSlots = BinaryMask.builder(menu.getDimensions())
    			.item(new ItemBuilder(Material.GRAY_STAINED_GLASS_PANE).setDisplayName("").build())
    			.pattern("011111111")
    			.pattern("000100000")
    			.pattern("000100000")
    			.pattern("000100000")
		        .pattern("000100000")
		        .pattern("000100000").build();
    	
    	menu.getSlot(1, 1).setSettings(SlotSettings.builder().itemTemplate(p -> {
    		return new ItemBuilder(Material.BARRIER).setLore("",AuriUtils.colorString("&7Click to close!")).setDisplayName(AuriUtils.colorString("&cClose")).build();
    	}).clickHandler((p, c) -> {
    		p.closeInventory();
    	}).build());
    	
    	menu.getSlot(3, 2).setSettings(SlotSettings.builder().itemTemplate(p -> {
    		ElytraPlayer ep = target;
    		ArrayList<String> lore = new ArrayList<>();
    		lore.add(AuriUtils.colorString(""));
    		lore.add(AuriUtils.colorString("&9" + ep.asBukkitPlayer().getName() + "&7's Main Network statistics"));
    		lore.add(AuriUtils.colorString(""));
    		lore.add(AuriUtils.colorString("" + ep.getProgressBar() + " &7&l" + "&c" + ep.getExperience() + "&7/" + ep.getRequiredXPToNextLevel() + " &c❂"));
    		lore.add(AuriUtils.colorString(""));
    		lore.add(AuriUtils.colorString("&7You are level &b" + ep.getLevel() + "!&7"));
    		
    		return new ItemBuilder(Material.LEGACY_SKULL_ITEM, (short) 3).setHead(p.getName()).setLore(lore).setDisplayName(AuriUtils.colorString("&9" + p.getName() + " &7| " + ep.getLevel() + "" + "&7 's Profile")).build();
    	}).clickHandler((p, c) -> {
    		//row, collum
    	}).build());
    	
    	menu.getSlot(5, 2).setSettings(SlotSettings.builder().itemTemplate(p -> {
    		ElytraPlayer ep = target;
    		ArrayList<String> lore = new ArrayList<>();
    		lore.add(AuriUtils.colorString(""));
    		lore.add(AuriUtils.colorString("&9" + ep.asBukkitPlayer().getName() + "&7's Balance"));
    		lore.add(AuriUtils.colorString(""));
    		lore.add(AuriUtils.colorString("&7Your balance » &e" + PlaceholderAPI.setPlaceholders(ep.asBukkitPlayer(), "%vault_eco_balance_commas% ⛃")));
    		if (RewardController.get().hasRewards(ep)) {
    			lore.add(AuriUtils.colorString(""));
    			lore.add(AuriUtils.colorString("&e&lYou have rewards!&7 Do /rewards to claim them!"));
    		}
    		
    		return new ItemBuilder(Material.SUNFLOWER).setLore(lore).setDisplayName(AuriUtils.colorString("&9" + p.getName() + " &7| &7" + ep.getLevel() + "" + "&7 's Balance")).build();
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

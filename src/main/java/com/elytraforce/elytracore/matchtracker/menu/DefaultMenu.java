package com.elytraforce.elytracore.matchtracker.menu;

import com.elytraforce.aUtils.chat.AChat;
import com.elytraforce.aUtils.item.AItemBuilder;
import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.matchtracker.MatchMenu;
import com.elytraforce.elytracore.matchtracker.TrackablePlayer;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.scheduler.BukkitRunnable;
import org.ipvp.canvas.mask.BinaryMask;
import org.ipvp.canvas.mask.Mask;
import org.ipvp.canvas.slot.Slot;
import org.ipvp.canvas.type.ChestMenu;

public class DefaultMenu extends MatchMenu{
	
	private final String gameName;
	
	public DefaultMenu(String gameName) {
		this.gameName=gameName;
	}

	@Override
	public void showMenu(TrackablePlayer player) {
		
		ChestMenu menu = ChestMenu.builder(6).redraw(false).title(AChat.colorString("&9Match Statistics &7| " + player.asEPlayer().getName() + "'s Match | &c" + gameName)).build();
		Mask mask = BinaryMask.builder(menu)
                .item(new AItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayName(AChat.colorString("&7Progress")).build())
                .pattern("000000000") 
                .pattern("000000000") 
                .pattern("000000000") 
                .pattern("000000000")
                .pattern("111111111") 
                .pattern("000000000").build(); 
		mask.apply(menu);
		menu.open(player.asEPlayer().asBukkitPlayer());
		
		new BukkitRunnable() {
            int lastSlot = 0;
            
            @Override
            public void run() {
                if (this.lastSlot != 9) {
                    final int slotToAdd = 36 + this.lastSlot;
                    if (lastSlot <= player.asEPlayer().getProgressBarInt()) {
                    	//set that slot to glass pane   
                    	menu.getSlot(slotToAdd).setItem(new AItemBuilder(Material.CYAN_STAINED_GLASS_PANE).setDisplayName(AChat.colorString("&aProgress")).build());
                    	player.asEPlayer().asBukkitPlayer().playSound(player.asEPlayer().asBukkitPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_GUITAR, 1.0f, 2.0f);
                    	++this.lastSlot;
                    } else {
                    	this.lastSlot = 9;
                    }
                    
                }
                else {
                	//set up default items
                	
                    menu.getSlot(49).setItem(new AItemBuilder(Material.EXPERIENCE_BOTTLE).setDisplayName(AChat.colorString("&a&lYour Progress")).setLore("", player.asEPlayer().getProgressBar() + AChat.colorString(" &7» " + player.asEPlayer().getExperience() + "&7/&7" + player.asEPlayer().getRequiredXPToNextLevel() + "&b❂")).build());
                    Slot s = menu.getSlot(22);
                    s.setClickHandler((p,c) -> {
                    	p.closeInventory();
                    });
                    s.setItem(new AItemBuilder(Material.BARRIER).setDisplayName(AChat.colorString("&cClose!")).setLore("").build());
                    player.asEPlayer().asBukkitPlayer().playSound(player.asEPlayer().asBukkitPlayer().getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0f, 3.0f);
                    menu.getSlot(45).setItem(new AItemBuilder(Material.GOLD_INGOT).setDisplayName(AChat.colorString("&7Current Level: &b" + player.asEPlayer().getLevel())).build());
                    menu.getSlot(53).setItem(new AItemBuilder(Material.DIAMOND).setDisplayName(AChat.colorString("&7Next Level: &b" + player.asEPlayer().getNextLevel())).build());
                    this.cancel();
                    
                }
                
                menu.open(player.asEPlayer().asBukkitPlayer());
            }
        }.runTaskTimer(Main.get(), 0L, 2L);
	}

}

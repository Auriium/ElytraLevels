package com.elytraforce.elytracore.matchtracker.menu;

import org.bukkit.Material;
import org.ipvp.canvas.mask.BinaryMask;
import org.ipvp.canvas.type.ChestMenu;

import com.elytraforce.elytracore.matchtracker.MatchMenu;
import com.elytraforce.elytracore.matchtracker.TrackablePlayer;
import com.elytraforce.elytracore.utils.AuriUtils;
import com.elytraforce.elytracore.utils.ItemBuilder;

public class TeamMenu extends MatchMenu {
	//example of how to make your own menus for the rest of the dev team; Do not use this in a production environment.
	
	private Material winningTeam;
	private String winningDescription;
	
	public TeamMenu(Material team, String winningDescription) {
		this.winningTeam = team;
		this.winningDescription = winningDescription;
	}

	@Override
	public void showMenu(TrackablePlayer player) {
		//allows you to specify what you want
		
		ChestMenu menu = ChestMenu.builder(6).redraw(false).title(AuriUtils.colorString(winningDescription)).build();
		BinaryMask mask = BinaryMask.builder(menu)
                .item(new ItemBuilder(Material.WHITE_STAINED_GLASS_PANE).setDisplayName(AuriUtils.colorString("Progress")).build())
                .pattern("000000000") 
                .pattern("000000000") 
                .pattern("000000000") 
                .pattern("000000000")
                .pattern("111111111") 
                .pattern("000000000").build(); 
		mask.apply(menu);
		menu.open(player.asEPlayer().asBukkitPlayer());
		
		//winningTeam apply items
		//winningDescription apply string
		
	}

}

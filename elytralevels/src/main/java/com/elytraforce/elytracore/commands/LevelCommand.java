package com.elytraforce.elytracore.commands;
import org.bukkit.Bukkit;

import org.bukkit.entity.Player;

import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.player.ElytraPlayer;
import com.elytraforce.elytracore.player.GUIController;
import com.elytraforce.elytracore.player.PlayerController;
import com.elytraforce.elytracore.rewards.RewardController;
import com.elytraforce.elytracore.utils.AuriUtils;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Default;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Subcommand;
import co.aikar.commands.annotation.Syntax;
@CommandAlias("level|levels|elytralevel")
public class LevelCommand extends BaseCommand {

	public static final String WILDCARD = "*";

    private final Main main;

    public LevelCommand(Main main) {
        this.main = main;
    }

    public Main getInstance() { return main; }
    
    
    @Subcommand("stats")
    @CommandAlias("stats")
    @Default
    @CommandCompletion("@players @players")
    @Description("Displays your or someone elses statistics")
    public static void onStats(Player player, @Optional String player2) {
    	ElytraPlayer p;
    	
    	if (player2 == null) {
    		p = PlayerController.get().getLevelPlayer(player);
    	} else {
    		try {
    			p = PlayerController.get().getLevelPlayer(Bukkit.getPlayer(player2));
    		} catch(NullPointerException e) {
    			player.sendMessage(AuriUtils.colorString("&cThat player is offline or does not exist!"));
    			return;
    		}
    		
    	}
    	
    	GUIController.get().showProfile(PlayerController.get().getLevelPlayer(player), p);
    }
    
    @Subcommand("rewards")
    @CommandAlias("rewards")
    @Description("Add levels to a player") 
    @CommandCompletion("@players [amount] [title] [showMessage]")
    public void onRewardsCommands(Player sender) {
    		RewardController.get().showMenu(PlayerController.get().getLevelPlayer(sender));
    }
    
    @Subcommand("addLevel")
    @CommandPermission("elyraforce.admin")
    @Description("Add levels to a player") 
    @Syntax("")
    @CommandCompletion("@players [amount] [title] [showMessage]")
    public void onAddLevel(Player sender, String player, Integer amount, boolean title, boolean showMessage) {
    	if (player != null) {
    		
    		PlayerController.get().getLevelPlayer(Bukkit.getPlayer(player)).addLevel(amount, title, showMessage);
    	} else {
    		sender.sendMessage(AuriUtils.colorString("&cThat player is offline or does not exist!"));
    		return;
    	}
    	
    }
    
    @Subcommand("addXP")
    @CommandPermission("elytraforce.admin")
    @Description("Add xp to a player") 
    @CommandCompletion("@players [amount] [title] [showMessage]")
    public void onAddXP(Player sender, String player, Integer amount, boolean title, boolean showMessage) {
    	
    	if (player != null) {
    		PlayerController.get().getLevelPlayer(Bukkit.getPlayer(player)).addExperience(amount, title, showMessage);
    	} else {
    		sender.sendMessage(AuriUtils.colorString("&cThat player is offline or does not exist!"));
    		return;
    	}
    }
     
    @Subcommand("removeLevel")
    @CommandPermission("elytraforce.admin")
    @Description("Remove levels to a player") 
    @CommandCompletion("@players [amount] [title] [showMessage]")
    public void onRemoveLevel(Player sender, String player, Integer amount, boolean title, boolean showMessage) {
    	if (player != null) {
    		PlayerController.get().getLevelPlayer(Bukkit.getPlayer(player)).removeLevel(amount, title, showMessage);
    	} else {
    		sender.sendMessage(AuriUtils.colorString("&cThat player is offline or does not exist!"));
    		return;
    	}
    	
    }
    
    @Subcommand("removeXP")
    @CommandPermission("elytraforce.admin")
    @Description("Remove xp to a player") 
    @CommandCompletion("@players [amount] [title] [showMessage]")
    public void onRemoveXP(Player sender, String player, Integer amount, boolean title, boolean showMessage) {
    	if (player != null) {
    		PlayerController.get().getLevelPlayer(Bukkit.getPlayer(player)).removeExperience(amount, title, showMessage);
    	} else {
    		sender.sendMessage(AuriUtils.colorString("&cThat player is offline or does not exist!"));
    		return;
    	}
    	
    }
	
}

package com.elytraforce.elytralevels.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.elytraforce.elytralevels.Main;
import com.elytraforce.elytralevels.player.PlayerController;
import com.elytraforce.elytralevels.rewards.RewardController;
import com.elytraforce.elytralevels.utils.AuriUtils;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;
import net.minecraft.server.v1_15_R1.PlayerSelector;
@CommandAlias("level|levels|elytralevel")
public class LevelCommand extends BaseCommand {

	public static final String WILDCARD = "*";

    private final Main main;

    public LevelCommand(Main main) {
        this.main = main;
    }

    public Main getInstance() { return main; }
    
    @Subcommand("rewards")
    @Description("Add levels to a player") 
    @CommandCompletion("@players [amount]")
    public void onRewardsCommands(CommandSender sender) {
    		RewardController.get().showMenu(PlayerController.get().getLevelPlayer((Player) sender));
    }
    
    @Subcommand("addLevel")
    @CommandPermission("level.admin")
    @Description("Add levels to a player") 
    @CommandCompletion("@players [amount]")
    public void onAddLevel(CommandSender sender, String player, Integer amount) {
    	if (player != null) {
    		
    		PlayerController.get().getLevelPlayer(Bukkit.getPlayer(player)).addLevel(amount);
    	} else {
    		sender.sendMessage(AuriUtils.colorString("&cThat player is offline or does not exist!"));
    		return;
    	}
    	
    }
    
    @Subcommand("addXP")
    @CommandPermission("level.admin")
    @Description("Add xp to a player") 
    @CommandCompletion("@players [amount] [title]")
    public void onAddXP(CommandSender sender, String player, Integer amount, boolean title) {
    	
    	if (player != null) {
    		PlayerController.get().getLevelPlayer(Bukkit.getPlayer(player)).addExperience(amount, title);
    	} else {
    		sender.sendMessage(AuriUtils.colorString("&cThat player is offline or does not exist!"));
    		return;
    	}
    }
    
    @Subcommand("removeLevel")
    @CommandPermission("level.admin")
    @Description("Remove levels to a player") 
    @CommandCompletion("@players [amount]")
    public void onRemoveLevel(CommandSender sender, String player, Integer amount) {
    	if (player != null) {
    		PlayerController.get().getLevelPlayer(Bukkit.getPlayer(player)).removeLevel(amount);
    	} else {
    		sender.sendMessage(AuriUtils.colorString("&cThat player is offline or does not exist!"));
    		return;
    	}
    	
    }
    
    @Subcommand("removeXP")
    @CommandPermission("level.admin")
    @Description("Remove xp to a player") 
    @CommandCompletion("@players [amount] [title]")
    public void onRemoveXP(CommandSender sender, String player, Integer amount, boolean title) {
    	if (player != null) {
    		PlayerController.get().getLevelPlayer(Bukkit.getPlayer(player)).removeExperience(amount, title);
    	} else {
    		sender.sendMessage(AuriUtils.colorString("&cThat player is offline or does not exist!"));
    		return;
    	}
    	
    }
	
}

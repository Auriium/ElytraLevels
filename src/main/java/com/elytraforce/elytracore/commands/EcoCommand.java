package com.elytraforce.elytracore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.elytraforce.aUtils.chat.AChat;
import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.player.PlayerController;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

@CommandAlias("eco|economy|elytraeco")
public class EcoCommand extends BaseCommand{
	
	public static final String WILDCARD = "*";

    private final Main main;

    public EcoCommand(Main main) {
        this.main = main;
    }

    public Main getInstance() { return main; }
	
	@Subcommand("addMoney")
    @CommandPermission("elytraforce.admin")
    @Description("Add money to a player") 
    @CommandCompletion("@players [amount] [showMessage]")
    public void onAddMoney(CommandSender sender, String player, Integer amount, boolean showMessage) {
    	
    	if (player != null) {
    	    PlayerController.get().addMoney(Bukkit.getPlayer(player),amount,showMessage);
    	} else {
    		sender.sendMessage(AChat.colorString("&cThat player is offline or does not exist!"));
        }
    	
    }
	
	@Subcommand("removeMoney")
    @CommandPermission("elytraforce.admin")
    @Description("Remove money to a player") 
    @CommandCompletion("@players [amount] [showMessage]")
    public void onRemoveMoney(CommandSender sender, String player, Integer amount, boolean showMessage) {
    	
    	if (player != null) {
            PlayerController.get().removeMoney(Bukkit.getPlayer(player),amount,showMessage);
    	} else {
    		sender.sendMessage(AChat.colorString("&cThat player is offline or does not exist!"));
    	}
    }
    
    @Subcommand("setMoney")
    @CommandPermission("elytraforce.admin")
    @Description("Set money of a player") 
    @CommandCompletion("@players [amount] [showMessage]")
    public void onSetMoney(CommandSender sender, String player, Integer amount, boolean showMessage) {
    	
    	if (player != null) {
    		PlayerController.get().setMoney(Bukkit.getPlayer(player),amount,showMessage);
    	} else {
    		sender.sendMessage(AChat.colorString("&cThat player is offline or does not exist!"));
    	}
    }
}

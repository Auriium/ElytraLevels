package com.elytraforce.elytracore.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.player.PlayerController;
import com.elytraforce.elytracore.utils.AuriUtils;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;

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
    		sender.sendMessage(AuriUtils.colorString("&cThat player is offline or does not exist!"));
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
    		sender.sendMessage(AuriUtils.colorString("&cThat player is offline or does not exist!"));
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
    		sender.sendMessage(AuriUtils.colorString("&cThat player is offline or does not exist!"));
    	}
    }
}

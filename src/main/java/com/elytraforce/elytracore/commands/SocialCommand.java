package com.elytraforce.elytracore.commands;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.player.ElytraPlayer;
import com.elytraforce.elytracore.player.PlayerController;
import com.elytraforce.elytracore.utils.MessageUtils;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;

@CommandAlias("socials")
public class SocialCommand extends BaseCommand {
	
	public static final String WILDCARD = "*";

    private final Main main;

    public SocialCommand(Main main) {
        this.main = main;
    }

    public Main getInstance() { return main; }
	
    @CommandAlias("discord")
	@Subcommand("discord")
    @Description("View the discord") 
    public void onDiscord(Player sender) {
    	
    	if (sender instanceof Player) {
    		ElytraPlayer player = PlayerController.get().getLevelPlayer((Player) sender);
    		MessageUtils.discordMessage(player);
    	}
    	
    }
    
    @CommandAlias("site|website")
	@Subcommand("site")
    @Description("View the webpage") 
    public void onWebsite(CommandSender sender) {
    	
    	if (sender instanceof Player) {
    		ElytraPlayer player = PlayerController.get().getLevelPlayer((Player) sender);
    		MessageUtils.websiteMessage(player);
    	}
    	
    }
    
    @CommandAlias("store|buy")
	@Subcommand("store")
    @Description("View the shop") 
    public void onShop(CommandSender sender) {
    	
    	if (sender instanceof Player) {
    		ElytraPlayer player = PlayerController.get().getLevelPlayer((Player) sender);
    		MessageUtils.storeMessage(player);
    	}
    	
    }

}

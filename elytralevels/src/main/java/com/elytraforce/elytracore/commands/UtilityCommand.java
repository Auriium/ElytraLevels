package com.elytraforce.elytracore.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.config.PluginConfig;
import com.elytraforce.elytracore.player.ElytraPlayer;
import com.elytraforce.elytracore.player.PlayerController;
import com.elytraforce.elytracore.player.UtilityController;
import com.elytraforce.elytracore.utils.AuriUtils;
import com.elytraforce.elytracore.utils.MessageController;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Subcommand;

public class UtilityCommand extends BaseCommand{

	public static final String WILDCARD = "*";

    private final Main main;

    public UtilityCommand(Main main) {
        this.main = main;
    }

    public Main getInstance() { return main; }
    
    @CommandAlias("invsee")
	@Subcommand("invsee")
    @Description("Check a player's inventory") 
    @CommandPermission("elytraforce.mod")
    @CommandCompletion("@players ")
    public void invsee(Player sender, String t) {
    	
    	if (sender instanceof Player) {
    		ElytraPlayer player = PlayerController.get().getLevelPlayer((Player) sender);

    		if (t == null) { AuriUtils.sendMessage(player, PluginConfig.getPrefix() + "&cTarget must be a player!"); return;}
    		
    		ElytraPlayer target = PlayerController.get().getLevelPlayer(Bukkit.getPlayer(t));
    		
    		UtilityController.get().invsee(player, target);
    	}
    	
    }
    
    @CommandAlias("god|godmode")
	@Subcommand("god")
    @Description("Set your godmode") 
    @CommandPermission("elytraforce.admin")
    @CommandCompletion("@players ")
    public void onGod(CommandSender sender, @Optional String player2) {
    	ElytraPlayer player;
    	
    	if (sender instanceof Player) {
    		if (player2 != null) {
    			player = PlayerController.get().getLevelPlayer(Bukkit.getPlayer(player2));
    		} else {
    			player = PlayerController.get().getLevelPlayer((Player) sender);
    		}
    		UtilityController.get().toggleGodMode(player);
    	}
    	
    }
	
    @CommandAlias("fly")
	@Subcommand("fly")
    @Description("Set your flymode") 
    @CommandPermission("elytraforce.mod")
    @CommandCompletion("@players ")
    public void onFly(CommandSender sender, @Optional String player2) {
    	ElytraPlayer player;
    	
    	if (sender instanceof Player) {
    		if (player2 != null) {
    			player = PlayerController.get().getLevelPlayer(Bukkit.getPlayer(player2));
    		} else {
    			player = PlayerController.get().getLevelPlayer((Player) sender);
    		}
    		UtilityController.get().setFlying(player);
    	}
    	
    }
    
    @CommandAlias("gm|gamemode")
	@Subcommand("gm")
    @Description("Switch your gamemode") 
    @CommandPermission("elytraforce.admin")
    @CommandCompletion("@players ")
    public void onGamemode(CommandSender sender, GameMode mode, @Optional String player2) {
    	ElytraPlayer player;
    	if (sender instanceof Player) {
    		if (player2 != null) {
    			player = PlayerController.get().getLevelPlayer(Bukkit.getPlayer(player2));
    		} else {
    			player = PlayerController.get().getLevelPlayer((Player) sender);
    		}
    		UtilityController.get().setGamemode(player, mode);
    	}
    	
    }
    
    @CommandAlias("gms")
	@Subcommand("gms")
    @Description("Switch your gamemode") 
    @CommandPermission("elytraforce.admin")
    @CommandCompletion("@players ")
    public void onGMS(CommandSender sender, @Optional String player2) {
    	ElytraPlayer player;
    	if (sender instanceof Player) {
    		if (player2 != null) {
    			player = PlayerController.get().getLevelPlayer(Bukkit.getPlayer(player2));
    		} else {
    			player = PlayerController.get().getLevelPlayer((Player) sender);
    		}
    		UtilityController.get().setGamemode(player, GameMode.SURVIVAL);
    	}
    	
    }
    
    @CommandAlias("gmc")
	@Subcommand("gmc")
    @Description("Switch your gamemode") 
    @CommandPermission("elytraforce.admin")
    @CommandCompletion("@players ")
    public void onGMC(CommandSender sender, @Optional String player2) {
    	ElytraPlayer player;
    	if (sender instanceof Player) {
    		if (player2 != null) {
    			player = PlayerController.get().getLevelPlayer(Bukkit.getPlayer(player2));
    		} else {
    			player = PlayerController.get().getLevelPlayer((Player) sender);
    		}

    		UtilityController.get().setGamemode(player, GameMode.CREATIVE);
    	}
    	
    }
    
    @CommandAlias("gma")
	@Subcommand("gma")
    @Description("Switch your gamemode") 
    @CommandPermission("elytraforce.admin")
    @CommandCompletion("@players ")
    public void onGMA(CommandSender sender, @Optional String player2) {
    	ElytraPlayer player;
    	if (sender instanceof Player) {
    		if (player2 != null) {
    			player = PlayerController.get().getLevelPlayer(Bukkit.getPlayer(player2));
    		} else {
    			player = PlayerController.get().getLevelPlayer((Player) sender);
    		}
    		UtilityController.get().setGamemode(player, GameMode.ADVENTURE);
    	}
    	
    }
    
    @CommandAlias("gmsp")
	@Subcommand("gmsp")
    @Description("Switch your gamemode") 
    @CommandPermission("elytraforce.admin")
    @CommandCompletion("@players ")
    public void onGMSP(CommandSender sender, @Optional String player2) {
    	ElytraPlayer player;
    	if (sender instanceof Player) {
    		if (player2 != null) {
    			player = PlayerController.get().getLevelPlayer(Bukkit.getPlayer(player2));
    		} else {
    			player = PlayerController.get().getLevelPlayer((Player) sender);
    		}
    		UtilityController.get().setGamemode(player, GameMode.SPECTATOR);
    	}
    	
    }
    
    @CommandAlias("heal")
	@Subcommand("heal")
    @Description("Heal yourself") 
    @CommandPermission("elytraforce.admin")
    @CommandCompletion("@players ")
    public void onHeal(CommandSender sender, @Optional String player2) {
    	ElytraPlayer player;
    	if (sender instanceof Player) {
    		if (player2 != null) {
    			player = PlayerController.get().getLevelPlayer(Bukkit.getPlayer(player2));
    		} else {
    			player = PlayerController.get().getLevelPlayer((Player) sender);
    		}
    		UtilityController.get().heal(player);
    	}
    	
    }
    
    @CommandAlias("feed")
	@Subcommand("feed")
    @Description("Feed yourself") 
    @CommandPermission("elytraforce.helper")
    @CommandCompletion("@players ")
    public void onFeed(CommandSender sender, @Optional String player2) {
    	ElytraPlayer player;
    	if (sender instanceof Player) {
    		if (player2 != null) {
    			player = PlayerController.get().getLevelPlayer(Bukkit.getPlayer(player2));
    		} else {
    			player = PlayerController.get().getLevelPlayer((Player) sender);
    		}
    		UtilityController.get().feed(player);
    	}
    }
    
    
	
}

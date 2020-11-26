package com.elytraforce.elytracore.commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.player.ElytraPlayer;
import com.elytraforce.elytracore.player.PlayerController;
import com.elytraforce.elytracore.player.UtilityController;
import com.elytraforce.elytracore.utils.AuriUtils;
import com.elytraforce.elytracore.utils.MessageUtils;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Optional;
import co.aikar.commands.annotation.Subcommand;

@SuppressWarnings("unused")
public class UtilityCommand extends BaseCommand{

	public static final String WILDCARD = "*";

    private final Main main;

    public UtilityCommand(Main main) {
        this.main = main;
    }

    public Main getInstance() { return main; }
    
    @CommandAlias("kill")
	@Subcommand("kill")
    @Description("Kill a player instantly") 
    @CommandPermission("elytraforce.admin")
    @CommandCompletion("@players ")
    public void kill(Player sender, @Optional String t) {
    	
    	ElytraPlayer player;
    	ElytraPlayer target;

    		player = PlayerController.get().getElytraPlayer(sender);
    		if (t != null) {
    			try {
    				target = PlayerController.get().getElytraPlayer(Bukkit.getPlayer(t));
    			} catch (NullPointerException e) {
    				MessageUtils.invalidTarget(player);
    				return;
    			}
    		} else {
    			target = player;
    		}
    		UtilityController.get().toggleGodMode(player,target);
    	
    }
    
    @CommandAlias("teleporthere|tphere")
	@Subcommand("teleporthere")
    @Description("Check a player's inventory") 
    @CommandPermission("elytraforce.mod")
    @CommandCompletion("@players ")
    public void onTPHere(Player sender, String t) {
    		ElytraPlayer player = PlayerController.get().getElytraPlayer(sender);

    		if (t == null) { AuriUtils.sendMessage(player, Main.getAConfig().pluginPrefix + "&cTarget must be a player!"); return;}
    		
    		try {
				ElytraPlayer target = PlayerController.get().getElytraPlayer(Bukkit.getPlayer(t));
				UtilityController.get().teleportHere(player, target);
			} catch (NullPointerException e) {
				MessageUtils.invalidTarget(player);
			}
    }
    
    @CommandAlias("teleport|tp")
	@Subcommand("teleport")
    @Description("Check a player's inventory") 
    @CommandPermission("elytraforce.mod")
    @CommandCompletion("@players ")
    public void onTP(Player sender, String t) {

    		ElytraPlayer player = PlayerController.get().getElytraPlayer(sender);

    		if (t == null) { AuriUtils.sendMessage(player, Main.getAConfig().pluginPrefix + "&cTarget must be a player!"); return;}
    		
    		try {
				ElytraPlayer target = PlayerController.get().getElytraPlayer(Bukkit.getPlayer(t));
				UtilityController.get().teleport(player, target);
			} catch (NullPointerException e) {
				MessageUtils.invalidTarget(player);
			}
    	
    }
    
    @CommandAlias("invsee")
	@Subcommand("invsee")
    @Description("Check a player's inventory") 
    @CommandPermission("elytraforce.mod")
    @CommandCompletion("@players ")
    public void invsee(Player sender, String t) {

    		ElytraPlayer player = PlayerController.get().getElytraPlayer(sender);

    		if (t == null) { AuriUtils.sendMessage(player, Main.getAConfig().pluginPrefix + "&cTarget must be a player!"); return;}
    		
    		try {
				ElytraPlayer target = PlayerController.get().getElytraPlayer(Bukkit.getPlayer(t));
				UtilityController.get().invsee(player, target);
			} catch (NullPointerException e) {
				MessageUtils.invalidTarget(player);
			}
    }
    
    @CommandAlias("god|godmode")
	@Subcommand("god")
    @Description("Set your godmode") 
    @CommandPermission("elytraforce.admin")
    @CommandCompletion("@players ")
    public void onGod(CommandSender sender, @Optional String player2) {
    	ElytraPlayer player;
    	ElytraPlayer target;

    		player = PlayerController.get().getElytraPlayer((Player) sender);
    		if (player2 != null) {
    			try {
    				target = PlayerController.get().getElytraPlayer(Bukkit.getPlayer(player2));
    			} catch (NullPointerException e) {
    				MessageUtils.invalidTarget(player);
    				return;
    			}
    		} else {
    			target = player;
    		}
    		UtilityController.get().toggleGodMode(player,target);
    	
    }
	
    @CommandAlias("fly")
	@Subcommand("fly")
    @Description("Set your flymode") 
    @CommandPermission("elytraforce.mod")
    @CommandCompletion("@players ")
    public void onFly(Player sender, @Optional String player2) {
    	ElytraPlayer player;
    	ElytraPlayer target;
    		player = PlayerController.get().getElytraPlayer(sender);
    		if (player2 != null) {
    			try {
    				target = PlayerController.get().getElytraPlayer(Bukkit.getPlayer(player2));
    			} catch (NullPointerException e) {
    				MessageUtils.invalidTarget(player);
    				return;
    			}
    		} else {
    			target = player;
    		}
    		UtilityController.get().setFlying(player,target);
    }
    
    @CommandAlias("gm|gamemode")
	@Subcommand("gm")
    @Description("Switch your gamemode") 
    @CommandPermission("elytraforce.admin")
    @CommandCompletion("@players ")
    public void onGamemode(Player sender, GameMode mode, @Optional String player2) {
    	ElytraPlayer player;
    	ElytraPlayer target;
    		player = PlayerController.get().getElytraPlayer(sender);
    		if (player2 != null) {
    			try {
    				target = PlayerController.get().getElytraPlayer(Bukkit.getPlayer(player2));
    			} catch (NullPointerException e) {
    				MessageUtils.invalidTarget(player);
    				return;
    			}
    		} else {
    			target = player;
    		}
    		UtilityController.get().setGamemode(player, target, mode);
    }
    
    @CommandAlias("gms")
	@Subcommand("gms")
    @Description("Switch your gamemode") 
    @CommandPermission("elytraforce.admin")
    @CommandCompletion("@players ")
    public void onGMS(Player sender, @Optional String player2) {
    	ElytraPlayer player;
    	ElytraPlayer target;
    		player = PlayerController.get().getElytraPlayer(sender);
    		if (player2 != null) {
    			try {
    				target = PlayerController.get().getElytraPlayer(Bukkit.getPlayer(player2));
    			} catch (NullPointerException e) {
    				MessageUtils.invalidTarget(player);
    				return;
    			}
    		} else {
    			target = player;
    		}
    		UtilityController.get().setGamemode(player, target, GameMode.SURVIVAL);
    }
    
    @CommandAlias("gmc")
	@Subcommand("gmc")
    @Description("Switch your gamemode") 
    @CommandPermission("elytraforce.admin")
    @CommandCompletion("@players ")
    public void onGMC(Player sender, @Optional String player2) {
    	ElytraPlayer player;
    	ElytraPlayer target;
    		player = PlayerController.get().getElytraPlayer(sender);
    		if (player2 != null) {
    			try {
    				target = PlayerController.get().getElytraPlayer(Bukkit.getPlayer(player2));
    			} catch (NullPointerException e) {
    				MessageUtils.invalidTarget(player);
    				return;
    			}
    		} else {
    			target = player;
    		}
    		UtilityController.get().setGamemode(player, target, GameMode.CREATIVE);
    }
    
    @CommandAlias("gma")
	@Subcommand("gma")
    @Description("Switch your gamemode") 
    @CommandPermission("elytraforce.admin")
    @CommandCompletion("@players ")
    public void onGMA(Player sender, @Optional String player2) {
    	ElytraPlayer player;
    	ElytraPlayer target;
    		player = PlayerController.get().getElytraPlayer(sender);
    		if (player2 != null) {
    			try {
    				target = PlayerController.get().getElytraPlayer(Bukkit.getPlayer(player2));
    			} catch (NullPointerException e) {
    				MessageUtils.invalidTarget(player);
    				return;
    			}
    		} else {
    			target = player;
    		}
    		UtilityController.get().setGamemode(player, target, GameMode.ADVENTURE);
    }
    
    @CommandAlias("gmsp")
	@Subcommand("gmsp")
    @Description("Switch your gamemode") 
    @CommandPermission("elytraforce.admin")
    @CommandCompletion("@players ")
    public void onGMSP(Player sender, @Optional String player2) {
    	ElytraPlayer player;
    	ElytraPlayer target;
    		player = PlayerController.get().getElytraPlayer(sender);
    		if (player2 != null) {
    			try {
    				target = PlayerController.get().getElytraPlayer(Bukkit.getPlayer(player2));
    			} catch (NullPointerException e) {
    				MessageUtils.invalidTarget(player);
    				return;
    			}
    		} else {
    			target = player;
    		}
    		UtilityController.get().setGamemode(player, target, GameMode.SPECTATOR);
    }
    
    @CommandAlias("heal")
	@Subcommand("heal")
    @Description("Heal yourself") 
    @CommandPermission("elytraforce.admin")
    @CommandCompletion("@players ")
    public void onHeal(Player sender, @Optional String player2) {
    	ElytraPlayer player;
    	ElytraPlayer target;
    		player = PlayerController.get().getElytraPlayer(sender);
    		if (player2 != null) {
    			try {
    				target = PlayerController.get().getElytraPlayer(Bukkit.getPlayer(player2));
    			} catch (NullPointerException e) {
    				MessageUtils.invalidTarget(player);
    				return;
    			}
    		} else {
    			target = player;
    		}
    		UtilityController.get().heal(player, target);
    }
    
    @CommandAlias("feed")
	@Subcommand("feed")
    @Description("Feed yourself") 
    @CommandPermission("elytraforce.helper")
    @CommandCompletion("@players ")
    public void onFeed(Player sender, @Optional String player2) {
    	ElytraPlayer player;
    	ElytraPlayer target;
    		player = PlayerController.get().getElytraPlayer(sender);
    		if (player2 != null) {
    			try {
    				target = PlayerController.get().getElytraPlayer(Bukkit.getPlayer(player2));
    			} catch (NullPointerException e) {
    				MessageUtils.invalidTarget(player);
    				return;
    			}
    		} else {
    			target = player;
    		}
    		UtilityController.get().feed(player, target);
    }

	@CommandAlias("speed")
	@Subcommand("speed")
	@Description("Speed yourself (I copy pasted this)")
	@CommandPermission("elytraforce.helper")
	@CommandCompletion("@players ")
	public void onSpeed(Player sender, @Optional String player2, int value) {
		ElytraPlayer player;
		ElytraPlayer target;
		player = PlayerController.get().getElytraPlayer(sender);
		if (player2 != null) {
			try {
				target = PlayerController.get().getElytraPlayer(Bukkit.getPlayer(player2));
			} catch (NullPointerException e) {
				MessageUtils.invalidTarget(player);
				return;
			}
		} else {
			target = player;
		}
		UtilityController.get().speed(player, target, value);
	}
    
    
	
}

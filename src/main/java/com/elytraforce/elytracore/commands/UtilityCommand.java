package com.elytraforce.elytracore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.player.ElytraPlayer;
import com.elytraforce.elytracore.player.PlayerController;
import com.elytraforce.elytracore.player.UtilityController;
import com.elytraforce.elytracore.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

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
    				MessageUtils.invalidTarget(player, t);
    				return;
    			}
    		} else {
    			target = player;
    		}
    		UtilityController.get().toggleGodMode(player,target);
    	
    }
    
    @CommandAlias("teleporthere|tphere")
	@Subcommand("teleporthere")
    @Description("Teleport there or here!") 
    @CommandPermission("elytraforce.mod")
    @CommandCompletion("@players ")
    public void onTPHere(Player sender, String t, @Optional String t2) {

    		ElytraPlayer send = PlayerController.get().getElytraPlayer(sender);

    		try {
				ElytraPlayer point;
				ElytraPlayer target;

				if (t2 != null) {
					point = PlayerController.get().getElytraPlayer(Objects.requireNonNull(Bukkit.getPlayer(t)));
					target = PlayerController.get().getElytraPlayer(Objects.requireNonNull(Bukkit.getPlayer(t2)));
				} else {
					point = PlayerController.get().getElytraPlayer(sender);
					target = PlayerController.get().getElytraPlayer(Objects.requireNonNull(Bukkit.getPlayer(t)));
				}

				UtilityController.get().teleportHere(send,point, target);
			} catch (NullPointerException e) {
				MessageUtils.invalidTarget(send,t);
			}
    }
    
    @CommandAlias("teleport|tp")
	@Subcommand("teleport")
    @Description("Teleport to someone!") 
    @CommandPermission("elytraforce.mod")
    @CommandCompletion("@players ")
    public void onTP(Player sender, String t, @Optional String t2) {

		ElytraPlayer send = PlayerController.get().getElytraPlayer(sender);

		try {
			ElytraPlayer point;
			ElytraPlayer target;

			if (t2 != null) {
				point = PlayerController.get().getElytraPlayer(Objects.requireNonNull(Bukkit.getPlayer(t)));
				target = PlayerController.get().getElytraPlayer(Objects.requireNonNull(Bukkit.getPlayer(t2)));
			} else {
				point = PlayerController.get().getElytraPlayer(sender);
				target = PlayerController.get().getElytraPlayer(Objects.requireNonNull(Bukkit.getPlayer(t)));
			}

			UtilityController.get().teleportHere(send,point, target);
		} catch (NullPointerException e) {
			MessageUtils.invalidTarget(send,t);
		}
    	
    }
    
    @CommandAlias("invsee")
	@Subcommand("invsee")
    @Description("Check a player's inventory") 
    @CommandPermission("elytraforce.mod")
    @CommandCompletion("@players ")
    public void invsee(Player sender, String t) {

    		ElytraPlayer player = PlayerController.get().getElytraPlayer(sender);

    		if (t == null) { sender.sendMessage(Main.getAConfig().pluginPrefix + "&cTarget must be a player!"); return; }
    		
    		try {
				ElytraPlayer target = PlayerController.get().getElytraPlayer(Bukkit.getPlayer(t));
				UtilityController.get().invsee(player, target);
			} catch (NullPointerException e) {
				MessageUtils.invalidTarget(player,t);
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
    				MessageUtils.invalidTarget(player,player2);
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
    				MessageUtils.invalidTarget(player,player2);
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
    				MessageUtils.invalidTarget(player,player2);
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
					MessageUtils.invalidTarget(player,player2);
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
					MessageUtils.invalidTarget(player,player2);
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
					MessageUtils.invalidTarget(player,player2);
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
					MessageUtils.invalidTarget(player,player2);
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
					MessageUtils.invalidTarget(player,player2);
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
					MessageUtils.invalidTarget(player,player2);
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
				MessageUtils.invalidTarget(player,player2);
				return;
			}
		} else {
			target = player;
		}
		UtilityController.get().speed(player, target, value);
	}
    
    
	
}

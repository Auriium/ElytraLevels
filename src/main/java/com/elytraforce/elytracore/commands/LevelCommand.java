package com.elytraforce.elytracore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.elytraforce.aUtils.chat.AChat;
import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.player.ElytraPlayer;
import com.elytraforce.elytracore.player.GUIController;
import com.elytraforce.elytracore.player.PlayerController;
import com.elytraforce.elytracore.player.redis.Delta;
import com.elytraforce.elytracore.player.redis.enums.DeltaEnum;
import com.elytraforce.elytracore.player.redis.enums.ValueEnum;
import com.elytraforce.elytracore.rewards.RewardController;
import com.elytraforce.elytracore.storage.SQLStorage;
import com.elytraforce.elytracore.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
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

        ElytraPlayer p = PlayerController.get().getElytraPlayer(player);

        if (player2 == null) {
            MessageUtils.balanceMessage(p,p);
        } else {
            Player p2 = Bukkit.getPlayer(player2);
            if (p2 == null) {
                SQLStorage.get().getIDFromUsername(player2).thenAccept(accept -> {
                    if (accept == null) {
                        MessageUtils.invalidTarget(p,player2); return;}

                    SQLStorage.get().getOrDefaultPlayer(accept, true).thenAccept(p3 -> {
                        GUIController.get().showProfile(p, p3);
                    });
                });
            } else {
                //ONLINE
                ElytraPlayer target = PlayerController.get().getElytraPlayer(p2);
                GUIController.get().showProfile(p, target);
            }
        }

    }
    
    @Subcommand("rewards")
    @CommandAlias("rewards")
    @Description("Add levels to a player") 
    @CommandCompletion("@players [amount] [title] [showMessage]")
    public void onRewardsCommands(Player sender) {
    		RewardController.get().showMenu(PlayerController.get().getElytraPlayer(sender));
    }
    
    @Subcommand("addLevel")
    @CommandPermission("elyraforce.admin")
    @Description("Add levels to a player")
    @CommandCompletion("@players [amount] [title] [showMessage]")
    public void onAddLevel(Player sender, String target, Integer amount, boolean title, boolean showMessage) {
        ElytraPlayer p1 = PlayerController.get().getElytraPlayer((OfflinePlayer) sender);

        Player bukkitPlayer = Bukkit.getPlayer(target);
        if (bukkitPlayer == null) {
            SQLStorage.get().getIDFromUsername(target).thenAccept(accept -> {
                if (accept == null) {
                    MessageUtils.invalidTarget(p1,target); return;}
                SQLStorage.get().getOrDefaultPlayer(accept, true).thenAccept(p2 -> {
                    MessageUtils.addLevelMessage(p1,p2);
                    p2.addChange(new Delta(accept,amount, DeltaEnum.INCREASE, ValueEnum.LEVEL));
                    p2.update();
                });
            });
        } else {
            PlayerController.get().addLevel(PlayerController.get().getElytraPlayer(bukkitPlayer),amount,title,showMessage);
        }
    }
    
    @Subcommand("addXP")
    @CommandPermission("elytraforce.admin")
    @Description("Add xp to a player") 
    @CommandCompletion("@players [amount] [title] [showMessage]")
    public void onAddXP(Player sender, String target, Integer amount, boolean title, boolean showMessage) {
        ElytraPlayer p1 = PlayerController.get().getElytraPlayer((OfflinePlayer) sender);

        Player bukkitPlayer = Bukkit.getPlayer(target);
        if (bukkitPlayer == null) {
            SQLStorage.get().getIDFromUsername(target).thenAccept(accept -> {
                if (accept == null) {
                    MessageUtils.invalidTarget(p1,target); return;}
                SQLStorage.get().getOrDefaultPlayer(accept, true).thenAccept(p2 -> {
                    MessageUtils.addXPMessage(p1,p2,amount);
                    p2.addChange(new Delta(accept,amount, DeltaEnum.INCREASE, ValueEnum.XP));
                    p2.update();
                });
            });
        } else {
            PlayerController.get().addXP(PlayerController.get().getElytraPlayer(bukkitPlayer),amount,title,showMessage);
        }
    }
     
    @Subcommand("removeLevel")
    @CommandPermission("elytraforce.admin")
    @Description("Remove levels to a player") 
    @CommandCompletion("@players [amount] [title] [showMessage]")
    public void onRemoveLevel(Player sender, String target, Integer amount, boolean title, boolean showMessage) {
        ElytraPlayer p1 = PlayerController.get().getElytraPlayer((OfflinePlayer) sender);

        Player bukkitPlayer = Bukkit.getPlayer(target);
        if (bukkitPlayer == null) {
            SQLStorage.get().getIDFromUsername(target).thenAccept(accept -> {
                if (accept == null) {
                    MessageUtils.invalidTarget(p1,target); return;}
                SQLStorage.get().getOrDefaultPlayer(accept, true).thenAccept(p2 -> {
                    MessageUtils.removeLevelMessage(p1,p2);
                    p2.addChange(new Delta(accept,Math.min(amount,p2.getLevel()), DeltaEnum.DECREASE, ValueEnum.LEVEL));
                    p2.update();
                });
            });
        } else {
            PlayerController.get().removeLevel(PlayerController.get().getElytraPlayer(bukkitPlayer),amount,title,showMessage);
        }
    }
    
    @Subcommand("removeXP")
    @CommandPermission("elytraforce.admin")
    @Description("Remove xp to a player") 
    @CommandCompletion("@players [amount] [title] [showMessage]")
    public void onRemoveXP(Player sender, String target, Integer amount, boolean title, boolean showMessage) {
        ElytraPlayer p1 = PlayerController.get().getElytraPlayer((OfflinePlayer) sender);

        Player bukkitPlayer = Bukkit.getPlayer(target);
        if (bukkitPlayer == null) {
            SQLStorage.get().getIDFromUsername(target).thenAccept(accept -> {
                if (accept == null) {
                    MessageUtils.invalidTarget(p1,target); return;}
                SQLStorage.get().getOrDefaultPlayer(accept, true).thenAccept(p2 -> {
                    MessageUtils.removeXPMessage(p1,p2,amount);
                    p2.addChange(new Delta(accept,amount, DeltaEnum.DECREASE, ValueEnum.XP));
                    p2.update();
                });
            });
        } else {
            PlayerController.get().removeXP(PlayerController.get().getElytraPlayer(bukkitPlayer),amount,title,showMessage);
        }
    }
	
}

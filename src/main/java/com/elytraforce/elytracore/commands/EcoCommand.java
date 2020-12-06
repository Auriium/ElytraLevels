package com.elytraforce.elytracore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.elytraforce.aUtils.ALogger;
import com.elytraforce.aUtils.chat.AChat;
import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.events.ChangeEnum;
import com.elytraforce.elytracore.player.ElytraPlayer;
import com.elytraforce.elytracore.player.PlayerController;
import com.elytraforce.elytracore.player.redis.Delta;
import com.elytraforce.elytracore.player.redis.enums.DeltaEnum;
import com.elytraforce.elytracore.player.redis.enums.ValueEnum;
import com.elytraforce.elytracore.storage.SQLStorage;
import com.elytraforce.elytracore.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
    public void onAddMoney(CommandSender sender, String target, Integer amount, boolean showMessage) {

        if (!(sender instanceof Player)) { return; }
        ElytraPlayer p1 = PlayerController.get().getElytraPlayer((OfflinePlayer) sender);

            Player bukkitPlayer = Bukkit.getPlayer(target);
            if (bukkitPlayer == null) {
                SQLStorage.get().getIDFromUsername(target).thenAccept(accept -> {
                    if (accept == null) {
                        MessageUtils.invalidTarget(p1,target); return;}
                    SQLStorage.get().getOrDefaultPlayer(accept, true).thenAccept(p2 -> {
                        MessageUtils.addMoneyMessage(p1,p2,amount);
                        p2.addChange(new Delta(accept,amount, DeltaEnum.INCREASE, ValueEnum.MONEY));
                        p2.update();
                    });
                });
            } else {
                PlayerController.get().addMoney(PlayerController.get().getElytraPlayer(bukkitPlayer),amount,showMessage);
            }
    }
	
	@Subcommand("removeMoney")
    @CommandPermission("elytraforce.admin")
    @Description("Remove money to a player") 
    @CommandCompletion("@players [amount] [showMessage]")
    public void onRemoveMoney(CommandSender sender, String target, Integer amount, boolean showMessage) {

        if (!(sender instanceof Player)) { return; }
        ElytraPlayer p1 = PlayerController.get().getElytraPlayer((OfflinePlayer) sender);

        Player bukkitPlayer = Bukkit.getPlayer(target);
        if (bukkitPlayer == null) {
            SQLStorage.get().getIDFromUsername(target).thenAccept(accept -> {
                if (accept == null) {
                    MessageUtils.invalidTarget(p1,target); return;}
                SQLStorage.get().getOrDefaultPlayer(accept, true).thenAccept(p2 -> {
                    MessageUtils.addMoneyMessage(p1,p2,amount);
                    p2.addChange(new Delta(accept,Math.min(p2.getMoney(),Math.abs(amount)), DeltaEnum.DECREASE, ValueEnum.MONEY));
                    p2.update();
                });
            });
        } else {
            PlayerController.get().removeMoney(PlayerController.get().getElytraPlayer(bukkitPlayer),amount,showMessage);
        }
    }

}

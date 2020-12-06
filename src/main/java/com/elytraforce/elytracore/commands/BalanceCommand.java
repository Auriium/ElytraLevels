package com.elytraforce.elytracore.commands;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import com.elytraforce.aUtils.ALogger;
import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.player.ElytraPlayer;
import com.elytraforce.elytracore.player.PlayerController;
import com.elytraforce.elytracore.storage.SQLStorage;
import com.elytraforce.elytracore.utils.MessageUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

@CommandAlias("bal|balance")
public class BalanceCommand extends BaseCommand{
	
	public static final String WILDCARD = "*";

    private final Main main;

    public BalanceCommand(Main main) {
        this.main = main;
    } 

    public Main getInstance() { return main; }
    
    @Default
    @CommandCompletion("@players @players")
    @Description("Lists your or others balances")
    public static void onBalance(Player player, @Optional String player2) {
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
                        MessageUtils.balanceMessage(p,p3);
                    });
                });
            } else {
                MessageUtils.balanceMessage(p,PlayerController.get().getElytraPlayer(p2));
            }
    	}
    }
}

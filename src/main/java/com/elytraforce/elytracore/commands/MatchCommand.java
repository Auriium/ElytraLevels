package com.elytraforce.elytracore.commands;

import java.util.ArrayList;
import java.util.UUID;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.matchtracker.TrackerController;
import com.elytraforce.elytracore.matchtracker.menu.DefaultMenu;
import com.elytraforce.elytracore.player.ElytraPlayer;
import com.elytraforce.elytracore.player.PlayerController;
import com.elytraforce.elytracore.utils.MessageUtils;

import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandCompletion;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Description;
import co.aikar.commands.annotation.Subcommand;

@CommandAlias("match")
@CommandPermission("elytraforce.admin")
public class MatchCommand extends BaseCommand{
	
	public static final String WILDCARD = "*";

    private final Main main;

    public MatchCommand(Main main) {
        this.main = main;
    }

    public Main getInstance() { return main; }

	@Subcommand("track")
    @CommandPermission("elyraforce.admin")
    @Description("Track a uuid match") 
    @CommandCompletion("[uuid]")
    public void onTrack(CommandSender sender, String name) {
		
			Player player = (Player) sender;
			ArrayList<ElytraPlayer> listPlayers = new ArrayList<ElytraPlayer>();
			
			for (Player p : player.getWorld().getPlayers()) {
				listPlayers.add(PlayerController.get().getLevelPlayer(player));
			}
		
    		TrackerController.get().beginTracker(UUID.fromString(name), listPlayers);
    		
    		MessageUtils.matchBeginTrackingMessage(PlayerController.get().getLevelPlayer(player), name);
    }
	
	@Subcommand("stop")
    @CommandPermission("elyraforce.admin")
    @Description("Stop tracking a uuid, debug command (This should be called through the API)") 
    @CommandCompletion("[uuid]")
    public void onStop(CommandSender sender, String name) {
		
			Player player = (Player) sender;
		
    		TrackerController.get().endTracker(UUID.fromString(name),new DefaultMenu("&c&lMatch&f&lTest"));
    		MessageUtils.matchEndTrackingMessage(PlayerController.get().getLevelPlayer(player), name);
    }
	
	
}

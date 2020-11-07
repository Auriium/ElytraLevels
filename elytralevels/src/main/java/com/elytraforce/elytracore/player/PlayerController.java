package com.elytraforce.elytracore.player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.ipvp.canvas.Menu;
import org.ipvp.canvas.mask.BinaryMask;
import org.ipvp.canvas.slot.Slot.ClickHandler;
import org.ipvp.canvas.slot.SlotSettings;
import org.ipvp.canvas.type.ChestMenu;

import com.elytraforce.elytracore.storage.SQLStorage;
import com.elytraforce.elytracore.utils.AuriUtils;
import com.elytraforce.elytracore.utils.ItemBuilder;

import me.clip.placeholderapi.PlaceholderAPI;



public class PlayerController {

    private static PlayerController instance;
    private HashSet<ElytraPlayer> players;
    
    private HashSet<ElytraPlayer> offlinePlayers;

    private PlayerController() {
        players = new HashSet<>();
    }

    public void playerJoined(Player player) {
        SQLStorage.get().loadPlayer(player);
    }
    
    public void joinOfflineCallback(Player player, int level, int experience, int money, List<Integer> unlockedRewards, boolean newPlayer) {
        if (player.isOnline()) {
            offlinePlayers.add(new ElytraPlayer(
                    player,
                    level,
                    experience,
                    money, 
                    unlockedRewards,
                    newPlayer
            ));
        }
    }
    
    public void completeOfflineStatement(ElytraPlayer player) {
    	if (player.isInDatabase()) {
            SQLStorage.get().updatePlayer(player, true);
        }
    	this.offlinePlayers.remove(player);
    }

    public void joinCallback(Player player, int level, int experience, int money, List<Integer> unlockedRewards, boolean newPlayer) {
        if (player.isOnline()) {
            players.add(new ElytraPlayer(
                    player,
                    level,
                    experience,
                    money, 
                    unlockedRewards,
                    newPlayer
            ));
        }
    }

    public void playerQuit(ElytraPlayer player) {


        if (player.isInDatabase()) {
            SQLStorage.get().updatePlayer(player, true);
        } else {
            SQLStorage.get().insertPlayer(player, true);
        }

        players.remove(player);
    }

    public HashSet<ElytraPlayer> getPlayers() {
        return players;
    }

    public ElytraPlayer getLevelPlayer(Player player) {
        return getLevelPlayer(player.getUniqueId());
    }

    public ElytraPlayer getLevelPlayer(UUID uuid) {
        for (ElytraPlayer elytraPlayer : players) {
            if (elytraPlayer.getUUID().equals(uuid))
                return elytraPlayer;
        }
        return null;
    }

    public static PlayerController get() {
        if (instance == null) {
            instance = new PlayerController();
        }
        return instance;
    }

}

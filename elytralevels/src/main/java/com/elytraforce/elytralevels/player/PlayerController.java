package com.elytraforce.elytralevels.player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;

import com.elytraforce.elytralevels.storage.SQLStorage;



public class PlayerController {

    private static PlayerController instance;
    private HashSet<LevelPlayer> players;

    private PlayerController() {

        players = new HashSet<>();
    }

    public void playerJoined(Player player) {
        SQLStorage.get().loadPlayer(player);
    }

    public void joinCallback(Player player, int level, int experience, int money, List<Integer> unlockedRewards, boolean newPlayer) {
        if (player.isOnline()) {
            players.add(new LevelPlayer(
                    player,
                    level,
                    experience,
                    money, 
                    unlockedRewards,
                    newPlayer
            ));
        }
    }

    public void playerQuit(LevelPlayer player) {


        if (player.isInDatabase()) {
            SQLStorage.get().updatePlayer(player, true);
        } else {
            SQLStorage.get().insertPlayer(player, true);
        }

        players.remove(player);
    }

    public HashSet<LevelPlayer> getPlayers() {
        return players;
    }

    public LevelPlayer getLevelPlayer(Player player) {
        return getLevelPlayer(player.getUniqueId());
    }

    public LevelPlayer getLevelPlayer(UUID uuid) {
        for (LevelPlayer levelPlayer : players) {
            if (levelPlayer.getUUID().equals(uuid))
                return levelPlayer;
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

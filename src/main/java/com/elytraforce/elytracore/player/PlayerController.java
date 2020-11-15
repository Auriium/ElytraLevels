package com.elytraforce.elytracore.player;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executors;

import com.elytraforce.elytracore.utils.AuriUtils;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import com.elytraforce.elytracore.storage.SQLStorage;



public class PlayerController {

    private static PlayerController instance;
    private HashSet<ElytraPlayer> players;

    private PlayerController() {
        players = new HashSet<>();
    }

    public void playerJoined(Player player) {
        SQLStorage.get().loadPlayer(player);
        //makes sure that if they are in the cache they are removed
        SQLStorage.get().removePlayerCached(player);
    }

    public void joinCallback(OfflinePlayer player, int level, int experience, int money, List<Integer> unlockedRewards, boolean newPlayer) {
            players.add(new ElytraPlayer(
                    player,
                    level,
                    experience,
                    money, 
                    unlockedRewards,
                    newPlayer
            ));
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

    public ElytraPlayer getLevelPlayer(OfflinePlayer player) {
        return getLevelPlayer(player.getUniqueId());
    }

    // // // // // // // // // //


    // // // // // // // // // //

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

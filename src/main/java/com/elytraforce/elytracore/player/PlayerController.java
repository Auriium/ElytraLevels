package com.elytraforce.elytracore.player;

import com.elytraforce.aUtils.chat.AChat;
import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.config.Config;
import com.elytraforce.elytracore.events.*;
import com.elytraforce.elytracore.player.redis.Delta;
import com.elytraforce.elytracore.player.redis.RedisController;
import com.elytraforce.elytracore.player.redis.enums.DeltaEnum;
import com.elytraforce.elytracore.player.redis.enums.ValueEnum;
import com.elytraforce.elytracore.storage.SQLStorage;
import com.elytraforce.elytracore.utils.MessageUtils;
import com.elytraforce.elytracore.utils.TitleUtils;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.UUID;


@SuppressWarnings("unused")
public class PlayerController {

    private static PlayerController instance;
    private final HashSet<ElytraPlayer> players;
    private final Config config;

    private PlayerController() {
        players = new HashSet<>();
        config = Main.getAConfig();
    }

    public void playerJoined(OfflinePlayer player) {

        SQLStorage.get().loadPlayer(player);
        //makes sure that if they are in the cache they are removed
        SQLStorage.get().removePlayerCached(player);
    }

    public void joinCallback(OfflinePlayer player, int level, int experience, int money, List<Integer> unlockedRewards, boolean newPlayer) {
        ElytraPlayer p = new ElytraPlayer(
                player,
                level,
                experience,
                money,
                unlockedRewards,
                newPlayer
        );

        players.add(p);

        Bukkit.getPluginManager().callEvent(new ElytraPlayerJoinEvent(p));
    }

    public void playerQuit(ElytraPlayer player) {

        SQLStorage.get().playerQuit(player);
        players.remove(player);
        Bukkit.getPluginManager().callEvent(new ElytraPlayerQuitEvent(player));
    }

    public HashSet<ElytraPlayer> getPlayers() {
        return players;
    }

    public ElytraPlayer getElytraPlayer(OfflinePlayer player) {
        return getElytraPlayer(player.getUniqueId());
    }

    // // // // // // // // // //

    public void addMoney(Player player, int amount, boolean sendMessage) {
        ElytraPlayer p = this.getElytraPlayer(player); addMoney(p,amount,sendMessage);
    }

    public void addMoney(ElytraPlayer player, int amount, boolean sendMessage) {
        int oldMoney = player.getMoney();
        if (sendMessage) { MessageUtils.addMoneyMessage(player, amount); }

        player.addChange(new Delta(player.getUUID(),amount, DeltaEnum.INCREASE, ValueEnum.MONEY));

        Bukkit.getPluginManager().callEvent(new MoneyEvent(player, oldMoney, player.getMoney(), ChangeEnum.INCREASE));
    }

    public void removeMoney(Player player, int amount, boolean sendMessage) {
        ElytraPlayer p = this.getElytraPlayer(player); removeMoney(p,amount,sendMessage);
    }

    public void removeMoney(ElytraPlayer player, int amount, boolean sendMessage) {
        int oldMoney = player.getMoney();
        if (sendMessage) {MessageUtils.removeMoneyMessage(player, amount); }

        player.addChange(new Delta(player.getUUID(),amount, DeltaEnum.DECREASE, ValueEnum.MONEY));

        if (player.getMoney() < 0) {
            player.addChange(new Delta(player.getUUID(),Math.abs(player.getMoney()),DeltaEnum.INCREASE,ValueEnum.MONEY));
        }

        Bukkit.getPluginManager().callEvent(new MoneyEvent(player, oldMoney, player.getMoney(), ChangeEnum.INCREASE));
    }

    public void setMoney (Player player, int amount, boolean sendMessage) {
        ElytraPlayer p = this.getElytraPlayer(player); setMoney(p,amount,sendMessage);
    }

    public void setMoney(ElytraPlayer player, int amount, boolean sendMessage) {
        int sex = amount - player.getMoney();
        if (sex == 0) { return; } else if (sex < 0) { //number is negative
            this.removeMoney(player,Math.abs(sex),sendMessage);
        } else { //number is positive
            this.addMoney(player,Math.abs(sex),sendMessage);
        }
    }

    public void addLevel(Player player, int amount, boolean title, boolean sendMessage) {
        ElytraPlayer p = this.getElytraPlayer(player); addLevel(p,amount,title,sendMessage);
    }

    public void addLevel(ElytraPlayer player, int amount, boolean title, boolean sendMessage) {
        int oldLevel = player.getLevel();

        player.addChange(new Delta(player.getUUID(),amount, DeltaEnum.INCREASE, ValueEnum.LEVEL));

        int newLevel = player.getLevel();

        if (newLevel == config.maxLevel) {
            MessageUtils.maxLevelMessage(player);
            TitleUtils.sendTitle(player, AChat.colorString("&9&lLEVEL UP!"), AChat.colorString("&7" + oldLevel + " -> &e" + newLevel));
            return;
        }

        if (sendMessage) { MessageUtils.addLevelMessage(player); }
        if (title) {TitleUtils.sendTitle(player, AChat.colorString("&9&lLEVEL UP!"), AChat.colorString("&7" + oldLevel + " -> &e" + newLevel)); }

        Bukkit.getPluginManager().callEvent(new LevelEvent(player, oldLevel, newLevel, ChangeEnum.INCREASE));
    }

    public void removeLevel(Player player, int amount, boolean title, boolean sendMessage) {
        ElytraPlayer p = this.getElytraPlayer(player); removeLevel(p,amount,title,sendMessage);
    }

    public void removeLevel(ElytraPlayer player, int amount, boolean title, boolean sendMessage) {
        int oldLevel = player.getLevel();

        player.addChange(new Delta(player.getUUID(),amount, DeltaEnum.DECREASE, ValueEnum.LEVEL));

        if (player.getLevel() < 0) {
            player.addChange(new Delta(player.getUUID(),Math.abs(player.getLevel()),DeltaEnum.INCREASE,ValueEnum.LEVEL));
        }

        int newLevel = player.getLevel();

        if (newLevel == config.maxLevel) {
            MessageUtils.maxLevelMessage(player);
            TitleUtils.sendTitle(player, AChat.colorString("&9&lLEVEL DOWN!"), AChat.colorString("&7" + oldLevel + " -> &e" + newLevel));
            return;
        }

        if (sendMessage) { MessageUtils.removeLevelMessage(player); }
        if (title) {TitleUtils.sendTitle(player, AChat.colorString("&9&lLEVEL DOWN!"), AChat.colorString("&7" + oldLevel + " -> &e" + newLevel)); }

        Bukkit.getPluginManager().callEvent(new LevelEvent(player, oldLevel, newLevel, ChangeEnum.DECREASE));
    }

    public void setLevel(Player player, int amount, boolean title, boolean sendMessage) {
        ElytraPlayer p = this.getElytraPlayer(player);
        setLevel(p,amount,title,sendMessage);
    }

    public void setLevel(ElytraPlayer player, int amount, boolean title, boolean sendMessage) {
        int sex = amount - player.getLevel();
        if (sex == 0) { return; } else if (sex < 0) { //number is negative
            this.removeLevel(player,Math.abs(sex),title,sendMessage);
        } else { //number is positive
            this.addLevel(player,Math.abs(sex),title,sendMessage);
        }
    }

    public void addXP(Player player, int amount, boolean title, boolean sendMessage) {
        ElytraPlayer p = this.getElytraPlayer(player); addXP(p,amount,title,sendMessage);
    }

    public void addXP(ElytraPlayer player, int amount, boolean title, boolean sendMessage) {
        int oldXP = player.getExperience();
        if (player.isDonator()) {amount = (int) (amount * 1.2); }
        if (sendMessage) { MessageUtils.addXPMessage(player, amount); }

        if (title) {
            if (player.isDisplayingTitle()) {
                player.setDisplayedXP(player.getDisplayedXP() + amount);
                player.getDisplayedTask().cancel();
            } else {
                player.setDisplayedXP(amount);
                player.setDisplayingTitle(true);
            }

            player.setDisplayedTask(new BukkitRunnable() {
                public void run() {
                    player.setDisplayingTitle(false);
                    player.setDisplayedXP(0);
                    player.setDisplayedTask(null);
                }
            }.runTaskLater(Main.get(), 40L));

            TitleUtils.sendAnimatedSideTitle(player, "", "         &b+" + player.getDisplayedXP() + "❂", 10);
        }

        if (player.getLevel() > config.maxLevel) { return; }

        player.addChange(new Delta(player.getUUID(),amount, DeltaEnum.INCREASE, ValueEnum.XP));
        Bukkit.getPluginManager().callEvent(new XPEvent(player, oldXP, player.getExperience(), ChangeEnum.INCREASE));

        while (player.canLevelUp()) {
            player.addChange(new Delta(player.getUUID(),player.getRequiredXPToNextLevel(), DeltaEnum.DECREASE, ValueEnum.XP));
            this.addLevel(player,1,true,true);
        }
    }

    public void removeXP(Player player, int amount, boolean title, boolean sendMessage) {
        ElytraPlayer p = this.getElytraPlayer(player); removeXP(p,amount,title,sendMessage);
    }

    public void removeXP(ElytraPlayer player, int amount, boolean title, boolean sendMessage) {
        int oldXP = player.getExperience();

        if (sendMessage) { MessageUtils.removeXPMessage(player, amount); }

        player.addChange(new Delta(player.getUUID(),amount, DeltaEnum.DECREASE, ValueEnum.XP));

        if (player.getExperience() < 0) {
            player.addChange(new Delta(player.getUUID(),Math.abs(player.getExperience()),DeltaEnum.INCREASE,ValueEnum.XP));
        }

        Bukkit.getPluginManager().callEvent(new XPEvent(player, oldXP, player.getExperience(), ChangeEnum.DECREASE));
    }

    public void setXP(Player player, int amount, boolean title, boolean sendMessage) {
        ElytraPlayer p = this.getElytraPlayer(player); setXP(p,amount,title,sendMessage);
    }

    public void setXP(ElytraPlayer player, int amount, boolean title, boolean sendMessage) {
        int sex = amount - player.getExperience();
        if (sex == 0) { return; } else if (sex < 0) { //number is negative
            this.removeXP(player,Math.abs(sex),title,sendMessage);
        } else { //number is positive
            this.addXP(player,Math.abs(sex),title,sendMessage);
        }
    }

    // // // // // // // // // //

    public ElytraPlayer getElytraPlayer(UUID uuid) {
        for (ElytraPlayer elytraPlayer : players) {
            if (elytraPlayer.getUUID().equals(uuid))
                return elytraPlayer;
        }
        return null;
    }

    public static PlayerController get() {
        return Objects.requireNonNullElseGet(instance, () -> instance = new PlayerController());
    }

}

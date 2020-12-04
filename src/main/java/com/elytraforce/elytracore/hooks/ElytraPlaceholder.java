package com.elytraforce.elytracore.hooks;

import com.elytraforce.aUtils.ALogger;
import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.player.PlayerController;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ElytraPlaceholder extends PlaceholderExpansion{

	private final Main main;

    public ElytraPlaceholder(Main plugin){
        this.main = plugin;
    }

    @Override
    public boolean persist(){
        return true;
    }

    @Override
    public boolean canRegister(){
        return true;
    }

    @Override
    public @NotNull String getAuthor(){
        return main.getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getIdentifier(){
        return "elytralevels";
    }

    @Override
    public @NotNull String getVersion(){
        return main.getDescription().getVersion();
    }


    //todo clean this autism up
    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier){

        if(player == null){
            return "";
        }

        if (PlayerController.get().getElytraPlayer(player) == null) {
            return "Loading...";
        }

        // %someplugin_placeholder1%
        if(identifier.equals("level")){
            try {
                return PlayerController.get().getElytraPlayer(player).getLevel() + "";
            } catch (NullPointerException ex) {
                return "Loading...";
            }
        }
        
        if(identifier.equals("next_level")){
            try {
                return Objects.requireNonNullElse(PlayerController.get().getElytraPlayer(player).getNextLevel() + "","Loading...");
            } catch (NullPointerException ex) {
                return "Loading...";
            }
        }
        
        if(identifier.equals("exp")){
            try {
                return Objects.requireNonNullElse(PlayerController.get().getElytraPlayer(player).getExperience() + "","Loading...");
            } catch (NullPointerException ex) {
                return "Loading...";
            }

        }
        
        if(identifier.equals("total_exp")){
            try {
                return Objects.requireNonNullElse(PlayerController.get().getElytraPlayer(player).getRequiredXPToNextLevel() + "","Loading...");
            } catch (NullPointerException ex) {
                return "Loading...";
            }

        }
        
        if(identifier.equals("exp_percent")){
            try {
                return Objects.requireNonNullElse(PlayerController.get().getElytraPlayer(player).getPercent(),"Loading...");
            } catch (NullPointerException ex) {
                return "Loading...";
            }

        }
        
        if(identifier.equals("exp_bar")){
            try {
                return Objects.requireNonNullElse(PlayerController.get().getElytraPlayer(player).getProgressBar(),"Loading...");
            } catch (NullPointerException ex) {
                return "Loading...";
            }

        }

        //returns unformatted money. Use vault instead! 
        if(identifier.equals("money")){
            try {
                return Objects.requireNonNullElse(PlayerController.get().getElytraPlayer(player).getMoney() + "","Loading...");
            } catch (NullPointerException ex) {
                return "Loading...";
            }

        }

        return null;
    }

}

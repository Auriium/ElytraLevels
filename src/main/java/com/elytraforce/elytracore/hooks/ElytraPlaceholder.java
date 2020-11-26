package com.elytraforce.elytracore.hooks;

import org.bukkit.entity.Player;
import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.player.PlayerController;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.jetbrains.annotations.NotNull;

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

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier){

        if(player == null){
            return "";
        }

        // %someplugin_placeholder1%
        if(identifier.equals("level")){
            return PlayerController.get().getElytraPlayer(player).getLevel().toString();
        }
        
        if(identifier.equals("next_level")){
            return PlayerController.get().getElytraPlayer(player).getNextLevel() + "";
        }
        
        if(identifier.equals("exp")){
            return PlayerController.get().getElytraPlayer(player).getExperience().toString();
        }
        
        if(identifier.equals("total_exp")){
            return PlayerController.get().getElytraPlayer(player).getRequiredXPToNextLevel() + "";
        }
        
        if(identifier.equals("exp_percent")){
            return PlayerController.get().getElytraPlayer(player).getPercent();
        }
        
        if(identifier.equals("exp_bar")){
            return PlayerController.get().getElytraPlayer(player).getProgressBar();
        }

        //returns unformatted money. Use vault instead! 
        if(identifier.equals("money")){
        	return PlayerController.get().getElytraPlayer(player).getMoney() + "";
        }

        return null;
    }

}

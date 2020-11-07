package com.elytraforce.elytracore.hooks;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.player.PlayerController;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;

public class ElytraPlaceholder extends PlaceholderExpansion{

	private Main main;

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
    public String getAuthor(){
        return main.getDescription().getAuthors().toString();
    }

    @Override
    public String getIdentifier(){
        return "elytralevels";
    }

    @Override
    public String getVersion(){
        return main.getDescription().getVersion();
    }

    @Override
    public String onPlaceholderRequest(Player player, String identifier){

        if(player == null){
            return "";
        }

        // %someplugin_placeholder1%
        if(identifier.equals("level")){
            return PlayerController.get().getLevelPlayer(player).getLevel().toString();
        }
        
        if(identifier.equals("next_level")){
            return PlayerController.get().getLevelPlayer(player).getNextLevel() + "";
        }
        
        if(identifier.equals("exp")){
            return PlayerController.get().getLevelPlayer(player).getExperience().toString();
        }
        
        if(identifier.equals("total_exp")){
            return PlayerController.get().getLevelPlayer(player).getRequiredXPToNextLevel() + "";
        }
        
        if(identifier.equals("exp_percent")){
            return PlayerController.get().getLevelPlayer(player).getPercent();
        }
        
        if(identifier.equals("exp_bar")){
            return PlayerController.get().getLevelPlayer(player).getProgressBar();
        }

        //returns unformatted money. Use vault instead! 
        if(identifier.equals("money")){
        	return PlayerController.get().getLevelPlayer(player).getMoney() + "";
        }

        return null;
    }

}

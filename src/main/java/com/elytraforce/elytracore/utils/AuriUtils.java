package com.elytraforce.elytracore.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.elytraforce.aUtils.item.AItemBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import com.elytraforce.elytracore.Main;
import com.elytraforce.elytracore.player.ElytraPlayer;
import com.elytraforce.elytracore.utils.text.DefaultFontInfo;


public class AuriUtils {
	     
	public static FileConfiguration loadFileConfig(String name) {
		File customConfigFile = new File(Main.get().getDataFolder(), name);
        if (!customConfigFile.exists()) {
            customConfigFile.getParentFile().mkdirs();
            Main.get().saveResource(name, false);
        }
        YamlConfiguration customConfig= new YamlConfiguration();
        
            try {
				customConfig.load(customConfigFile);
			} catch (IOException | InvalidConfigurationException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            
            return customConfig;
	}
	
	//Utils meant to be copied
	private final static int CENTER_PX = 154;
	
	public static int closestInteger(int of, List<Integer> in) {
	    int min = Integer.MAX_VALUE;
	    int closest = of;

	    for (int v : in) {
	        final int diff = Math.abs(v - of);

	        if (diff < min) {
	            min = diff;
	            closest = v;
	        }
	    }

	    return closest;
	}
	
	public static void log(String string) {
		Bukkit.getConsoleSender().sendMessage(colorString("&f" + Main.pluginName + " &f" + string));
	}
	
	public static void logError(String string) {
		Bukkit.getConsoleSender().sendMessage(colorString("&4[ERROR] " + Main.pluginName + " &f" + string));
	}
	
	public static void logWarning(String string) {
		Bukkit.getConsoleSender().sendMessage(colorString("&c[WARNING] " + Main.pluginName + " &f" + string));
	}
	
	public static String colorString(String string) { return ChatColor.translateAlternateColorCodes('&', string); }
	
	public static List<String> colorString(List<String> strings) { 
		
		ArrayList<String> stringList = new ArrayList<String>();
		
		for (String string : strings) {
			stringList.add(colorString(string));
		}
		
		return stringList;
	}
	
	public static void sendMessage(ElytraPlayer player, String message) {
		player.asBukkitPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', message));
	}
	
	public static void sendCenteredMessage(ElytraPlayer player, String message) {
		sendCenteredMessage(player.asBukkitPlayer(), message);
	}
	 
	public static void sendCenteredMessage(Player player, String message) {
		player.sendMessage(centerMessage(message));
	}
	
	public static String centerMessage(String message) {
		if (message == null || message.equals("")) return "";
        message = ChatColor.translateAlternateColorCodes('&', message);

        int messagePxSize = 0;
        boolean previousCode = false;
        boolean isBold = false;

        for(char c : message.toCharArray()){
                if(c == '§'){
                        previousCode = true;
                        continue;
                }else if(previousCode == true){
                        previousCode = false;
                        if(c == 'l' || c == 'L'){
                                isBold = true;
                                continue;
                        }else isBold = false;
                }else{
                        DefaultFontInfo dFI = DefaultFontInfo.getDefaultFontInfo(c);
                        messagePxSize += isBold ? dFI.getBoldLength() : dFI.getLength();
                        messagePxSize++;
                }
        }

        int halvedMessageSize = messagePxSize / 2;
        int toCompensate = CENTER_PX - halvedMessageSize;
        int spaceLength = DefaultFontInfo.SPACE.getLength() + 1;
        int compensated = 0;
        StringBuilder sb = new StringBuilder();
        while(compensated < toCompensate){
                sb.append(" ");
                compensated += spaceLength;
        }
        
        return sb.toString() + message;
	}
	
}

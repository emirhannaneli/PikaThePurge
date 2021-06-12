package xyz.pikadev.Actions;

import org.bukkit.ChatColor;

public class CC {
    public String translate(String message){
        return ChatColor.translateAlternateColorCodes('&',message);
    }
}

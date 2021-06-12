package xyz.pikadev.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import xyz.pikadev.PikaThePurge;

import java.util.List;

public class OnQuit implements Listener {
    PikaThePurge plugin = PikaThePurge.getPlugin(PikaThePurge.class);
    @EventHandler
    public void onQuit(PlayerQuitEvent e){
        List<String> blacklist = plugin.getConfig().getStringList("Blacklist");
        List<String> whitelist = plugin.getConfig().getStringList("Whitelist");
        Player player = e.getPlayer();
        if(!(whitelist.contains(player.getName()))){
            blacklist.add(player.getName());
            plugin.getConfig().set("Blacklist",blacklist);
            plugin.saveConfig();
        }
    }
}

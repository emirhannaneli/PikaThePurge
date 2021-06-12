package xyz.pikadev.Events;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import xyz.pikadev.PikaThePurge;

import java.util.List;


public class OnDeath implements Listener {
    PikaThePurge plugin = PikaThePurge.getPlugin(PikaThePurge.class);

    @EventHandler
    public void onDeath(PlayerDeathEvent e) {
        List<String> whitelist = plugin.getConfig().getStringList("Whitelist");
        List<String> blacklist = plugin.getConfig().getStringList("Blacklist");
        Player player = e.getEntity();
        if (!(whitelist.contains(player.getName()))){
            blacklist.add(player.getName());
            plugin.getConfig().set("Blacklist",blacklist);
            plugin.saveConfig();
            player.kickPlayer(ChatColor.translateAlternateColorCodes('&', "&6Pika&eThePurge \n\n&7» &cOyundan Elendin &7«"));
        }
    }
}

package xyz.pikadev.Events;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.pikadev.Actions.CC;
import xyz.pikadev.Actions.RandomTP;
import xyz.pikadev.Donate.Book;
import xyz.pikadev.PikaThePurge;

import java.util.ArrayList;
import java.util.List;

public class OnJoin implements Listener {
    public static List<Player> latePlayer = new ArrayList<>();
    PikaThePurge plugin = PikaThePurge.getPlugin(PikaThePurge.class);

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        String worldName = plugin.getConfig().getString("World.Name");
        Player player = e.getPlayer();
        player.setGameMode(GameMode.ADVENTURE);
        if (!PikaThePurge.gameStatus) {
            player.teleport(new Location(Bukkit.getWorld(worldName), 0, 248, 0));
        } else {
            latePlayer.add(player);
            new RandomTP(player);
            new BukkitRunnable() {
                int immunity = 20;
                @Override
                public void run() {
                    if (immunity <= 1) {
                        player.setGameMode(GameMode.SURVIVAL);
                        new Book().removeBook();
                        latePlayer.remove(player);
                        cancel();
                    }
                    immunity -= 1;
                    player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(new CC().translate("&6"+immunity+" Saniye Sonra Dokunulmazlığın Kalkacak.")));
                }
            }.runTaskTimer(plugin, 20L, 20L);
        }
        List<String> blacklist = plugin.getConfig().getStringList("Blacklist");
        if (blacklist.contains(player.getName())) {
            player.kickPlayer(ChatColor.translateAlternateColorCodes('&',
                    "&6Pika&eThePurge \n\n&7» &cTur Bitene Kadar Tekrar Giremezsiniz. &7«"));

        }
    }
}

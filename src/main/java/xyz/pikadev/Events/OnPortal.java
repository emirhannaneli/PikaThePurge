package xyz.pikadev.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerPortalEvent;
import xyz.pikadev.PikaThePurge;

public class OnPortal implements Listener {
    @EventHandler
    public void onPortal(PlayerPortalEvent e) {
        Player player = e.getPlayer();
        if (!PikaThePurge.gameStatus) {
            e.setCancelled(true);
        }else{
            if(OnJoin.latePlayer.contains(player)){
                e.setCancelled(true);
            }
        }
    }
}

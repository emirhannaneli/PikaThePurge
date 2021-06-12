package xyz.pikadev.Events;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import xyz.pikadev.PikaThePurge;

public class OnDamage implements Listener {
    Player player;
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (!PikaThePurge.gameStatus) {
            e.setCancelled(true);
        } else {
            if (e.getEntity() instanceof Player) {
                player = (Player) e.getEntity();
                if (OnJoin.latePlayer.contains(player)) {
                    e.setCancelled(true);
                    player = null;
                }
            }else{
                if (OnJoin.latePlayer.contains(player)) {
                    e.setCancelled(true);
                    player = null;
                }
            }
        }

    }
}

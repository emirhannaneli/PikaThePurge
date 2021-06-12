package xyz.pikadev.Actions;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import xyz.pikadev.PikaThePurge;

public class RandomTP {
    PikaThePurge plugin = PikaThePurge.getPlugin(PikaThePurge.class);

    public RandomTP(Player player) {
        int rndLocMax = plugin.getConfig().getInt("RandomTeleportRadius.Max");
        int rndLocMin = plugin.getConfig().getInt("RandomTeleportRadius.Min");
        int rndLocX = (int) (Math.random() * (rndLocMax - (rndLocMin * -1))) + (rndLocMin * -1);
        int rndLocZ = (int) (Math.random() * (rndLocMax - (rndLocMin * -1))) + (rndLocMin * -1);
        player.teleport(new Location(player.getWorld(), rndLocX, 248, rndLocZ));
    }

}

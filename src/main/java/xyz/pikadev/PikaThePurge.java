package xyz.pikadev;

import org.bstats.bukkit.Metrics;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.WorldBorder;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.pikadev.Commands.PTP;
import xyz.pikadev.Donate.Book;
import xyz.pikadev.Events.*;

public final class PikaThePurge extends JavaPlugin {
    public static boolean gameStatus = false;
    @Override
    public void onEnable() {
        /*Create Config*/
        getConfig().options().copyDefaults(true);
        saveConfig();
        /*Register Commands*/
        getCommand("ptp").setExecutor(new PTP());
        /*Register Tab Completions*/
        getCommand("ptp").setTabCompleter(new TabCompleitons());
        /*Register Events*/
        getServer().getPluginManager().registerEvents(new OnDeath(),this);
        getServer().getPluginManager().registerEvents(new OnJoin(),this);
        getServer().getPluginManager().registerEvents(new OnDamage(),this);
        getServer().getPluginManager().registerEvents(new OnPortal(),this);
        getServer().getPluginManager().registerEvents(new OnQuit(),this);
        getServer().getPluginManager().registerEvents(new Book(),this);
        /*World Border*/
        setBorder();
        /*Metrics*/
        Metrics metrics = new Metrics(this, 11665);
        /*Active*/
        Bukkit.getConsoleSender().sendMessage("§6Pika§eThePurge » §aAktif...");
    }

    private void setBorder(){
        if(getConfig().getBoolean("World.Border")){
            String worldName = getConfig().getString("World.Name");
            double borderSize = getConfig().getInt("RandomTeleportRadius.Max") * 1.2;
            Bukkit.getWorld(worldName).setSpawnLocation(new Location(Bukkit.getWorld(worldName),0,0,0));
            WorldBorder wb = Bukkit.getWorld(worldName).getWorldBorder();
            wb.setCenter(new Location(Bukkit.getWorld(worldName),0,0,0));
            wb.setSize(borderSize);
        }
    }

    @Override
    public void onDisable() {
        /*Deaktive*/
        Bukkit.getConsoleSender().sendMessage("§6Pika§eThePurge » §cDeaktif...");
    }
}

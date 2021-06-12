package xyz.pikadev.Commands;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.pikadev.Actions.CC;
import xyz.pikadev.Actions.RandomTP;
import xyz.pikadev.Donate.Book;
import xyz.pikadev.PikaThePurge;

import java.util.List;

public class PTP implements CommandExecutor {
    PikaThePurge plugin = PikaThePurge.getPlugin(PikaThePurge.class);
    int safeAreaTimer = plugin.getConfig().getInt("SafeAreaTimer");
    int safeAreaTimerCount = safeAreaTimer;
    BossBar bossBar = Bukkit.createBossBar(new CC().translate("&6Güvenli Bir Bölgeye Geçmek için " + safeAreaTimer + " Saniye Süren Var!"), BarColor.YELLOW, BarStyle.SOLID);

    List<String> whitelist = plugin.getConfig().getStringList("Whitelist");
    String worldName = plugin.getConfig().getString("World.Name");

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length < 1) {
            about(sender);
            return false;
        }
        if (args[0].equalsIgnoreCase("start")) {
            if (!sender.hasPermission("ptr.admin")) {
                sender.sendMessage(new CC().translate("&6Pika&eThePurge &7» &cBu Komutu Kullanma İzniniz Yok."));
                return false;
            }
            if (PikaThePurge.gameStatus) {
                sender.sendMessage(new CC().translate("&6Pika&eThePurge &7» &cZaten Bir Oyun Oynanıyor."));
                return false;
            }

            Bukkit.getServer().setWhitelist(true);
            Bukkit.broadcastMessage(new CC().translate("&6Pika&eThePurge &7» &eOyun Birazdan Başlayacak."));
            Bukkit.broadcastMessage(new CC().translate("&6Pika&eThePurge &7» &cIşınlanılıyor..."));
            Bukkit.broadcastMessage(new CC().translate("&6Pika&eThePurge &7» &cLütfen Bekleyin..."));

            new BukkitRunnable() {
                @Override
                public void run() {
                    for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                        if (whitelist.contains(onlinePlayer.getName())) {
                            onlinePlayer.teleport(new Location(Bukkit.getWorld(worldName), 0, 248, 0));
                        } else {
                            new RandomTP(onlinePlayer);
                        }
                        bossBar.addPlayer(onlinePlayer);
                        bossBar.setVisible(true);
                        onlinePlayer.sendTitle(new CC().translate("&eOyun Birazdan Başlayacak"), new CC().translate("&7pikadev.xyz :)"));
                    }
                }
            }.runTask(plugin);

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (safeAreaTimerCount <= 0) {
                        PikaThePurge.gameStatus = true;
                        Bukkit.broadcastMessage(new CC().translate("&6Pika&eThePurge &7» &aOyun Başladı."));
                        bossBar.setVisible(false);
                        bossBar.removeAll();
                        new Book().removeBook();
                        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
                            onlinePlayer.setGameMode(GameMode.SURVIVAL);
                            onlinePlayer.sendTitle(new CC().translate("&aOyun Başladı."), new CC().translate("&7pikadev.xyz :)"));
                            onlinePlayer.setFoodLevel(20);
                            onlinePlayer.setHealth(20);
                        }
                        Bukkit.getServer().setWhitelist(false);
                        cancel();
                    }
                    safeAreaTimerCount -= 1;
                    bossBar.setTitle(new CC().translate("&6Güvenli Bir Bölgeye Geçmek için " + safeAreaTimerCount + " Saniye Süren Var!"));
                }
            }.runTaskTimer(plugin, 10L, 20L);
        }
        if (args[0].equalsIgnoreCase("reload")) {
            if(sender instanceof Player){
                if (!sender.hasPermission("ptr.admin")) {
                    sender.sendMessage(new CC().translate("&6Pika&eThePurge &7» &cBu Komutu Kullanma İzniniz Yok."));
                    return false;
                }
            }
            plugin.reloadConfig();
            sender.sendMessage(new CC().translate("&6Pika&eThePurge &7» &aConfig Dosyası Yenilendi."));
        }
        return false;
    }

    private void about(CommandSender player) {
        player.sendMessage(new CC().translate("&6Pika&edev Sunar..."));
        player.sendMessage(new CC().translate("  &7&l=> &6Version: 1.0-SNAPSHOT"));
        player.sendMessage(new CC().translate("  &7&l=> &6Web: https://pikadev.xyz"));
        player.sendMessage(new CC().translate("  &7&l=> &6Source Code: https://github.com/PiikaDev/PikaThePurge"));
    }

}

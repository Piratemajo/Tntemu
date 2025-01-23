package es.tntemu.config;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import es.tntemu.Tntemu;

import java.util.Random;

public class EventManager {

    private final Tntemu plugin;
    private final Random random = new Random();

    public EventManager(Tntemu plugin) {
        this.plugin = plugin;
    }

    public void startRandomEvents() {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (random.nextBoolean()) {
                    for (Player player : Bukkit.getOnlinePlayers()) {
                        player.sendMessage("¡Un evento aleatorio ha comenzado!");
                    }
            
                }
            }
        }.runTaskTimer(plugin, 0, 1200); 
    }

}

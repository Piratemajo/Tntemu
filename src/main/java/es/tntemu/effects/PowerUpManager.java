package es.tntemu.effects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import es.tntemu.Tntemu;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class PowerUpManager {

    private final Tntemu plugin;
    private final Random random = new Random();
    private final Map<String, PowerUp> powerUps = new HashMap<>();

    public PowerUpManager(Tntemu plugin) {
        this.plugin = plugin;
        registerDefaultPowerUps();
    }

    private void registerDefaultPowerUps() {
        powerUps.put("speed", new PowerUp(Material.FEATHER, player -> {
            player.setWalkSpeed(0.5f);
            Bukkit.getScheduler().runTaskLater(plugin, () -> player.setWalkSpeed(0.2f), 200);
        }));

        powerUps.put("invincibility", new PowerUp(Material.GOLDEN_APPLE, player -> {
            player.setInvulnerable(true);
            Bukkit.getScheduler().runTaskLater(plugin, () -> player.setInvulnerable(false), 200);
        }));

        powerUps.put("fire_resistance", new PowerUp(Material.BLAZE_POWDER, player -> {
            player.setFireTicks(0);
        }));
    }

    public void spawnRandomPowerUp(Location location) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (plugin.getPlayersInGame().isEmpty()) {
                    cancel();
                    return;
                }

                Location randomLocation = location.clone().add(random.nextInt(10) - 5, 0, random.nextInt(10) - 5);
                PowerUp powerUp = getRandomPowerUp();
                randomLocation.getWorld().dropItemNaturally(randomLocation, new ItemStack(powerUp.getMaterial()));
            }
        }.runTaskTimer(plugin, 0, 600);
    }

    private PowerUp getRandomPowerUp() {
        Object[] values = powerUps.values().toArray();
        return (PowerUp) values[random.nextInt(values.length)];
    }

    public void applyPowerUp(Player player, ItemStack powerUpItem) {
        for (PowerUp powerUp : powerUps.values()) {
            if (powerUp.getMaterial() == powerUpItem.getType()) {
                powerUp.apply(player);
                break;
            }
        }
    }

    public void registerPowerUp(String name, PowerUp powerUp) {
        powerUps.put(name, powerUp);
    }
}

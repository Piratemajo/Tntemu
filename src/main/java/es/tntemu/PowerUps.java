package es.tntemu;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class PowerUps {

    private final Tntemu plugin;
    private final Random random = new Random();

    public PowerUps(Tntemu plugin) {
        this.plugin = plugin;
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
                ItemStack powerUp = getRandomPowerUp();
                randomLocation.getWorld().dropItemNaturally(randomLocation, powerUp);
            }
        }.runTaskTimer(plugin, 0, 600); // Spawns a power-up every 30 seconds
    }

    private ItemStack getRandomPowerUp() {
        Material[] powerUpMaterials = {Material.GOLDEN_APPLE, Material.FEATHER, Material.BLAZE_POWDER};
        return new ItemStack(powerUpMaterials[random.nextInt(powerUpMaterials.length)]);
    }

    public void applyPowerUp(Player player, ItemStack powerUp) {
        switch (powerUp.getType()) {
            case GOLDEN_APPLE:
                player.setInvulnerable(true);
                Bukkit.getScheduler().runTaskLater(plugin, () -> player.setInvulnerable(false), 200);
                break;
            case FEATHER:
                player.setWalkSpeed(0.5f);
                Bukkit.getScheduler().runTaskLater(plugin, () -> player.setWalkSpeed(0.2f), 200);
                break;
            case BLAZE_POWDER:
                player.setFireTicks(0);
                break;
            default:
                break;
        }
    }
}

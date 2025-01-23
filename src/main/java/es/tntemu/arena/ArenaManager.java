package es.tntemu.arena;


import org.bukkit.*;
import es.tntemu.Tntemu;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.block.Action;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ArenaManager implements Listener {

    private final Tntemu plugin;
    private final Map<String, Location[]> arenas = new HashMap<>();
    private String selectedArena;
    private Location pos1;
    private Location pos2;
    private Location spawnPoint;

    public ArenaManager(Tntemu plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public void addArena(String name) {
        if (pos1 == null || pos2 == null || spawnPoint == null) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage("Debes seleccionar las dos esquinas y el punto de reaparición antes de guardar la arena.");
            }
            return;
        }
        
        arenas.put(name, new Location[]{pos1, pos2, spawnPoint});
        plugin.getConfig().set("arenas." + name + ".pos1", serializeLocation(pos1));
        plugin.getConfig().set("arenas." + name + ".pos2", serializeLocation(pos2));
        plugin.getConfig().set("arenas." + name + ".spawn", serializeLocation(spawnPoint));
        plugin.saveConfig();
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage("Arena \"" + name + "\" guardada.");
        }
    }

    public void selectArena(String name) {
        if (!arenas.containsKey(name)) {
            for (Player player : Bukkit.getOnlinePlayers()) {
                player.sendMessage("La arena \"" + name + "\" no existe.");
            }
            return;
        }
        selectedArena = name;
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage("Arena seleccionada: \"" + name + "\".");
        }
    }

    public void loadArenaConfig() {
        if (plugin.getConfig().isConfigurationSection("arenas")) {
            for (String name : plugin.getConfig().getConfigurationSection("arenas").getKeys(false)) {
                Location pos1 = deserializeLocation(plugin.getConfig().getString("arenas." + name + ".pos1"));
                Location pos2 = deserializeLocation(plugin.getConfig().getString("arenas." + name + ".pos2"));
                Location spawn = deserializeLocation(plugin.getConfig().getString("arenas." + name + ".spawn"));
                arenas.put(name, new Location[]{pos1, pos2, spawn});
            }
        }
    }

    public Location[] getSelectedArenaBounds() {
        if (selectedArena == null) {
            return null;
        }
        return arenas.get(selectedArena);
    }

    private String serializeLocation(Location loc) {
        return loc.getWorld().getName() + "," + loc.getX() + "," + loc.getY() + "," + loc.getZ();
    }

    private Location deserializeLocation(String str) {
        String[] parts = str.split(",");
        World world = Bukkit.getWorld(parts[0]);
        double x = Double.parseDouble(parts[1]);
        double y = Double.parseDouble(parts[2]);
        double z = Double.parseDouble(parts[3]);
        return new Location(world, x, y, z);
    }

    public Map<String, Location[]> getArenas() {
        return arenas;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (player.getInventory().getItemInMainHand().getType() == Material.STICK) {
            if (event.getAction() == Action.LEFT_CLICK_BLOCK) {
                pos1 = event.getClickedBlock().getLocation();
                player.sendMessage(Color.GREEN + "Posición 1 establecida en: " + pos1.toString());
            } else if (event.getAction() == Action.RIGHT_CLICK_BLOCK) {
                pos2 = event.getClickedBlock().getLocation();
                player.sendMessage(Color.GREEN + "Posición 2 establecida en: " + pos2.toString());
            } else if (event.getAction() == Action.RIGHT_CLICK_AIR) {
                spawnPoint = player.getLocation();
                player.sendMessage(Color.GREEN + "Punto de reaparición establecido en: " + spawnPoint.toString());
            }
        }
    }
}

package es.tntemu;


import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;
import java.util.Map;

public class ArenaManager {

    private final Tntemu plugin;
    private final Map<String, Location[]> arenas = new HashMap<>();
    private String selectedArena;

    public ArenaManager(Tntemu plugin) {
        this.plugin = plugin;
    }

    public void addArena(String name, Location pos1, Location pos2) {
        arenas.put(name, new Location[]{pos1, pos2});
        plugin.getConfig().set("arenas." + name + ".pos1", serializeLocation(pos1));
        plugin.getConfig().set("arenas." + name + ".pos2", serializeLocation(pos2));
        plugin.saveConfig();
        Bukkit.broadcastMessage("Arena \"" + name + "\" guardada.");
    }

    public void selectArena(String name) {
        if (!arenas.containsKey(name)) {
            Bukkit.broadcastMessage("La arena \"" + name + "\" no existe.");
            return;
        }
        selectedArena = name;
        Bukkit.broadcastMessage("Arena seleccionada: \"" + name + "\".");
    }

    public void loadArenaConfig() {
        if (plugin.getConfig().isConfigurationSection("arenas")) {
            for (String name : plugin.getConfig().getConfigurationSection("arenas").getKeys(false)) {
                Location pos1 = deserializeLocation(plugin.getConfig().getString("arenas." + name + ".pos1"));
                Location pos2 = deserializeLocation(plugin.getConfig().getString("arenas." + name + ".pos2"));
                arenas.put(name, new Location[]{pos1, pos2});
            }
        }
    }

    public Location[] getSelectedArenaBounds() {
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
}

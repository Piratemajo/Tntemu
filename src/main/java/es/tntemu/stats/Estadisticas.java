package es.tntemu.stats;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import es.tntemu.Tntemu;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

public class Estadisticas {

    private final Map<String, Integer> kills = new HashMap<>();
    private final Map<String, Integer> deaths = new HashMap<>();
    protected final Tntemu plugin;
    protected File statsFile;
    protected FileConfiguration statsConfig;

    public Estadisticas(Tntemu plugin) {
        this.plugin = plugin;
        statsFile = new File(plugin.getDataFolder(), "stats.yml");
        if (!statsFile.exists()) {
            try {
                statsFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        statsConfig = YamlConfiguration.loadConfiguration(statsFile);
        loadStats();
    }

    public void addKill(String playerName) {
        kills.put(playerName, kills.getOrDefault(playerName, 0) + 1);
        saveStats();
    }

    public void addDeath(String playerName) {
        deaths.put(playerName, deaths.getOrDefault(playerName, 0) + 1);
        saveStats();
    }

    public int getKills(String playerName) {
        return kills.getOrDefault(playerName, 0);
    }

    public int getDeaths(String playerName) {
        return deaths.getOrDefault(playerName, 0);
    }

    public Map<String, Integer> getAllKills() {
        return kills;
    }

    public Map<String, Integer> getAllDeaths() {
        return deaths;
    }

    public void saveStats() {
        for (String player : kills.keySet()) {
            statsConfig.set("kills." + player, kills.get(player));
        }
        for (String player : deaths.keySet()) {
            statsConfig.set("deaths." + player, deaths.get(player));
        }
        try {
            statsConfig.save(statsFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void loadStats() {
        if (statsConfig.contains("kills")) {
            for (String player : statsConfig.getConfigurationSection("kills").getKeys(false)) {
                kills.put(player, statsConfig.getInt("kills." + player));
            }
        }
        if (statsConfig.contains("deaths")) {
            for (String player : statsConfig.getConfigurationSection("deaths").getKeys(false)) {
                deaths.put(player, statsConfig.getInt("deaths." + player));
            }
        }
    }

    public void resetStats() {
        kills.clear();
        deaths.clear();
        saveStats();
    }

    public void clearStats() {
        statsFile.delete();
        statsConfig = YamlConfiguration.loadConfiguration(statsFile);
    }

    public void reloadStats() {
        statsConfig = YamlConfiguration.loadConfiguration(statsFile);
        loadStats();
    }

    public void allwins() {
        for (String player : kills.keySet()) {
            for (String player2 : kills.keySet()) {
                if (kills.get(player) > kills.get(player2)) {
                    for (Player jugador : Bukkit.getOnlinePlayers()) {
                      jugador.sendMessage(player + " tiene más kills que " + player2 + ".");
                    }
                }
            }
    
        }
    }
}

package es.tntemu;

import org.bukkit.entity.Player;

public class Placeholders {

    private final Tntemu plugin;

    public Placeholders(Tntemu plugin) {
        this.plugin = plugin;
    }

    public String parsePlaceholders(Player player, String text) {
        if (player == null) {
            return text;
        }

        text = text.replace("{kills}", String.valueOf(plugin.getEstadisticas().getKills(player.getName())));
        text = text.replace("{deaths}", String.valueOf(plugin.getEstadisticas().getDeaths(player.getName())));

        return text;
    }
}

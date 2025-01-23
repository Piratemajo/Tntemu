package es.tntemu.scoreboards;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;
import org.jetbrains.annotations.NotNull;
import es.tntemu.Tntemu;

public class ScoreboardManager {

    private final Tntemu plugin;
    private final Scoreboard scoreboard;
    private final Objective objective;

    public ScoreboardManager(Tntemu plugin) {
        this.plugin = plugin;
        org.bukkit.scoreboard.ScoreboardManager manager = Bukkit.getScoreboardManager();
        this.scoreboard = manager.getMainScoreboard();
        Objective existingObjective = scoreboard.getObjective("tntemu");
        if (existingObjective != null) {
            existingObjective.unregister();
        }

        // Crear un nuevo objetivo
        this.objective = scoreboard.registerNewObjective(
                "tntemu",
                "dummy",
                Component.text("TNTEmu", NamedTextColor.RED)
        );
        this.objective.setDisplaySlot(DisplaySlot.SIDEBAR);
    }

    public void updateScoreboard(Player player) {
        Score kills = objective.getScore(
                (@NotNull OfflinePlayer) Component.text("Kills: ", NamedTextColor.GREEN)
                        .append(Component.text(plugin.getEstadisticas().getKills(player.getName()), NamedTextColor.WHITE))
        );
        kills.setScore(3);

        Score deaths = objective.getScore(
                (@NotNull OfflinePlayer) Component.text("Deaths: ", NamedTextColor.RED)
                        .append(Component.text(plugin.getEstadisticas().getDeaths(player.getName()), NamedTextColor.WHITE))
        );
        deaths.setScore(2);

        Score points = objective.getScore(
                (@NotNull OfflinePlayer) Component.text("Points: ", NamedTextColor.YELLOW)
                        .append(Component.text(plugin.getScoreManager().getScore(player), NamedTextColor.WHITE))
        );
        points.setScore(1);

        player.setScoreboard(scoreboard);
    }

    public void clearScoreboard(Player player) {
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }
}

package es.tntemu.stats;

/*
*  Implementacion de el sistema de logros para un futuro
*/

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class AchievementManager {

    private final Map<Player, Map<String, Boolean>> playerAchievements = new HashMap<>();

    public void awardAchievement(Player player, String achievement) {
        playerAchievements.computeIfAbsent(player, k -> new HashMap<>()).put(achievement, true);
        player.sendMessage("¡Has conseguido el logro: " + achievement + "!");
    }

    public boolean hasAchievement(Player player, String achievement) {
        return playerAchievements.getOrDefault(player, new HashMap<>()).getOrDefault(achievement, false);
    }

    public Map<String, Boolean> getAchievements(Player player) {
        return playerAchievements.getOrDefault(player, new HashMap<>());
    }
}

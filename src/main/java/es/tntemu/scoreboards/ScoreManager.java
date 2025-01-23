package es.tntemu.scoreboards;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class ScoreManager {

    private final Map<Player, Integer> playerScores = new HashMap<>();

    public void addScore(Player player, int score) {
        playerScores.put(player, playerScores.getOrDefault(player, 0) + score);
    }

    public int getScore(Player player) {
        return playerScores.getOrDefault(player, 0);
    }

    public void resetScores() {
        playerScores.clear();
    }

    public Player getTopScorer() {
        Player topScorer = null;
        int maxScore = 0;
        for (Map.Entry<Player, Integer> entry : playerScores.entrySet()) {
            if (entry.getValue() > maxScore) {
                maxScore = entry.getValue();
                topScorer = entry.getKey();
            }
        }
        return topScorer;
    }
}

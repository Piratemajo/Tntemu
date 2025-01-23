package es.tntemu.modo;

import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;

public class RewardManager {

    private final Map<Player, Integer> dailyRewards = new HashMap<>();
    private final Map<Player, Integer> weeklyRewards = new HashMap<>();

    public void giveDailyReward(Player player) {
        dailyRewards.put(player, dailyRewards.getOrDefault(player, 0) + 1);
        player.sendMessage("¡Has recibido tu recompensa diaria!");
    }

    public void giveWeeklyReward(Player player) {
        weeklyRewards.put(player, weeklyRewards.getOrDefault(player, 0) + 1);
        player.sendMessage("¡Has recibido tu recompensa semanal!");
    }

    public int getDailyRewards(Player player) {
        return dailyRewards.getOrDefault(player, 0);
    }

    public int getWeeklyRewards(Player player) {
        return weeklyRewards.getOrDefault(player, 0);
    }
}

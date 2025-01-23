package es.tntemu.modo;

import es.tntemu.Tntemu;

public class DifficultyManager {

    private final Tntemu plugin;
    private DifficultyLevel currentDifficulty;

    public DifficultyManager(Tntemu plugin) {
        this.plugin = plugin;
        this.currentDifficulty = DifficultyLevel.NORMAL;
    }

    public void setDifficulty(DifficultyLevel difficulty) {
        this.currentDifficulty = difficulty;
        plugin.getConfig().set("difficulty", difficulty.name());
        plugin.saveConfig();
    }

    public DifficultyLevel getDifficulty() {
        return currentDifficulty;
    }

    public enum DifficultyLevel {
        EASY, NORMAL, HARD
    }
}

package es.tntemu;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.boss.BarColor;
import org.bukkit.boss.BarStyle;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
;

public class Tntemu extends JavaPlugin implements Listener {

    private Player tntHolder;

    private BossBar bossBar;

    private ArenaManager arenaManager;
    private List<Player> playersInGame = new ArrayList<>();
    private int timer;
    private Estadisticas estadisticas;
    private Particulas particulas;
    private PowerUps powerUps;
    private List<String> musicTracks;
    private int currentRound = 0;
    private int totalRounds;
    private Placeholders placeholders;

    @Override
    public void onEnable() {
        bossBar = Bukkit.createBossBar("Tiempo restante", BarColor.RED, BarStyle.SOLID);
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("tntemu").setExecutor(new TNTCommand(this));
        saveDefaultConfig();
        timer = getConfig().getInt("timer", 10);
        totalRounds = getConfig().getInt("rounds", 3);
        arenaManager = new ArenaManager(this);
        arenaManager.loadArenaConfig();
        estadisticas = new Estadisticas(this);
        particulas = new Particulas();
        powerUps = new PowerUps(this);
        musicTracks = getConfig().getStringList("music.tracks");
        placeholders = new Placeholders(this);

        getLogger().info("TNTEmu ha sido activado.");

        // Comprobación de arenas y comandos
        if (arenaManager.getArenas().isEmpty()) {
            getLogger().warning("No se encontraron arenas configuradas.");
        } else {
            getLogger().info("Arenas cargadas correctamente.");
        }

        if (getCommand("tntemu") == null) {
            getLogger().warning("El comando 'tntemu' no está registrado correctamente.");
        } else {
            getLogger().info("El comando 'tntemu' está registrado correctamente.");
        }
    }

    @Override
    public void onDisable() {
        getLogger().info("TNTEmu ha sido desactivado.");
        estadisticas.saveStats();
    }

    public void startGame(List<Player> players) {
        currentRound = 1;
        startRound(players);
    }

    private void startRound(List<Player> players) {
        bossBar.setProgress(1.0);
        bossBar.setVisible(true);

        playersInGame.clear();
        playersInGame.addAll(players);

        for (Player player : players) {
            bossBar.addPlayer(player);
        }

        new BukkitRunnable() {
            int countdown = timer;

            @Override
            public void run() {
                if (playersInGame.size() <= 1 || countdown <= 0) {
                    bossBar.setVisible(false);
                    endRound();
                    cancel();
                    return;
                }

                double progress = (double) countdown / timer;
                bossBar.setProgress(progress);
                bossBar.setTitle(ChatColor.RED + getConfig().getString("messages.time_remaining", "Tiempo restante: ") + countdown + " segundos");
                countdown--;
            }
        }.runTaskTimer(this, 0, 20);

        giveTNT(playersInGame.get(new Random().nextInt(playersInGame.size())));
        powerUps.spawnRandomPowerUp(playersInGame.get(0).getLocation());
        playMusic();
    }

    public void giveTNT(Player player) {
        if (player == null) return;
        player.getInventory().setItem(0, new ItemStack(Material.TNT));
        tntHolder = player;
        Bukkit.broadcastMessage(ChatColor.GREEN + placeholders.parsePlaceholders(player, getConfig().getString("messages.tnt_holder", "{player} tiene la Patata.").replace("{player}", player.getName())));
        particulas.generarParticulas(player);
    }

    public void explodePlayer(Player player) {
        if (player == null) return;
        Location loc = player.getLocation();
        player.getWorld().createExplosion(loc, (float) getConfig().getDouble("explosion.radius", 4.0), false, false);
        player.setGameMode(GameMode.SPECTATOR);
        playersInGame.remove(player);
        Bukkit.broadcastMessage(ChatColor.RED + placeholders.parsePlaceholders(player, getConfig().getString("messages.player_exploded", "{player} ha explotado.").replace("{player}", player.getName())));
        estadisticas.addDeath(player.getName());
        if (!playersInGame.isEmpty()) {
            giveTNT(playersInGame.get(new Random().nextInt(playersInGame.size())));
        }
    }

    public void endRound() {
        if (playersInGame.size() == 1) {
            Player winner = playersInGame.get(0);
            Bukkit.broadcastMessage(ChatColor.GREEN + placeholders.parsePlaceholders(winner, getConfig().getString("messages.round_winner", "{player} ha ganado la ronda.").replace("{player}", winner.getName())));
            estadisticas.addKill(winner.getName());
        } else {
            Bukkit.broadcastMessage(ChatColor.RED + getConfig().getString("messages.round_over", "La ronda ha terminado."));
            /* TODO revisar la linea */
            explodePlayer(playersInGame.get(new Random().nextInt(playersInGame.size())));
        }
        playersInGame.clear();
        tntHolder = null;
        stopMusic();

        if (currentRound < totalRounds) {
            currentRound++;
            Bukkit.broadcastMessage(ChatColor.YELLOW + "Comenzando la ronda " + currentRound + "...");
            startRound(new ArrayList<>(Bukkit.getOnlinePlayers()));
        } else {
            endGame();
        }
    }

    public void endGame() {
        Bukkit.broadcastMessage(ChatColor.RED + getConfig().getString("messages.game_over", "El juego ha terminado."));
        /* TODO Revisar esta linea*/
        stopMusic();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Player && event.getPlayer().equals(tntHolder)) {
            Player clicked = (Player) event.getRightClicked();
            if (playersInGame.contains(clicked)) {
                giveTNT(clicked);
                event.getPlayer().getInventory().remove(Material.TNT);
                Bukkit.broadcastMessage(ChatColor.YELLOW + placeholders.parsePlaceholders(event.getPlayer(), getConfig().getString("messages.tnt_passed", "{player1} ha pasado la Patata a {player2}.")
                        .replace("{player1}", event.getPlayer().getName())
                        .replace("{player2}", clicked.getName())));
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        playersInGame.remove(player);
        if (player.equals(tntHolder)) {
            giveTNT(playersInGame.isEmpty() ? null : playersInGame.get(new Random().nextInt(playersInGame.size())));
        }
    }

    public ArenaManager getArenaManager() {
        return arenaManager;
    }

    public List<Player> getPlayersInGame() {
        return playersInGame;
    }

    public int getTimer() {
        return timer;
    }

    public void setTimer(int timer) {
        this.timer = timer;
    }

    public BossBar getBossBar() {
        return bossBar;
    }

    public Estadisticas getEstadisticas() {
        return estadisticas;
    }

    private void playMusic() {
        if (getConfig().getBoolean("music.enabled", true)) {
            for (String track : musicTracks) {
                for (Player player : playersInGame) {
                    player.playSound(player.getLocation(), track, 1.0f, 1.0f);
                }
            }
        }
    }

    private void stopMusic() {
        if (getConfig().getBoolean("music.enabled", true)) {
            for (String track : musicTracks) {
                for (Player player : playersInGame) {
                    player.stopSound(track);
                }
            }
        }
    }
}
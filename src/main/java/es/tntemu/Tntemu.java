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

public class Tntemu extends JavaPlugin implements Listener {

    private Player tntHolder;

    private BossBar bossBar;

    private ArenaManager arenaManager;
    private List<Player> playersInGame = new ArrayList<>();
    private int timer;

    @Override
    public void onEnable() {
        bossBar = Bukkit.createBossBar("Tiempo restante", BarColor.RED, BarStyle.SOLID);
        getServer().getPluginManager().registerEvents(this, this);
        getCommand("tntemu").setExecutor(new TNTCommand(this));
        saveDefaultConfig();
        timer = getConfig().getInt("timer", 10);
        arenaManager = new ArenaManager(this);
        arenaManager.loadArenaConfig();
    }

    @Override
    public void onDisable() {
        getLogger().info("TNTEmu ha sido desactivado.");
    }

    public void startGame(List<Player> players) {
        // Configurar la BossBar
        bossBar.setProgress(1.0);
        bossBar.setVisible(true);
        for (Player player : players) {
            bossBar.addPlayer(player);
        }

        // Lógica del temporizador
        new BukkitRunnable() {
            int countdown = timer;

            @Override
            public void run() {
                if (playersInGame.size() <= 1 || countdown <= 0) {
                    bossBar.setVisible(false);
                    endGame();
                    cancel();
                    return;
                }

                double progress = (double) countdown / timer;
                bossBar.setProgress(progress);
                bossBar.setTitle(ChatColor.RED + "Tiempo restante: " + countdown + " segundos");
                countdown--;
            }
        }.runTaskTimer(this, 0, 20);
    }

    public void giveTNT(Player player) {
        if (player == null) return;
        player.getInventory().setItem(0, new ItemStack(Material.TNT));
        tntHolder = player;
        Bukkit.broadcastMessage(ChatColor.YELLOW + player.getName() + " tiene la TNT. ¡Cuidado!");
    }

    public void explodePlayer(Player player) {
        if (player == null) return;
        Location loc = player.getLocation();
        player.getWorld().createExplosion(loc, 4.0F, false, false);
        player.setGameMode(GameMode.SPECTATOR);
        playersInGame.remove(player);
        Bukkit.broadcastMessage(ChatColor.RED + player.getName() + " ha explotado.");
        if (!playersInGame.isEmpty()) {
            giveTNT(playersInGame.get(new Random().nextInt(playersInGame.size())));
        }
    }

    public void endGame() {
        if (playersInGame.size() == 1) {
            Player winner = playersInGame.get(0);
            Bukkit.broadcastMessage(ChatColor.GREEN + winner.getName() + " ha ganado el juego.");
        } else {
            Bukkit.broadcastMessage(ChatColor.RED + "El juego ha terminado.");
        }
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEntityEvent event) {
        if (event.getRightClicked() instanceof Player && event.getPlayer().equals(tntHolder)) {
            Player clicked = (Player) event.getRightClicked();
            giveTNT(clicked);
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
}
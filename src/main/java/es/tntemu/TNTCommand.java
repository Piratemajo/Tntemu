package es.tntemu;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.stream.Collectors;

public class TNTCommand implements CommandExecutor {

    private final Tntemu plugin;

    public TNTCommand(Tntemu plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(ChatColor.RED + "¡Este comando solo puede ser usado por jugadores!");
            return true;
        }

        if (args.length == 0 || (!args[0].equalsIgnoreCase("start") && !args[0].equalsIgnoreCase("stop"))) {
            sender.sendMessage(ChatColor.YELLOW + "Uso: /tntemu <start|stop>");
            return true;
        }

        if (args[0].equalsIgnoreCase("start")) {
            List<Player> players = Bukkit.getOnlinePlayers().stream().collect(Collectors.toList());
            if (players.size() < 2) {
                sender.sendMessage(ChatColor.RED + "¡Se necesitan al menos 2 jugadores para comenzar!");
                return true;
            }
            plugin.startGame(players);
            sender.sendMessage(ChatColor.GREEN + "¡El juego ha comenzado!");
        } else if (args[0].equalsIgnoreCase("stop")) {
            plugin.endGame();
            sender.sendMessage(ChatColor.RED + "El juego ha terminado.");
        }

        if (args[0].equalsIgnoreCase("addarena") && args.length == 7) {
            Player player = (Player) sender;
            String name = args[1];
            Location pos1 = new Location(player.getWorld(), Double.parseDouble(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4]));
            Location pos2 = new Location(player.getWorld(), Double.parseDouble(args[5]), Double.parseDouble(args[6]));
            plugin.getArenaManager().addArena(name, pos1, pos2);
            sender.sendMessage(ChatColor.GREEN + "¡Arena \"" + name + "\" guardada!");
        } else if (args[0].equalsIgnoreCase("selectarena") && args.length == 2) {
            plugin.getArenaManager().selectArena(args[1]);
            sender.sendMessage(ChatColor.YELLOW + "Arena seleccionada: " + args[1]);
        }


        return true;
    }
}

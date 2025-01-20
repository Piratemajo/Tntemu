package es.tntemu;

/* 
 * Actualizacion de los codigos de colores a la versiones superiores de la 1.21.1
*/
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;

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
            sender.sendMessage(Component.text("¡Este comando solo puede ser usado por jugadores!", TextColor.color(0xFF0000)));
            return true;
        }

        if (args.length == 0 || (!args[0].equalsIgnoreCase("start") && !args[0].equalsIgnoreCase("stop"))) {
            sender.sendMessage(Component.text("Uso: /tntemu <start|stop>", TextColor.color(0xFF0000)));
            return true;
        }

        if (args[0].equalsIgnoreCase("start")) {
            List<Player> players = Bukkit.getOnlinePlayers().stream().collect(Collectors.toList());
            if (players.size() < 2) {
                sender.sendMessage(Component.text("¡Necesitas al menos 2 jugadores para comenzar el juego!", TextColor.color(0xFF0000)));
                return true;
            }
            plugin.startGame(players);
            sender.sendMessage(Component.text("¡El juego ha comenzado!", TextColor.color(0x7fff00)));
        } else if (args[0].equalsIgnoreCase("stop")) {
            plugin.endGame();
            sender.sendMessage(Component.text("¡El juego ha terminado!", TextColor.color(0xd2691e)));
        }

        if (args[0].equalsIgnoreCase("setup")&& args.length == 8) {
            Player player  = (Player) sender;
            String name = args[1];
            Location pos1 = new Location(player.getWorld(), Double.parseDouble(args[2]), Double.parseDouble(args[3]), Double.parseDouble(args[4]));
            Location pos2 = new Location(player.getWorld(), Double.parseDouble(args[5]), Double.parseDouble(args[6]), Double.parseDouble(args[7]));
            plugin.getArenaManager().addArena(name, pos1, pos2);
            plugin.getArenaManager().selectArena(name);
            sender.sendMessage(Component.text("¡La arena ha sido creada!", TextColor.color(0x7fff00)));
        } else if (args[0].equalsIgnoreCase("select") && args.length == 2) {
            plugin.getArenaManager().selectArena(args[1]);
            sender.sendMessage(Component.text("¡La arena ha sido seleccionada!", TextColor.color(0x7fff00)));
        } else if (args[0].equalsIgnoreCase("list")) {
            sender.sendMessage(Component.text("Arenas disponibles:", TextColor.color(0x7fff00)));
            for (String name : plugin.getArenaManager().getArenas().keySet()) {
                sender.sendMessage(Component.text(name, TextColor.color(0x7fff00)));
            }
        } else {
            sender.sendMessage(Component.text("Uso: /tntemu <start|stop|setup|select|list>", TextColor.color(0xFF0000)));
        } 
 


        return true;
    }
}

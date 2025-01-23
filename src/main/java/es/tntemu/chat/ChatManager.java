package es.tntemu.chat;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

/*
* Implementacion de un chat de juego
*/

public class ChatManager {

    public void sendMessage(Player sender, String message) {
        for (Player player : Bukkit.getOnlinePlayers()) {
            player.sendMessage(sender.getName() + ": " + message);
        }
    }
}

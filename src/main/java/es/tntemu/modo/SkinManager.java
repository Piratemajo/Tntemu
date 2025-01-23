package es.tntemu.modo;

import org.bukkit.entity.Player;

public class SkinManager {

    public void setSkin(Player player, String skinUrl) {
        player.sendMessage("Tu skin ha sido cambiada a: " + skinUrl);
    }
}

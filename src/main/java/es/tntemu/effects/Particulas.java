package es.tntemu.effects;

import org.bukkit.entity.Player;
import org.bukkit.Particle;

public class Particulas {
    
    public void generarParticulas(Player jugador) {
        jugador.getWorld().spawnParticle(Particle.HEART, jugador.getLocation(), 10);
    }
}

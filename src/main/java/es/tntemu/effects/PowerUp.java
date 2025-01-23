package es.tntemu.effects;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.function.Consumer;

public class PowerUp {

    private final Material material;
    private final Consumer<Player> effect;

    public PowerUp(Material material, Consumer<Player> effect) {
        this.material = material;
        this.effect = effect;
    }

    public Material getMaterial() {
        return material;
    }

    public void apply(Player player) {
        effect.accept(player);
    }
}

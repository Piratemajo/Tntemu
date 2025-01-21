package es.tntemu.menus;

import es.tntemu.Tntemu;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.Material;
import org.bukkit.inventory.meta.ItemMeta;

public class Menus implements Listener {

    private final Tntemu plugin;

    public Menus(Tntemu plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    public Inventory crearMenuAdmin() {
        Inventory menu = Bukkit.createInventory(null, 27, "Admin Menu");

        ItemStack item1 = new ItemStack(Material.DIAMOND);
        ItemMeta meta1 = item1.getItemMeta();
        meta1.setDisplayName("Iniciar Juego");
        item1.setItemMeta(meta1);
        menu.setItem(11, item1);

        ItemStack item2 = new ItemStack(Material.GOLD_INGOT);
        ItemMeta meta2 = item2.getItemMeta();
        meta2.setDisplayName("Detener Juego");
        item2.setItemMeta(meta2);
        menu.setItem(13, item2);

        ItemStack item3 = new ItemStack(Material.EMERALD);
        ItemMeta meta3 = item3.getItemMeta();
        meta3.setDisplayName("Listar Arenas");
        item3.setItemMeta(meta3);
        menu.setItem(15, item3);

        return menu;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (!event.getView().getTitle().equals("Admin Menu")) return;

        event.setCancelled(true);
        Player player = (Player) event.getWhoClicked();
        ItemStack clickedItem = event.getCurrentItem();

        if (clickedItem == null || clickedItem.getType() == Material.AIR) return;

        switch (clickedItem.getType()) {
            case DIAMOND:
                player.performCommand("tntemu start");
                break;
            case GOLD_INGOT:
                player.performCommand("tntemu stop");
                break;
            case EMERALD:
                player.performCommand("tntemu list");
                break;
            default:
                break;
        }

        player.closeInventory();
    }
}

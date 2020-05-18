package net.dopedealers.plugins.mutechat.utils;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class MuteInventory extends BukkitRunnable {
    private final int[] numbers;
    private final Player player;
    private final Inventory inv;
    private int current;

    public MuteInventory(final Player player) {
        this.numbers = new int[]{3, 4, 5, 2, 6, 1, 7, 0, 8, 9, 17, 18, 26, 19, 25, 20, 24, 21, 22, 23};
        this.current = -1;
        this.player = player;
        this.inv = player.getOpenInventory().getTopInventory();
    }

    public void run() {
        if (this.player.getOpenInventory().getTitle().equalsIgnoreCase(Util.c("&5zMuteChat"))) {
            this.cancel();
            return;
        }
        ++this.current;
        if (this.current >= 10000) {
            this.cancel();
            this.player.closeInventory();
            return;
        }
        final int id = new Random().nextInt(15);
        for (int i = 0; i < this.numbers.length; ++i) {
            this.inv.setItem(this.numbers[i], new ItemBuilder(Material.STAINED_GLASS_PANE, id).setName(" ").getStack());
        }
        this.player.updateInventory();
    }
}

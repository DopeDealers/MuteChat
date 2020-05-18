package net.dopedealers.plugins.mutechat.listeners;

import net.dopedealers.plugins.mutechat.Registry;
import net.dopedealers.plugins.mutechat.utils.Lang;
import net.dopedealers.plugins.mutechat.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;

import static net.dopedealers.plugins.mutechat.utils.Util.c;

public class InventoryListener implements Listener
{
    public boolean mutedbool;
    public Registry plugin;

    public InventoryListener(final Registry pl) {
        this.plugin = pl;
    }

    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) {
            return;
        }
        final Player player = (Player)e.getWhoClicked();
        final ItemStack is = e.getCurrentItem();
        final Inventory inv = e.getInventory();
        String test = Registry.chat.getPlayerPrefix(null, player);
        final String name = test + player.getName();
        if (inv == null || is == null) {
            return;
        }
        if (inv.getTitle().equalsIgnoreCase(Util.c("&5zMuteChat"))) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);
            if (is.getType() != Material.WOOL) {
                return;
            }
            switch (is.getDurability()) {
                case 14: {
                    this.mutedbool = false;
                    player.sendMessage(Util.c("&c&lFalse"));
                    Bukkit.broadcastMessage(Util.c(Lang.UNMUTE_SERVER_BC.getConfigValue(new String[]{this.plugin.muted_staff_sender, Lang.PREFIX.getConfigValue(null)})));
                    Bukkit.getScheduler().runTaskLater(this.plugin, new Runnable() {
                        @Override
                        public void run() {
                            plugin.muted_staff_sender = "";
                        }
                    }, 40);//The time to wait, in ticks (20 ticks = 1 second)
                    this.plugin.getLogger().info(String.valueOf(this.plugin.muted));
                    break;
                }
                case 5: {
                    this.mutedbool = true;
                    final Registry plugin = this.plugin;

                    plugin.muted_staff_sender = name;
                    this.plugin.muted_staff_sender = name;
                    player.sendMessage(Util.c("&6&lTrue!"));
                    Bukkit.broadcastMessage(Util.c(Lang.MUTE_SERVER_BC.getConfigValue(new String[]{this.plugin.muted_staff_sender, Lang.PREFIX.getConfigValue(null)})));
                    break;
                }
                default: {
                    throw new IllegalStateException("Unexpected value: " + is.getDurability());
                }
            }
            this.plugin.getLogger().info("Testing !!! > " + this.mutedbool);
            this.plugin.muted = this.mutedbool;
            this.plugin.muted_staff_sender = name;
            player.updateInventory();
            player.closeInventory();
        }
    }
}

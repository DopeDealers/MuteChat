package net.dopedealers.plugins.mutechat.listeners;

import net.dopedealers.plugins.mutechat.Registry;
import net.dopedealers.plugins.mutechat.utils.Lang;
import net.dopedealers.plugins.mutechat.utils.Util;
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

import static net.dopedealers.plugins.mutechat.utils.Util.c;

public class InventoryListener implements Listener
{
    public boolean mutedbool;
    public String muted_staff = "";
    public Registry plugin;

    public InventoryListener(Registry pl) {
        this.plugin = pl;
    }
    @EventHandler
    public void onInventoryClick(final InventoryClickEvent e) {
        if (!(e.getWhoClicked() instanceof Player)) {
            return;
        }
        final Player player = (Player)e.getWhoClicked();
        final ItemStack is = (ItemStack) e.getCurrentItem();
        final InventoryView inv = (InventoryView) e.getInventory();
        if (inv == null || is == null) {
            return;
        }
        if (inv.getTitle().equalsIgnoreCase(c("&5zMuteChat"))) {
            e.setCancelled(true);
            e.setResult(Event.Result.DENY);

            //Util.checkMuted(is, player, Registry.getInstance());

            if (is.getType() != Material.WOOL) {
                return;
            }

            switch (is.getDurability()) {
                case 14: {
                    player.sendMessage(c(Lang.MUTE_SERVER.getConfigValue(new String[]{player.getPlayer().getName(), Lang.PREFIX.getConfigValue(null)})));
                    mutedbool = false;
                    muted_staff = "";
                    break;
                }
                case 5: {
                    player.sendMessage(c(Lang.UNMUTE_SERVER.getConfigValue(new String[]{player.getPlayer().getName(), Lang.PREFIX.getConfigValue(null)})));
                    mutedbool = true;
                    muted_staff = this.plugin.muted_staff_sender = player.getPlayer().getName();
                    break;
                }
                default:
                    throw new IllegalStateException("Unexpected value: " + is.getDurability());
            }
            //Registry.getInstance().muted = mute_bool;
            this.plugin.getLogger().info("Testing !!! > " + mutedbool);
            this.plugin.muted = mutedbool;
            this.plugin.muted_staff_sender = muted_staff;
            player.updateInventory();
            player.closeInventory();
        }
    }
}
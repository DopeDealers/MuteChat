package net.dopedealers.plugins.mutechat.command.mc;

import net.dopedealers.plugins.mutechat.Registry;
import net.dopedealers.plugins.mutechat.command.CommandListener;
import net.dopedealers.plugins.mutechat.utils.*;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;

import static net.dopedealers.plugins.mutechat.Registry.*;

public class MuteChat extends CommandListener
{
    public Registry plugin;

    public MuteChat(final Registry plugin) {
        super("mutechat", "zmute.staff");
        this.plugin = plugin;
    }

    @Override
    public void execute(final CommandSender sender, final String[] args) {
        if (!(sender instanceof Player)) {
            return;
        }
        final Player player = (Player)sender;
        if (args.length == 0) {
            if (this.plugin.muted) {
                this.plugin.muted = false;
                String test = Registry.chat.getPlayerPrefix(player);
                this.plugin.muted_staff_sender = test + player.getName();
                sender.sendMessage(Util.c(Lang.UNMUTE_SERVER.getConfigValue(new String[]{this.plugin.muted_staff_sender, Lang.PREFIX.getConfigValue(null)})));
                Bukkit.broadcastMessage(Util.c(Lang.UNMUTE_SERVER_BC.getConfigValue(new String[]{this.plugin.muted_staff_sender, Lang.PREFIX.getConfigValue(null)})));
                Bukkit.getScheduler().runTaskLaterAsynchronously(this.plugin, new Runnable() {
                    @Override
                    public void run() {
                        plugin.muted_staff_sender = "";
                    }
                }, 40); //The time to wait, in ticks (20 ticks = 1 second)
                this.plugin.getLogger().info(String.valueOf(this.plugin.muted));
            }
            else {
                this.plugin.muted = true;
                String test = Registry.chat.getPlayerPrefix(player);
                this.plugin.muted_staff_sender = test + player.getName();
                sender.sendMessage(Util.c(Lang.MUTE_SERVER.getConfigValue(new String[]{this.plugin.muted_staff_sender, Lang.PREFIX.getConfigValue(null)})));
                Bukkit.broadcastMessage(Util.c(Lang.MUTE_SERVER_BC.getConfigValue(new String[]{this.plugin.muted_staff_sender, Lang.PREFIX.getConfigValue(null)})));
            }
        }
        else if (args[0].equals("gui")) {
            final Inventory inv = Bukkit.createInventory((InventoryHolder)null, 27, Util.c("&5zMuteChat"));
            if (this.plugin.muted) {
                inv.setItem(13, new ItemBuilder(Material.WOOL, 14).setName(Util.c("&cUnmute chat?")).setLore(Util.c("&6click this if you wana\nmunmute the chat!")).getStack());
            }
            else {
                inv.setItem(13, new ItemBuilder(Material.WOOL, 5).setName(Util.c("&aMute chat?")).setLore(Util.c("&6click this if you wana\nmute the chat!")).getStack());
            }
            player.openInventory(inv);
            new MuteInventory(player.getPlayer()).runTaskTimer((Plugin)Registry.getPlugin(Registry.class), 10L, 10L);
        }
    }
}
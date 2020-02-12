package net.dopedealers.plugins.mutechat.command.mc;

import net.dopedealers.plugins.mutechat.Registry;
import net.dopedealers.plugins.mutechat.command.CommandListener;
import net.dopedealers.plugins.mutechat.utils.ItemBuilder;
import net.dopedealers.plugins.mutechat.utils.Lang;
import net.dopedealers.plugins.mutechat.utils.MuteInventory;
import net.dopedealers.plugins.mutechat.utils.Util;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.plugin.Plugin;

import static net.dopedealers.plugins.mutechat.Registry.*;

public class MuteChat extends CommandListener {
    public Registry plugin;

    public MuteChat(Registry plugin) {
        super("mutechat", "test.test");
        this.plugin = plugin;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {

        if (!(sender instanceof Player)) {
            return;
        }
        final Player player = (Player)sender;
        if (args.length == 0) {

            if (this.plugin.muted) {
                this.plugin.muted = false;
                this.plugin.muted_staff_sender = "";
                sender.sendMessage(Util.c("&6&lFalse!"));
                this.plugin.getLogger().info(String.valueOf(this.plugin.muted));
            } else {
                this.plugin.muted = true;
                this.plugin.muted_staff_sender = player.getPlayer().getName();
                sender.sendMessage(Util.c("&6&lTrue!"));
                this.plugin.getLogger().info(String.valueOf(this.plugin.muted));
            }
        } else if (args[0].equals("gui")) {
            final Inventory inv = Bukkit.createInventory((InventoryHolder)null, 27, Util.c("&5zMuteChat"));
            if (this.plugin.muted) {
                inv.setItem(13, new ItemBuilder(Material.WOOL, 14).setName(Util.c("&cUnmute chat?")).setLore(Util.c("&6OWOW HAHA")).getStack());
                player.openInventory(inv);
            } else {
                inv.setItem(13, new ItemBuilder(Material.WOOL, 5).setName(Util.c("&aMute chat?")).setLore(Util.c("&3OWOW MARIjana")).getStack());
                player.openInventory(inv);
            }
            new MuteInventory(player.getPlayer()).runTaskTimer((Plugin) Registry.getPlugin((Class)Registry.class), 10L, 10L);
        } else if (args[0].equals("reload")) {
            try {
                getInstance().saveConfig();
                getInstance().saveDefaultConfig();
                getInstance().messagesFile.getConfig().options().copyDefaults(true);
                getInstance().messagesFile.saveConfig();
            } catch (Exception e) {
                getInstance().getLogger().info(e.getLocalizedMessage());
            } finally {

                sender.sendMessage(Util.c(Lang.RELOAD.getConfigValue(new String[]{"", Lang.PREFIX.getConfigValue(null)})));
            }
        }
    }
}
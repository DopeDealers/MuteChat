package net.dopedealers.plugins.mutechat.command.mc;

import net.dopedealers.plugins.mutechat.Registry;
import net.dopedealers.plugins.mutechat.command.CommandListener;
import net.dopedealers.plugins.mutechat.utils.Lang;
import net.dopedealers.plugins.mutechat.utils.Util;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ZMC extends CommandListener {

    public ZMC() {
        super("zmc", "zmute.help");

    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return;
        }
        if (args.length == 0) {
            sender.sendMessage(Util.c(Lang.NO_ARG.getConfigValue(new String[]{"/zmc <help:reload>", Lang.PREFIX.getConfigValue(null)})));
        } else if (args[0].equals("help")) {
            sender.sendMessage(Util.c("&7&m-&c&m>&r&7&m-----------------&c&m<&r&7&m-"));
            sender.sendMessage(" ");
            sender.sendMessage(Util.c("         &5zMuteChat"));
            sender.sendMessage(" ");
            sender.sendMessage(Util.c("&cCommands:"));
            sender.sendMessage(Util.c("&b/mutechat [opens gui]\n&b/mutechat toggle"));
            sender.sendMessage(" ");
            sender.sendMessage(Util.c("&7&m-&c&m>&r&7&m-----------------&c&m<&r&7&m-"));
        } else if (args[0].equals("reload") && ((Player) sender).getPlayer().hasPermission("zmute.admin")) {
            try {
                Registry.getInstance().saveConfig();
                Registry.getInstance().saveDefaultConfig();
                Registry.getInstance().messagesFile.getConfig().options().copyDefaults(true);
                Registry.getInstance().messagesFile.saveConfig();
            } catch (Exception e) {
                Registry.getInstance().getLogger().info(e.getLocalizedMessage());
            } finally {

                sender.sendMessage(Util.c(Lang.RELOAD.getConfigValue(new String[] {"", Lang.PREFIX.getConfigValue(null)})));
            }
        }
    }
}

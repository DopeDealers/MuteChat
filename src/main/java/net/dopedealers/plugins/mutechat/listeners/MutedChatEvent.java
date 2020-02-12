package net.dopedealers.plugins.mutechat.listeners;

import net.dopedealers.plugins.mutechat.Registry;
import net.dopedealers.plugins.mutechat.utils.Lang;
import net.dopedealers.plugins.mutechat.utils.Util;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.*;

public class MutedChatEvent implements Listener {

    @EventHandler(priority = EventPriority.HIGH)
    public void chat(final AsyncPlayerChatEvent e) {
        final Player player = (Player)e.getPlayer();
        if (Registry.getInstance().muted) {
            if (!player.hasPermission("zmutechat.staff")) {
                e.setCancelled(true);
                player.sendMessage(Util.c(Lang.MUTED_CHAT.getConfigValue(new String[] {Registry.getInstance().muted_staff_sender, Lang.PREFIX.getConfigValue(null)})));
            }
        }
    }
}

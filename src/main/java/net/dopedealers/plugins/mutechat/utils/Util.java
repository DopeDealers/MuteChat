package net.dopedealers.plugins.mutechat.utils;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.Random;

public class Util {
    public static String c(final String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static Random random = new Random();
}

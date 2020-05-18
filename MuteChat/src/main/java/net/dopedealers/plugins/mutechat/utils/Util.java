package net.dopedealers.plugins.mutechat.utils;

import org.bukkit.ChatColor;

import java.util.Random;

public class Util {
    public static String c(final String input) {
        return ChatColor.translateAlternateColorCodes('&', input);
    }

    public static Random random = new Random();

}

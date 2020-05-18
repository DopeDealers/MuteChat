package net.dopedealers.plugins.mutechat.utils;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;

public enum Lang
{
    PREFIX("message.prefix", "&5zMuteChat &0>&r"),
    NO_PERM("message.no_perm", "{1} &cYou do not have the permission node:&r &3{0}"),
    MUTED_CHAT("messages.muted_chat", "{1} &cThe chat has been muted by {0}"),
    NO_ARG("message.no_arg", "{1} &cYou did not provide an argument &6{0}"),
    MUTE_SERVER("message.staff_mute", "{1} &aYou have muted the server!"),
    MUTE_SERVER_BC("message.staff_mute.bc", "{1} &6{0}&r &aHas muted the entire server"),
    UNMUTE_SERVER("message.staff_unmute", "{1} &cYou have unmuted the server!"),
    UNMUTE_SERVER_BC("message.staff_unmute.bc", "{1} &6{0}&r &cHas unmuted the entire server"),
    RELOAD("message.reload", "{1} &cReloaded the plugin!"),
    MC_TOGGLE("mute.toggle", "true"),
    MC_STAFF("mute.staff_member", "");

    private String path;
    private String def;
    private static FileConfiguration LANG;

    Lang(final String path, final String start) {
        this.path = path;
        this.def = start;
    }

    public static void setFile(final FileConfiguration config) {
        Lang.LANG = config;
    }

    public String getDefault() {
        return this.def;
    }

    public String getPath() {
        return this.path;
    }

    public String getConfigValue(final String[] args) {
        String value = ChatColor.translateAlternateColorCodes('&', Lang.LANG.getString(this.path, this.def));
        if (args == null) {
            return value;
        }
        if (args.length == 0) {
            return value;
        }
        for (int i = 0; i < args.length; ++i) {
            value = value.replace("{" + i + "}", args[i]);
        }
        return value;
    }
}


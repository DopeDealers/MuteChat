package net.dopedealers.plugins.mutechat.utils;

import org.bukkit.configuration.file.FileConfiguration;

public enum Key {
    LICENSE_KEY("License_Key", "");

    private String path;
    private String def;
    private static FileConfiguration LANG;

    Key(final String path, final String start) {
        this.path = path;
        this.def = start;
    }

    public static void setFile(final FileConfiguration config) {
        Key.LANG = config;
    }

    public String getDefault() {
        return this.def;
    }

    public String getPath() {
        return this.path;
    }

    public String getConfigValue() {
        String value = Key.LANG.getString(this.path, this.def);
        return value;
    }
}

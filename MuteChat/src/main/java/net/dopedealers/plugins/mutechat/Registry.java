package net.dopedealers.plugins.mutechat;

import net.dopedealers.plugins.mutechat.command.CommandListener;
import net.dopedealers.plugins.mutechat.command.CommandRegistry;
import net.dopedealers.plugins.mutechat.listeners.InventoryListener;
import net.dopedealers.plugins.mutechat.listeners.MutedChatEvent;
import net.dopedealers.plugins.mutechat.utils.ConfigWrapper;
import net.dopedealers.plugins.mutechat.utils.Lang;
import net.dopedealers.plugins.mutechat.utils.Util;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public class Registry extends JavaPlugin {

    private static Registry Instance;
    public boolean muted;
    public String muted_staff_sender;
    public ConfigWrapper messagesFile;

    public Registry() {
        this.messagesFile = new ConfigWrapper(this, "", "messages.yml");
        muted = new Boolean("false");
    }


    public static Registry getInstance() {
        return Registry.Instance;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        this.getConfig().options().copyDefaults(true);
        this.saveConfig();
        this.saveDefaultConfig();

        Registry.Instance = this;
        this.getServer().getPluginManager().registerEvents(new InventoryListener(this), this);
        this.getServer().getPluginManager().registerEvents(new MutedChatEvent(), this);
        loadMessages();

        new CommandRegistry();
        //Bukkit.getPluginManager().registerEvents(new PlayerBlackListJoin(), this);
        // }
    }
    // LWTY-QDVB-3J5T-X6D5
    @Override
    public void onDisable() {
        super.onDisable();
        if (muted && Lang.MC_TOGGLE.getConfigValue(null).equals("false")) {
            this.messagesFile.getConfig().set("muted", "true");
        } else if (!muted && Lang.MC_TOGGLE.getConfigValue(null).equals("true")) {
            this.messagesFile.getConfig().set("muted", "false");
        }
    }

    public boolean onCommand(final CommandSender s, final Command c, final String string, final String[] args) {
        final CommandListener command = CommandRegistry.getCommand(c.getName());
        if (command == null) {
            return false;
        }
        if (s instanceof Player) {
            final Player player = (Player)s;
            if (command.getPermission().equals("") || player.hasPermission(command.getPermission())) {
                command.execute(s, args);
            }
            else {
                player.sendMessage(Util.c(Lang.NO_PERM.getConfigValue( new String[] { command.getPermission(), Lang.PREFIX.getConfigValue(null)} )));
            }
            return true;
        }
        command.execute(s, args);
        return true;
    }
    private void loadMessages() {
        Lang.setFile(this.messagesFile.getConfig());

        for (final Lang value : Lang.values()) {
            this.messagesFile.getConfig().addDefault(value.getPath(), value.getDefault());
        }
        this.messagesFile.getConfig().options().copyDefaults(true);
        this.messagesFile.saveConfig();
    }

}

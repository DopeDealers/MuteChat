package net.dopedealers.plugins.mutechat;

import net.dopedealers.plugins.mutechat.command.CommandListener;
import net.dopedealers.plugins.mutechat.command.CommandRegistry;
import net.dopedealers.plugins.mutechat.listeners.InventoryListener;
import net.dopedealers.plugins.mutechat.listeners.MutedChatEvent;
import net.dopedealers.plugins.mutechat.utils.ConfigWrapper;
import net.dopedealers.plugins.mutechat.utils.Lang;
import net.dopedealers.plugins.mutechat.utils.Util;
import net.milkbowl.vault.chat.Chat;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.regex.Pattern;

public class Registry extends JavaPlugin {

    private static Registry Instance;
    public static Chat chat;
    public boolean muted;
    public String muted_staff_sender;
    public ConfigWrapper messagesFile;

    public Registry() {
        this.messagesFile = new ConfigWrapper(this, "", "messages.yml");
        muted = Boolean.FALSE;
    }

    public static Registry getInstance() {
        return Registry.Instance;
    }

    @Override
    public void onEnable() {
        super.onEnable();
        String version = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3];

        // todo 
        if (version.equals("v1_8_R1")) {
            // v1_8_R1 code
        } else if (version.equals("v1_8_R2")) {
            // v1_8_R2 code
        } else if (version.equals("v1_8_R3")) {
            // v1_8_R3 code
        } else if (version.equals("v1_9_R1")) {
            // v1_9_R1 code
        }
        Registry.Instance = this;
        this.getServer().getPluginManager().registerEvents(new InventoryListener(this), this);
        this.getServer().getPluginManager().registerEvents(new MutedChatEvent(), this);
        new CommandRegistry();
        setupChat();
        loadMessages();
        checkDepend();
    }
    @Override
    public void onDisable() {
        super.onDisable();
        if (muted && Lang.MC_TOGGLE.getConfigValue(null).equals("false")) {
            this.messagesFile.getConfig().set("muted", "true");
        }
    }

    public void checkDepend() {
        CommandSender console = getServer().getConsoleSender();

        String[] dependencies = {"Vault", "LuckPerms"}; // add your dependency names here
        console.sendMessage("§8*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*");
        console.sendMessage(" ");
        console.sendMessage("§8*=*§r        §5zMuteChat        §8*=*");
        console.sendMessage(" ");
        console.sendMessage("§8- Checking if the dependencies are on the server.");
        console.sendMessage("§8*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*");
        for (String plugin_names : dependencies) { // loops through the stringlist for names
            Plugin plugin_name = getServer().getPluginManager().getPlugin(plugin_names); // gets plugin on server
            if (plugin_name != null && plugin_name.isEnabled()) { // checks if its enabled
                console.sendMessage("§7- §a" + plugin_name + " §7Has successfully been found and registered");
            } else { // if not disable
                console.sendMessage("§7- §c" + plugin_names + " §7Has not been found. disabling");
                Bukkit.getScheduler().runTaskLaterAsynchronously(getInstance(), new Runnable() {
                    @Override
                    public void run() {
                        getServer().getPluginManager().disablePlugin(getInstance());
                    }
                }, 40);
            }
        }
        console.sendMessage(" ");
        console.sendMessage("§8*=*=*=*=*=*=*=*=*=*=*=*=*=*=*=*");
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

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        assert rsp != null;
        chat = rsp.getProvider();
        return chat != null;
    }
}

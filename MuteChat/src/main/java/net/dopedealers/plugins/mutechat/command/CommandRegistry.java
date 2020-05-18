package net.dopedealers.plugins.mutechat.command;



import net.dopedealers.plugins.mutechat.Registry;
import net.dopedealers.plugins.mutechat.command.mc.MuteChat;
import net.dopedealers.plugins.mutechat.command.mc.ZMC;
import net.dopedealers.plugins.mutechat.command.mc.Test;

import java.util.ArrayList;
import java.util.List;

public class CommandRegistry {
    private static List<CommandListener> commands;

    public CommandRegistry() {
        if (CommandRegistry.commands == null) {
            CommandRegistry.commands = new ArrayList<CommandListener>();
        }
        CommandRegistry.commands.add(new ZMC());
        CommandRegistry.commands.add(new Test());
        CommandRegistry.commands.add(new MuteChat(Registry.getInstance()));

    }

    public static CommandListener getCommand(final String name) {
        for (final CommandListener commands : getCommands()) {
            if (commands.getName().equalsIgnoreCase(name)) {
                return commands;
            }
        }
        return null;
    }

    private static List<CommandListener> getCommands() {
        return CommandRegistry.commands;
    }
}

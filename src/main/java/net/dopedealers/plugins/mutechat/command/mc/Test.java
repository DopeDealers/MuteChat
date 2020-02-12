package net.dopedealers.plugins.mutechat.command.mc;

import net.dopedealers.plugins.mutechat.Registry;
import net.dopedealers.plugins.mutechat.command.CommandListener;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Test extends CommandListener {

    public Test() {
        super("test", "test.test");

    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        if (!(sender instanceof Player)) {
            return;
        }

    }
}

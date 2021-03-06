package de.xcrafttm.borderxp.commands;

import de.xcrafttm.borderxp.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class BorderToggle implements CommandExecutor {

    public BorderToggle(Main plugin) {
        Objects.requireNonNull(plugin.getCommand("bordertoggle")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            Main.isEnabled = !Main.isEnabled;
            if (Main.isEnabled) {
                commandSender.sendMessage("§8[§6BorderXP§8] §6Challenge §aEnabled!");
            } else commandSender.sendMessage("§8[§6BorderXP§8] §6Challenge §cDisabled!");
        } else {
            commandSender.sendMessage("§8[§6BorderXP§8] §cConsole is not allowed to use this Command!");
        }
        return true;
    }
}

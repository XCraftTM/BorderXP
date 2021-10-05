package de.xcrafttm.borderxp.commands;

import de.xcrafttm.borderxp.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Objects;

public class VillageToggle implements CommandExecutor {

    private final Main plugin;

    public VillageToggle(Main plugin) {
        this.plugin = plugin;
        Objects.requireNonNull(plugin.getCommand("villagetoggle")).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (commandSender instanceof Player) {
            if (plugin.getBorderConfig().getVillageToggle() == "true") {
                plugin.getBorderConfig().setFalse();
                commandSender.sendMessage("§8[§6BorderXP§8] §cVillage Spawn §lDisabled!");
                commandSender.sendMessage("§8[§6BorderXP§8] §cYou will need to Reset your World and Restart!");
            } else {
                plugin.getBorderConfig().setTrue();
                commandSender.sendMessage("§8[§6BorderXP§8] §aVillage Spawn §lEnabled!");
                commandSender.sendMessage("§8[§6BorderXP§8] §cYou will need to Reset your World and Restart!");
            }
        } else {
            commandSender.sendMessage("§8[§6BorderXP§8] §cConsole is not allowed to use this Command!");
        }
        return true;
    }
}

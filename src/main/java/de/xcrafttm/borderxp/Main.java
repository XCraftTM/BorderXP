package de.xcrafttm.borderxp;

import de.xcrafttm.borderxp.commands.BorderToggle;
import de.xcrafttm.borderxp.listener.PlayerConnectionEvent;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Main extends JavaPlugin implements Listener {

    public static boolean isEnabled = true;
    private final ConsoleCommandSender console = Bukkit.getConsoleSender();

    @Override
    public void onLoad() {
        console.sendMessage("§8[§6BorderXP§8] §2Loading Plugin...");
    }

    @Override
    public void onEnable() {
        console.sendMessage("§8[§6BorderXP§8] §aPlugin Enabled!");

        new BorderToggle(this);

        init();

        Bukkit.getPluginManager().registerEvents(this, this);

        World world = Bukkit.getWorld("world");
        world.setGameRule(GameRule.SPAWN_RADIUS, 0);
    }

    @Override
    public void onDisable() {
        console.sendMessage("§8[§6BorderXP§8] §cPlugin Disabled!");
    }

    @EventHandler
    public void onPlayerExpChange(PlayerLevelChangeEvent event) {
        if (!Main.isEnabled)
            return;
        AtomicInteger maxXP = new AtomicInteger(1);
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (maxXP.get() < player.getLevel())
                maxXP.set((int) player.getLevel());
        });
        WorldBorder border = Objects.requireNonNull(Bukkit.getWorld("world")).getWorldBorder();
        border.setSize(maxXP.get(), 5);

    }
    private void init() {
        new PlayerConnectionEvent(this);
    }
}

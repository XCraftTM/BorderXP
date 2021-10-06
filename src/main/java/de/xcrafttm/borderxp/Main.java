package de.xcrafttm.borderxp;

import de.xcrafttm.borderxp.commands.BorderToggle;
import de.xcrafttm.borderxp.commands.VillageToggle;
import de.xcrafttm.borderxp.listener.PlayerConnectionEvent;
import de.xcrafttm.borderxp.utils.BorderConfig;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bstats.bukkit.Metrics;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class Main extends JavaPlugin implements Listener {

    private BorderConfig borderConfig;

    public static boolean isEnabled = true;
    private final ConsoleCommandSender console = Bukkit.getConsoleSender();

    @Override
    public void onLoad() {
        borderConfig = new BorderConfig();
        console.sendMessage("§8[§6BorderXP§8] §2Loading Plugin...");
    }

    @Override
    public void onEnable() {
        // All you have to do is adding the following two lines in your onEnable method.
        // You can find the plugin ids of your plugins on the page https://bstats.org/what-is-my-plugin-id
        int pluginId = 12971; // <-- Replace with the id of your plugin!
        Metrics metrics = new Metrics(this, pluginId);

        console.sendMessage("§8[§6BorderXP§8] §aPlugin Enabled!");

        new BorderToggle(this);
        new VillageToggle(this);

        init();

        Bukkit.getPluginManager().registerEvents(this, this);

        World world = Bukkit.getWorld("world");
        world.setGameRule(GameRule.SPAWN_RADIUS, 0);
    }

    @Override
    public void onDisable() {
        console.sendMessage("§8[§6BorderXP§8] §cPlugin Disabled!");
    }

    private void init() {
        new PlayerConnectionEvent(this);
    }

    @EventHandler
    public void onPlayerExpChange(PlayerLevelChangeEvent event) {
        if (!Main.isEnabled)
            return;
        AtomicInteger maxXP = new AtomicInteger(1);
        Bukkit.getOnlinePlayers().forEach(player -> {
            if (maxXP.get() < player.getLevel())
                maxXP.set(player.getLevel());
        });
        WorldBorder worldborder = Objects.requireNonNull(Bukkit.getWorld("world")).getWorldBorder();
        WorldBorder netherborder = Objects.requireNonNull(Bukkit.getWorld("world_nether")).getWorldBorder();
        WorldBorder endborder = Objects.requireNonNull(Bukkit.getWorld("world_the_end")).getWorldBorder();
        worldborder.setSize(maxXP.get(), 5);
        netherborder.setSize(maxXP.get(), 5);
        endborder.setSize(maxXP.get(), 5);

    }

    public BorderConfig getBorderConfig() {
        return borderConfig;
    }

}

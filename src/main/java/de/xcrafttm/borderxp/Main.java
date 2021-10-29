package de.xcrafttm.borderxp;

import de.xcrafttm.borderxp.commands.BorderToggle;
import de.xcrafttm.borderxp.commands.VillageToggle;
import de.xcrafttm.borderxp.listener.PlayerConnectionEvent;
import de.xcrafttm.borderxp.utils.BorderConfig;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerLevelChangeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bstats.bukkit.Metrics;

import java.util.Objects;

public class Main extends JavaPlugin implements Listener {

    private BorderConfig borderConfig;
    public static int level;
    public static float exp;

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
        if (Bukkit.getServer().getOnlinePlayers().size() >= 2) {
            float xpfloat = event.getPlayer().getExp();
            event.getPlayer().sendExperienceChange(xpfloat, event.getPlayer().getLevel());
            for (Player player : Bukkit.getOnlinePlayers()){
                if (player != event.getPlayer()) {
                    player.setLevel(event.getPlayer().getLevel());
                    player.setExp(event.getPlayer().getExp());
                    level = event.getPlayer().getLevel();
                    exp = event.getPlayer().getExp();
                }
            }
        }
        WorldBorder worldborder = Objects.requireNonNull(Bukkit.getWorld("world")).getWorldBorder();
        WorldBorder netherborder = Objects.requireNonNull(Bukkit.getWorld("world_nether")).getWorldBorder();
        WorldBorder endborder = Objects.requireNonNull(Bukkit.getWorld("world_the_end")).getWorldBorder();
        worldborder.setSize(event.getPlayer().getLevel(), 3);
        netherborder.setSize(event.getPlayer().getLevel(), 3);
        endborder.setSize(event.getPlayer().getLevel(), 3);
    }

    @EventHandler
    public void whenPlayerDeath(PlayerDeathEvent e) {
        e.setKeepLevel(true);
        e.setDroppedExp(0);
    }

    public BorderConfig getBorderConfig() {
        return borderConfig;
    }

}

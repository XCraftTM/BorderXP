package de.xcrafttm.borderxp.listener;

import de.xcrafttm.borderxp.Main;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;

public class PlayerConnectionEvent implements Listener {

    private final Main plugin;

    public PlayerConnectionEvent(Main plugin) {
        this.plugin = plugin;
        Bukkit.getPluginManager().registerEvents(this, plugin);
    }

    private final ConsoleCommandSender console = Bukkit.getConsoleSender();

    @EventHandler
    public void onFirstJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        World world = Bukkit.getWorld("world");
        Location village = player.getWorld().locateNearestStructure(player.getLocation(), StructureType.VILLAGE, 5000, true);
        if (village == null) {
            player.sendMessage("§8[§6BorderXP§8] §cNo Village was found, to spawn you in.");
            return;
        }
        Location loc = player.getWorld().getHighestBlockAt(village).getLocation().add(0.5, 1, 0.5);
        village.getChunk().load(true);

        if (!player.hasPlayedBefore()) {
        world.setSpawnLocation(loc);
        player.teleport(loc);
        WorldBorder border =  Objects.requireNonNull(Bukkit.getWorld("world")).getWorldBorder();
        border.setCenter(loc.getBlockX() + 0.5, loc.getBlockZ() + 0.5);
        border.setSize(1);
        player.setLevel(1);
        }
    }
}

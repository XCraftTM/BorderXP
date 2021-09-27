package de.xcrafttm.borderxp.listener;

import de.xcrafttm.borderxp.Main;
import org.bukkit.*;
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

    @EventHandler
    public void onFirstJoin(PlayerJoinEvent event) throws InterruptedException {
        Player player = event.getPlayer();
        World world = Bukkit.getWorld("world");
        Location loc = Bukkit.getWorld("world").locateNearestStructure(player.getLocation(), StructureType.VILLAGE, 2, false).getBlock().getLocation();
        loc.add(0,80,0);
        player.setHealth(20);
        player.setFoodLevel(25);

        //if (player.hasPlayedBefore()) {
        world.setSpawnLocation(loc);
        player.teleport(loc);
        WorldBorder border =  Objects.requireNonNull(Bukkit.getWorld("world")).getWorldBorder();
        border.setCenter(loc.getBlockX() + 0.5, loc.getBlockZ() + 0.5);
        border.setSize(1);
        //}
    }
}

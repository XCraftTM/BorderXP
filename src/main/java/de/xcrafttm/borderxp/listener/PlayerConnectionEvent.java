package de.xcrafttm.borderxp.listener;

import de.xcrafttm.borderxp.Main;
import org.bukkit.*;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

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
            player.sendMessage("§8[§6BorderXP§8] §cNo Village was found, to spawn you in. Please Reset the World and Restart!");
            return;
        }
        Location loc = player.getWorld().getHighestBlockAt(village).getLocation().add(0.5, 1, 0.5);
        village.getChunk().load(true);

        if (!player.hasPlayedBefore()) {
        world.setSpawnLocation(loc);
        player.teleport(loc);
        WorldBorder border =  Objects.requireNonNull(Bukkit.getWorld("world")).getWorldBorder();
        WorldBorder netherborder = Objects.requireNonNull(Bukkit.getWorld("world_nether")).getWorldBorder();
        WorldBorder endborder = Objects.requireNonNull(Bukkit.getWorld("world_the_end")).getWorldBorder();
        border.setCenter(loc.getBlockX() + 0.5, loc.getBlockZ() + 0.5);
        border.setSize(1);

        netherborder.setCenter(Objects.requireNonNull(Bukkit.getWorld("world_nether")).getSpawnLocation());
        endborder.setCenter(Objects.requireNonNull(Bukkit.getWorld("world_the_end")).getSpawnLocation());

            AtomicInteger maxXP = new AtomicInteger(1);
            Bukkit.getOnlinePlayers().forEach(player1 -> {
                if (maxXP.get() < player1.getLevel())
                    maxXP.set((int) player1.getLevel());
            });
        netherborder.setSize(maxXP.get(), 5);
        endborder.setSize(maxXP.get(), 5);

        player.setLevel(1);
        }
    }
}

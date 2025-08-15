package com.blueind.minjunSMP.smp;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.Random;

public class RandomSpawn implements Listener {

    private final int SPAWN_RADIUS = 10000;
    private final Random random = new Random();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPlayedBefore()) {
            randomTP(event.getPlayer());
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent event) {
        World overworld = event.getPlayer().getServer().getWorlds().get(0);
        var randomLocation = createRandomLocation(overworld);
        event.setRespawnLocation(randomLocation);
    }

    private void randomTP(Player player) {
        World overworld = player.getServer().getWorlds().get(0);
        var randomLocation = createRandomLocation(overworld);
        player.teleport(randomLocation);
    }

    private Location createRandomLocation(World world) {
        int x = random.nextInt(SPAWN_RADIUS * 2) - SPAWN_RADIUS;
        int z = random.nextInt(SPAWN_RADIUS * 2) - SPAWN_RADIUS;

        int y = world.getHighestBlockYAt(x, z) + 1;

        return new Location(world, x, y, z);
    }
}
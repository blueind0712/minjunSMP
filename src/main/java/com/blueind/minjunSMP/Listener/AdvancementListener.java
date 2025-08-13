package com.blueind.minjunSMP.Listener;

import com.destroystokyo.paper.event.player.PlayerAdvancementCriterionGrantEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAdvancementDoneEvent;

import java.util.List;

public class AdvancementListener implements Listener {

    private final List<String> blockedWorlds = List.of("world_nether", "world_the_end");

    public AdvancementListener() {
    }

    @EventHandler
    public void saveAdvancement(PlayerAdvancementCriterionGrantEvent e) {
        var playerWorld = e.getPlayer().getWorld().getName();
        for (var world : blockedWorlds) {
            if (world.equals(playerWorld)) {
                e.setCancelled(true);
                return;
            }
        }
    }

    @EventHandler
    public void broadcastAdvancement(PlayerAdvancementDoneEvent e) {
        e.message(null);
    }
}
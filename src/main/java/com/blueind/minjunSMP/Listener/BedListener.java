package com.blueind.minjunSMP.Listener;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.event.player.PlayerBedEnterEvent;

public class BedListener implements Listener {

    // 침대 사용(잠자기, 리스폰 지점 설정)을 금지합니다.
    @EventHandler
    public void onPlayerBedEnter(PlayerBedEnterEvent event) {
        event.setCancelled(true);
        event.getBed().setType(Material.AIR);
        event.getPlayer().sendMessage("침대를 사용할 수 없습니다!");
    }

    // 침대 제작을 금지합니다.
    @EventHandler
    public void onCraftItem(CraftItemEvent event) {
        if (event.getRecipe().getResult().getType().name().contains("_BED")) {
            event.setCancelled(true);
            event.getWhoClicked().sendMessage("침대를 제작할 수 없습니다!");
        }
    }

    // 침대 설치를 금지합니다.
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        if (event.getBlock().getType().name().contains("_BED")) {
            event.setCancelled(true);
            event.getPlayer().sendMessage("침대를 설치할 수 없습니다!");
        }
    }

    // 침대 블록 파괴 시 아이템 드랍을 금지합니다.
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        if (event.getBlock().getType().name().contains("_BED")) {
            event.setDropItems(false);
            event.getPlayer().sendMessage("침대가 파괴되었지만 아이템은 드랍되지 않습니다.");
        }
    }

}
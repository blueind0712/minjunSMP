package com.blueind.minjunSMP.smp;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.util.Vector;

public class TraceCompass implements Listener {
    private final JavaPlugin plugin;
    private final Component specialCompassName;

    public TraceCompass(JavaPlugin plugin) {
        this.specialCompassName = Component.text(String.valueOf(ChatColor.AQUA) + "추적 나침반");
        this.plugin = plugin;
        this.registerSpecialCompassRecipe();
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        ItemStack item = player.getInventory().getItemInMainHand();
        if (item.getType() == Material.COMPASS && item.hasItemMeta() && (event.getAction() == Action.RIGHT_CLICK_AIR || event.getAction() == Action.RIGHT_CLICK_BLOCK)) {
            ItemMeta meta = item.getItemMeta();
            if (meta != null && meta.hasDisplayName()) {
                String plainName = PlainTextComponentSerializer.plainText().serialize(meta.displayName());
                if (plainName.equals(PlainTextComponentSerializer.plainText().serialize(this.specialCompassName))) {
                    Player nearestPlayer = this.findNearestPlayer(player);
                    if (nearestPlayer != null && !nearestPlayer.getUniqueId().equals(player.getUniqueId())) {
                        Location startLocation = player.getEyeLocation();
                        Location endLocation = nearestPlayer.getEyeLocation();
                        Vector direction = endLocation.toVector().subtract(startLocation.toVector()).normalize();
                        Location currentLocation = startLocation.clone();

                        for(int i = 0; i < 6; ++i) {
                            currentLocation.add(direction.clone().multiply((double)0.5F));
                            currentLocation.getWorld().spawnParticle(Particle.FLAME, currentLocation, 3, (double)0.0F, (double)0.0F, (double)0.0F, (double)0.0F);
                        }

                        PlayerInventory inventory = player.getInventory();
                        if (inventory.getItemInMainHand().getAmount() > 1) {
                            inventory.getItemInMainHand().setAmount(inventory.getItemInMainHand().getAmount() - 1);
                        } else {
                            inventory.setItemInMainHand(new ItemStack(Material.AIR));
                        }
                    } else {
                        player.sendMessage(String.valueOf(ChatColor.RED) + "주변에 추적할 플레이어가 없습니다.");
                    }
                }
            }
        }

    }

    private Player findNearestPlayer(Player sourcePlayer) {
        Player nearestPlayer = null;
        double nearestDistanceSquared = Double.MAX_VALUE;

        for(Player otherPlayer : Bukkit.getOnlinePlayers()) {
            if (!otherPlayer.getUniqueId().equals(sourcePlayer.getUniqueId())) {
                double distanceSquared = sourcePlayer.getLocation().distanceSquared(otherPlayer.getLocation());
                if (distanceSquared < nearestDistanceSquared) {
                    nearestDistanceSquared = distanceSquared;
                    nearestPlayer = otherPlayer;
                }
            }
        }

        return nearestPlayer;
    }

    private void registerSpecialCompassRecipe() {
        ItemStack result = new ItemStack(Material.COMPASS);
        ItemMeta meta = result.getItemMeta();
        if (meta != null) {
            meta.displayName(this.specialCompassName);
            result.setItemMeta(meta);
        }

        NamespacedKey key = new NamespacedKey(this.plugin, "special_compass_recipe");
        ShapedRecipe recipe = new ShapedRecipe(key, result);
        recipe.shape(new String[]{" D ", "DCD", " D "});
        recipe.setIngredient('D', Material.DIAMOND);
        recipe.setIngredient('C', Material.COMPASS);
        Bukkit.addRecipe(recipe);
    }
}
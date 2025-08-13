package com.blueind.minjunSMP;

import com.blueind.minjunSMP.Listener.AdvancementListener;
import com.blueind.minjunSMP.Listener.BedListener;
import com.blueind.minjunSMP.Listener.VillagerListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinjunSMP extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new BedListener(), this);
        getServer().getPluginManager().registerEvents(new VillagerListener(), this);
        getServer().getPluginManager().registerEvents(new AdvancementListener(), this);

    }

    @Override
    public void onDisable() {

    }
}

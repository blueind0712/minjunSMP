package com.blueind.minjunSMP;

import com.blueind.minjunSMP.Listener.BedListener;
import org.bukkit.plugin.java.JavaPlugin;

public final class MinjunSMP extends JavaPlugin {

    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(new BedListener(), this);

    }

    @Override
    public void onDisable() {

    }
}

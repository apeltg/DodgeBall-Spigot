package me.sadev.dodge;

import org.bukkit.plugin.java.JavaPlugin;

public final class Dodge extends JavaPlugin {

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();
        saveDefaultConfig();
    }
}

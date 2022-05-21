package me.sadev.dodge.arena.listeners;

import me.sadev.dodge.Dodge;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitEventHandler implements Listener {

    Dodge plugin = Dodge.getInstance();

    @EventHandler
    public void onQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        Bukkit.getScheduler().runTaskAsynchronously(plugin,() -> {
            plugin.getLog().info(plugin.getUserAccountController().getUserAccount(player.getUniqueId()).toString());
        });
    }
}

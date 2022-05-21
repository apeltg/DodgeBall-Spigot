package me.sadev.dodge.arena.listeners;

import me.sadev.dodge.Dodge;
import me.sadev.dodge.database.models.UserAccount;
import me.sadev.dodge.database.models.UserStatus;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.Timestamp;
import java.time.Instant;

public class PlayerJoinEventHandler implements Listener {

    Dodge plugin = Dodge.getInstance();

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        plugin.getLog().info("Carregando/criando o jogador.");
        Bukkit.getScheduler().runTaskAsynchronously(plugin,() -> {
            plugin.getLog().info("Tem uma conta??");
            if (plugin.getUserAccountController().containsUser(player.getUniqueId())) return;
            plugin.getLog().info("Não tem, vou criar uma");
            UserAccount userAccount = new UserAccount(
                    player.getUniqueId(),
                    -1,
                    Timestamp.from(Instant.now()),
                    new UserStatus(0, 0, 0, 0),
                    player.getName());
            plugin.getUserAccountRepository().insertOrUpdateUserAccount(userAccount);
            plugin.getLog().info("Usuario criado com sucesso.\n" + userAccount);
        });
    }
}

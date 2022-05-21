package me.sadev.dodge;

import lombok.Getter;
import me.sadev.dodge.arena.listeners.PlayerJoinEventHandler;
import me.sadev.dodge.arena.listeners.PlayerQuitEventHandler;
import me.sadev.dodge.database.user.UserAccountRepository;
import me.sadev.dodge.database.user.UserAccountController;
import me.sadev.dodge.database.connection.SQLManager;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Getter
public final class Dodge extends JavaPlugin {

    @Getter
    private static Dodge instance;
    private SQLManager sqlManager;
    private UserAccountController userAccountController;
    private UserAccountRepository userAccountRepository;
    @Getter
    private final Logger log = LoggerFactory.getLogger("Dodge");

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();
        log.info(ChatColor.GREEN + "Iniciando...");

        instance = this;
        saveDefaultConfig();

        log.info("Iniciando SQLManager.");
        sqlManager = new SQLManager(this);
        userAccountController = new UserAccountController();
        userAccountRepository = new UserAccountRepository();

        getServer().getPluginManager().registerEvents(new PlayerJoinEventHandler(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitEventHandler(), this);

        log.info(String.format(ChatColor.GREEN + "Finalizado! %sms", System.currentTimeMillis() - start));
    }

    @Override
    public void onDisable() {
        long start = System.currentTimeMillis();
        log.info(ChatColor.RED + "Flw, foi bom jogar com você.");
        sqlManager.onDisable();
        log.info(String.format(ChatColor.RED + "Plugin encerrado em %sms", System.currentTimeMillis() - start));
    }
}

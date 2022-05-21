package me.sadev.dodge;

import lombok.Getter;
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
    public final Logger log = LoggerFactory.getLogger("Dodge");

    @Override
    public void onEnable() {
        long start = System.currentTimeMillis();
        log.info(ChatColor.GREEN + "Iniciando...");

        instance = this;
        saveDefaultConfig();

        sqlManager = new SQLManager(this);
        //userAccountController = new UserAccountController();
        //userAccountRepository = new UserAccountRepository();

        log.info(String.format(ChatColor.GREEN + "Finalizado! %sms", System.currentTimeMillis() - start));
    }

    @Override
    public void onDisable() {
        log.info("Desligando");
        // sqlManager.onDisable();
    }
}

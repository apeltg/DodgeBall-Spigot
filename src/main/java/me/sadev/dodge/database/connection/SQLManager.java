package me.sadev.dodge.database.connection;

import me.sadev.dodge.Dodge;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class SQLManager {

    private final Dodge plugin;
    private final ConnectionPoolManager pool;


    public SQLManager(Dodge plugin) {
        this.plugin = plugin;
        pool = new ConnectionPoolManager(plugin);
        makeTable();
    }

    private void makeTable() {
        try (Connection conn = pool.getConnection(); Statement ps = conn.createStatement()) {
            ps.addBatch("CREATE TABLE IF NOT EXISTS userAccount(" +
                    "userID MEDIUMINT NOT NULL AUTO_INCREMENT, " +
                    "userName varchar(30) NOT NULL, " +
                    "uuid VARCHAR(40) NOT NULL, " +
                    "createdAt CURRENT_TIMESTAMP NOT NULL, " +
                    "UNIQUE (uuid), " +
                    "PRIMARY KEY `id`(`id`))");
            ps.addBatch("CREATE TABLE IF NOT EXISTS userStatus(" +
                    "id INTEGER NOT NULL AUTO_INCREMENT, " +
                    "userID INTEGER NOT NULL, " +
                    "wins DOUBLE UNSIGNED NOT NULL, " +
                    "loses DOUBLE UNSIGNED NOT NULL, " +
                    "kills DOUBLE UNSIGNED NOT NULL, " +
                    "deaths DOUBLE UNSIGNED NOT NULL, " +
                    "CONSTRAINT fk_userID FOREIGN KEY (userID) REFERENCES userAccount(id), " +
                    "PRIMARY KEY `id`(`id`))");

            ps.executeBatch();
            plugin.getLogger().info("Table criada com sucesso");
        } catch (SQLException e) { plugin.getLog().error(e); }
    }

    public Connection getConnection() {
        try { return pool.getConnection();
        } catch (SQLException e) { plugin.getLog().error(e);}
        return null;
    }

    public void onDisable() {
        pool.closePool();
    }
}

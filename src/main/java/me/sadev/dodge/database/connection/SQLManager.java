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
            ps.addBatch("CREATE TABLE IF NOT EXISTS userAccount(userID MEDIUMINT NOT NULL AUTO_INCREMENT, userName varchar(30) NOT NULL, uuid VARCHAR(40) NOT NULL, createdAt TIMESTAMP NOT NULL, UNIQUE (uuid), PRIMARY KEY(userID));");
            ps.addBatch("CREATE TABLE IF NOT EXISTS userStatus(id MEDIUMINT NOT NULL AUTO_INCREMENT, userID MEDIUMINT NOT NULL, wins DOUBLE UNSIGNED NOT NULL, loses DOUBLE UNSIGNED NOT NULL, kills DOUBLE UNSIGNED NOT NULL, deaths DOUBLE UNSIGNED NOT NULL, FOREIGN KEY (userID) REFERENCES userAccount(userID), PRIMARY KEY (`id`))");

            ps.executeBatch();
            plugin.getLogger().info("Table criada com sucesso");
        } catch (SQLException e) { e.printStackTrace(); }
    }

    public Connection getConnection() {
        try { return pool.getConnection();
        } catch (SQLException e) { e.printStackTrace(); }
        return null;
    }

    public void onDisable() {
        try {getConnection().close();
        } catch (SQLException e) {throw new RuntimeException(e);}
        pool.closePool();
    }
}

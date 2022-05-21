package me.sadev.dodge.database.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.sadev.dodge.Dodge;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionPoolManager {


    private HikariDataSource dataSource;

    private final String hostname;
    private final String port;
    private final String database;
    private final String username;
    private final String password;

    private final int maximumConnections;

    public ConnectionPoolManager(Dodge plugin) {
        this.hostname = plugin.getConfig().getString("mysql.host");
        this.port = "3306";
        this.database = plugin.getConfig().getString("mysql.database");
        this.username = plugin.getConfig().getString("mysql.username");
        this.password = plugin.getConfig().getString("mysql.password");
        this.maximumConnections = 10;

        setupPool();
    }

    private void setupPool() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl("jdbc:mysql://" + hostname + ":" + port + "/" + database);
        config.setUsername(username);
        config.setPassword(password);

        config.setMaximumPoolSize(maximumConnections);
        config.addDataSourceProperty("cachePrepStmts", "true");
        config.addDataSourceProperty("prepStmtCacheSize", "250");
        config.addDataSourceProperty("prepStmtCacheSqlLimit", "2048");
        dataSource = new HikariDataSource(config);
    }

    public Connection getConnection() throws SQLException {
        return dataSource.getConnection();
    }

    public void close(Connection conn, PreparedStatement ps, ResultSet res) {
        if (conn != null) try {
            conn.close();
        } catch (SQLException ignored) {}
        if (ps != null) try {
            ps.close();
        } catch (SQLException ignored) {}
        if (res != null) try {
            res.close();
        } catch (SQLException ignored) {}
    }

    public void closePool() {
        if (dataSource != null && !dataSource.isClosed()) dataSource.close();
    }
}

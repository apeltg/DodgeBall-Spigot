package me.sadev.dodge.database.connection;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import me.sadev.dodge.Dodge;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ConnectionPoolManager {

    private final Dodge plugin;

    private HikariDataSource dataSource;

    private String hostname;
    private String port;
    private String database;
    private String username;
    private String password;

    private int maximumConnections;

    public ConnectionPoolManager(Dodge plugin) {
        this.plugin = plugin;
        init();
        setupPool();
    }

    private void init() {
        hostname = plugin.getConfig().getString("mysql.host");
        port = "3306";
        database = plugin.getConfig().getString("mysql.database");
        username = plugin.getConfig().getString("mysql.username");
        password = plugin.getConfig().getString("mysql.password");
        maximumConnections = 10;
    }

    private void setupPool() {
        HikariConfig config = new HikariConfig();
        config.setJdbcUrl(
                "jdbc:mysql://" + hostname + ":" + port + "/" + database
        );
        //config.setDataSourceClassName("org.mariadb.jdbc.MariaDbDataSource");
        config.setDriverClassName("com.mysql.jdbc.Driver");
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

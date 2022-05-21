package me.sadev.dodge.database.user;

import me.sadev.dodge.Dodge;
import me.sadev.dodge.database.models.UserAccount;
import me.sadev.dodge.database.models.UserStatus;

import java.sql.*;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public class UserAccountRepository {

    private final Connection connection = Dodge.getInstance().getSqlManager().getConnection();
    private final UserAccountController userAccountController = Dodge.getInstance().getUserAccountController();

    public CompletableFuture<Boolean> loadUserAccount(UUID userUUID) {
        return CompletableFuture.supplyAsync(() -> {
            boolean check = false;
            String loadUserSQL = "SELECT * FROM userAccount user, userStatus status WHERE user.uuid='" + userUUID.toString() + "' AND user.userID = status.userID";

            try (final PreparedStatement statement = connection.prepareStatement(loadUserSQL)) {
                try (final ResultSet resultSet = statement.executeQuery()) {
                    if (!resultSet.next()) return false;
                    check = true;
                    do {
                        int id = resultSet.getInt("id");
                        Timestamp createdAt = resultSet.getTimestamp("createdAt");
                        String name = resultSet.getString("username");
                        UserStatus status = new UserStatus(
                                resultSet.getDouble("status.wins"),
                                resultSet.getDouble("status.loses"),
                                resultSet.getDouble("status.deaths"),
                                resultSet.getDouble("status.kills"));
                        userAccountController.loadUser(new UserAccount(userUUID, id, createdAt, status, name));
                    } while (resultSet.next());
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return check;
        });
    }

    public void insertOrUpdateUserAccount(UserAccount userAccount) {
        String selectUserAccount = "SELECT userID FROM userAccount WHERE uuid='" + userAccount.getUuid() + "'";
        String insertData = "INSERT INTO userAccount(uuid, username,createdAt) value (?, ?, ?)";
        try (final PreparedStatement statement = connection.prepareStatement(selectUserAccount); ResultSet resultSet = statement.executeQuery()) {
            if (resultSet.next()) updateUserAccount(userAccount);
            try (PreparedStatement preparedStatement = connection.prepareStatement(insertData, Statement.RETURN_GENERATED_KEYS)) {
                preparedStatement.setString(1, userAccount.getUuid().toString());
                preparedStatement.setString(2, userAccount.getName());
                preparedStatement.setTimestamp(3, userAccount.getCreatedAt());
                preparedStatement.executeUpdate();

                try (ResultSet getID = preparedStatement.getGeneratedKeys()) {
                    if (getID.next()) {
                        int id = getID.getInt(1);
                        userAccount.setUserID(id);
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void updateUserAccount(UserAccount userAccount) {
        try (Statement ps = connection.createStatement()) {
            ps.addBatch("UPDATE userStatus SET wins = " + userAccount.getStatus().getWins() + " WHERE userID=" + userAccount.getUserID());
            ps.addBatch("UPDATE userStatus SET loses = " + userAccount.getStatus().getWins() + " WHERE userID=" + userAccount.getUserID());
            ps.addBatch("UPDATE userStatus SET kills = " + userAccount.getStatus().getWins() + " WHERE userID=" + userAccount.getUserID());
            ps.addBatch("UPDATE userStatus SET deaths = " + userAccount.getStatus().getWins() + " WHERE userID=" + userAccount.getUserID());

            ps.executeBatch();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}

package me.sadev.dodge.database.models;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
public class UserAccount {

    private UUID uuid;
    private int userID;
    private Timestamp createdAt;
    private UserStatus status;
    private String name;

    public double getKD() {
        return status.getKills() / status.getDeaths();
    }

    public double getWL() {
        return status.getWins() / status.getLoses();
    }
}

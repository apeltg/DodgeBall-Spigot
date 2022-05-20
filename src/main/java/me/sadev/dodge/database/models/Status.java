package me.sadev.dodge.database.models;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Status {

    private double wins;
    private double loses;
    private double deaths;
    private double kills;
}

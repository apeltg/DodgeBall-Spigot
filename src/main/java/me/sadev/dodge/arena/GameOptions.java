package me.sadev.dodge.arena;

import me.sadev.dodge.arena.enums.Modo;
import me.sadev.dodge.arena.enums.Tipo;
import org.bukkit.Location;

public record GameOptions(
        int maxPlayers,
        Modo modo,
        Tipo tipo,
        Location redTeamLoc,
        Location blueTeamLoc) {}


package me.sadev.dodge.arena;

import me.sadev.dodge.arena.enums.Team;
import org.bukkit.entity.Player;

public class ArenaPlayer {

    private final Player player;

    private Arena arena;
    private Status status;

    private Team time = Team.NENHUM;

    public ArenaPlayer(Arena arena, Player player) {
        this.arena = arena;
        this.status = new Status(player);
        this.player = player;
    }

    public Player player() {
        return player;
    }

    public Arena arena() {
        return arena;
    }

    public ArenaPlayer setArena(Arena arena) {
        this.arena = arena;
        return this;
    }

    public Status status() {
        return status;
    }

    public ArenaPlayer setStatus(Status status) {
        this.status = status;
        return this;
    }

    public Team time() {
        return time;
    }

    public ArenaPlayer setTime(Team time) {
        this.time = time;
        return this;
    }
}

package me.sadev.dodge.arena;

import lombok.Getter;
import lombok.Setter;
import me.sadev.dodge.arena.enums.Team;
import me.sadev.dodge.database.models.UserAccount;
import org.bukkit.entity.Player;

public class ArenaPlayer {

    @Getter
    private final Player player;
    @Getter
    private final UserAccount userAccount;

    private Arena arena;
    private Status status;

    private Team time = Team.NENHUM;

    public ArenaPlayer(UserAccount userAccount, Arena arena, Player player) {
        this.userAccount = userAccount;
        this.arena = arena;
        this.status = new Status(this);
        this.player = player;
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

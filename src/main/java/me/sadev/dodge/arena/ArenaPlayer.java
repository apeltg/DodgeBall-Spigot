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

    @Getter
    @Setter
    private Arena arena;
    @Getter
    @Setter
    private Status status;

    @Getter
    @Setter
    private Team time = Team.NENHUM;

    public ArenaPlayer(UserAccount userAccount, Arena arena, Player player) {
        this.userAccount = userAccount;
        this.arena = arena;
        this.status = new Status(this);
        this.player = player;
    }
}

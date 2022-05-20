package me.sadev.dodge.arena;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.entity.Player;

public class Status {

    @Getter
    private final Player player;

    @Getter
    @Setter
    private double kills;
    @Getter
    @Setter
    private double mortes;
    @Getter
    @Setter
    private double derrotas;
    @Getter
    @Setter
    private double vitorias;

    public Status(ArenaPlayer player) {
        this.player = player.getPlayer();
        kills = player.getUserAccount().getStatus().getKills();
        mortes = player.getUserAccount().getStatus().getDeaths();
        derrotas = player.getUserAccount().getStatus().getLoses();
        vitorias = player.getUserAccount().getStatus().getWins();
    }
}

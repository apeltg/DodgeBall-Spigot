package me.sadev.dodge.arena;

import org.bukkit.entity.Player;

public class Status {

    private final Player player;

    private int kills;
    private int abates;
    private int partidas;
    private int vitorias;

    public Status(Player player) {
        this.player = player;
        asyncGetStatus();
    }

    public Player player() {
        return player;
    }

    public int kills() {
        return kills;
    }

    public Status setKills(int kills) {
        this.kills = kills;
        return this;
    }

    public int abates() {
        return abates;
    }

    public Status setAbates(int abates) {
        this.abates = abates;
        return this;
    }

    public int partidas() {
        return partidas;
    }

    public Status setPartidas(int partidas) {
        this.partidas = partidas;
        return this;
    }

    public int vitorias() {
        return vitorias;
    }

    public Status setVitorias(int vitorias) {
        this.vitorias = vitorias;
        return this;
    }

    public void asyncGetStatus() {
        // TODO - Pega os status do database automaticamente e adiciona
    }
}

package me.sadev.dodge.arena.enums;

public enum GameStatus {

    Esperando("esperando"),
    Jogando("jogando"),
    Terminando("terminando");

    public final String name;

    public String toString() {
        return name;
    }

    GameStatus(String name) {
        this.name = name;
    }
}

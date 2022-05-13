package me.sadev.dodge.arena;

import me.sadev.dodge.Dodge;
import me.sadev.dodge.arena.enums.GameStatus;
import me.sadev.dodge.arena.events.ArenaChangeGameStatusEvent;
import me.sadev.dodge.arena.events.PlayerJoinArenaEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Arena {

    private static final Dodge plugin = JavaPlugin.getPlugin(Dodge.class);

    private final Set<ArenaPlayer> players = new HashSet<>();
    private final List<ArenaPlayer> spectators = new ArrayList<>();

    private final Location pos1;
    private final Location pos2;

    private final GameOptions options;
    private GameStatus gameStatus;

    public Arena(Location pos1, Location pos2, GameOptions options) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.options = options;
        this.gameStatus = GameStatus.Esperando;
    }

    public Set<ArenaPlayer> players() {
        return players;
    }

    public List<ArenaPlayer> spectators() {
        return spectators;
    }

    public Location pos1() {
        return pos1;
    }

    public Location pos2() {
        return pos2;
    }

    public GameOptions options() {
        return options;
    }

    public GameStatus gameStatus() {
        return gameStatus;
    }

    public Arena setGameStatus(GameStatus gameStatus) {
        // Chama o evento PlayerJoinArena
        ArenaChangeGameStatusEvent event = new ArenaChangeGameStatusEvent(this, gameStatus, gameStatus());
        Bukkit.getPluginManager().callEvent(event);

        // Verifica se o evento foi cancelado para teleportar o player
        if (event.isCancelled()) return this;

        this.gameStatus = gameStatus;
        return this;
    }

    public Location getTeamLocation(ArenaPlayer player) {
        switch (player.time()) {
            case RED -> {
                return options.redTeamLoc();
            }
            case BLUE -> {
                return options.blueTeamLoc();
            }
        }
        return null; // TODO - Retornar o Spawn
    }

    public void teleport(ArenaPlayer player) {
        // Chama o evento PlayerJoinArena
        PlayerJoinArenaEvent event = new PlayerJoinArenaEvent(player, this);
        Bukkit.getPluginManager().callEvent(event);

        // Verifica se o evento foi cancelado para teleportar o player
        if (event.isCancelled()) return;

        // Finalmente teleporta o jogador
        player.setArena(this);
        players.add(player);
        player.player().teleport(getTeamLocation(player));
    }

    public void remove(ArenaPlayer player) {
        players.remove(player);
        // TODO - Teleportar para o Spawn
    }
}

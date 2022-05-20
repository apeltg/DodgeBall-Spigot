package me.sadev.dodge.arena;

import lombok.Getter;
import lombok.Setter;
import me.sadev.dodge.Dodge;
import me.sadev.dodge.arena.enums.GameStatus;
import me.sadev.dodge.arena.enums.Team;
import me.sadev.dodge.arena.events.ArenaChangeGameStatusEvent;
import me.sadev.dodge.arena.events.PlayerJoinArenaEvent;
import me.sadev.dodge.arena.events.PlayerQuitArenaEvent;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Arena {

    private final Dodge plugin = JavaPlugin.getPlugin(Dodge.class);

    @Getter
    private final Set<ArenaPlayer> players = new HashSet<>();
    @Getter
    private final List<ArenaPlayer> spectators = new ArrayList<>();
    @Getter
    private final Location pos1;
    @Getter
    private final Location pos2;

    @Getter
    private final GameOptions options;
    @Getter
    @Setter
    private GameStatus gameStatus;

    public Arena(Location pos1, Location pos2, GameOptions options) {
        this.pos1 = pos1;
        this.pos2 = pos2;
        this.options = options;
        this.gameStatus = GameStatus.Esperando;
    }

    /**
    * Pega o {@link Team} do jogador
     */
    public Location getTeamLocation(ArenaPlayer player) {
        switch (player.getTime()) {
            case RED -> {
                return options.redTeamLoc();
            }
            case BLUE -> {
                return options.blueTeamLoc();
            }
        }
        return null; // TODO - Retornar o Spawn
    }

    /**
     * Teleporta e adiciona o {@link ArenaPlayer} à partida
     */
    public Arena teleport(ArenaPlayer player) {
        // Chama o evento PlayerJoinArena
        PlayerJoinArenaEvent event = new PlayerJoinArenaEvent(player, this);
        Bukkit.getPluginManager().callEvent(event);

        // Verifica se o evento foi cancelado para teleportar o player
        if (event.isCancelled()) return this;

        // Finalmente teleporta o jogador
        player.setArena(this);
        players.add(player);
        player.getPlayer().teleport(getTeamLocation(player));

        return this;
    }


    /**
     * Remove um determinado {@link ArenaPlayer} do partida.
     *
     * @param player - Jogador no qual sera removido.
     */
    public Arena remove(ArenaPlayer player) {
        PlayerQuitArenaEvent event = new PlayerQuitArenaEvent(player, this);
        Bukkit.getPluginManager().callEvent(event);

        // Verifica se o evento foi cancelado para teleportar o player
        if (event.isCancelled()) return this;

        players.remove(player);
        // TODO - Teleportar para o Spawn
        return this;
    }
}

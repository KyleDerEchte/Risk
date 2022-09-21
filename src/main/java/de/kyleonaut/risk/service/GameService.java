package de.kyleonaut.risk.service;

import de.kyleonaut.risk.RiskPlugin;
import de.kyleonaut.risk.model.Game;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.UUID;

public class GameService {
    private static GameService instance;
    private Game currentGame;

    public static GameService getInstance() {
        if (instance == null) {
            instance = new GameService();
        }
        return instance;
    }

    private GameService() {
        final RiskPlugin plugin = RiskPlugin.getPlugin(RiskPlugin.class);
        this.currentGame = new Game();
        this.currentGame.setUuid(UUID.randomUUID());
        this.currentGame.setParticipants(new ArrayList<>());
        this.currentGame.setState(Game.GameState.STARTING);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {

        }, 0, 40 * 20);
    }

    public Game.GameState getCurrentGameState() {
        return currentGame.getState();
    }


}

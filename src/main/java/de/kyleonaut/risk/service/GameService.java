package de.kyleonaut.risk.service;

import de.kyleonaut.risk.RiskPlugin;
import de.kyleonaut.risk.api.event.GameStartEvent;
import de.kyleonaut.risk.model.Game;
import de.kyleonaut.risk.model.wrapper.GameStateWrapper;
import de.kyleonaut.risk.util.BukkitTimeUnit;
import org.bukkit.Bukkit;

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

    }

    public void initializeGame() {
        final RiskPlugin plugin = RiskPlugin.getPlugin(RiskPlugin.class);
        this.currentGame = Game.create();
        startGameStateUpdater(plugin);
    }
    /*
    * Maybe move this to the game instance itself ?
    *
    * */
    private void startGameStateUpdater(RiskPlugin plugin) {
        final GameStateWrapper gameStateWrapper = new GameStateWrapper(this.getCurrentGameState(), 0);
        Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            gameStateWrapper.setLiving(gameStateWrapper.getLiving() + 1);

            if (this.currentGame.getState().equals(Game.GameState.RUNNING)) {
                return;
            }
            if (gameStateWrapper.getLiving() < BukkitTimeUnit.ofSeconds(10)) {
                return;
            }
            // TODO: 22.09.2022 read maximal game length from the config
            if (gameStateWrapper.getLiving() > BukkitTimeUnit.ofSeconds(10) && gameStateWrapper.getLiving() < BukkitTimeUnit.ofSeconds(20)) { //maximal game length
                final GameStartEvent gameStartEvent = new GameStartEvent(this.currentGame);
                Bukkit.getPluginManager().callEvent(gameStartEvent);
                if (gameStartEvent.isCancelled()) {
                    gameStateWrapper.setLiving(0);
                    return;
                }
                this.currentGame.setState(Game.GameState.RUNNING);
            }
        }, 0, 1);
    }

    public Game.GameState getCurrentGameState() {
        return currentGame.getState();
    }


}

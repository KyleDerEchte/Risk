package de.kyleonaut.risk.manager;

import de.kyleonaut.risk.RiskPlugin;
import de.kyleonaut.risk.holder.CurrentGameHolder;
import de.kyleonaut.risk.model.Game;
import de.kyleonaut.risk.model.State;
import de.kyleonaut.risk.util.BukkitTimeUnit;
import de.kyleonaut.risk.util.Constants;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

@RequiredArgsConstructor
public class GameLoopManager {
    private final RiskPlugin plugin;
    private final CurrentGameHolder currentGameHolder;
    private int taskId;

    public void start() {
        final AtomicLong tickCounter = new AtomicLong(0);
        this.taskId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            tickCounter.incrementAndGet();
            final Game game = currentGameHolder.copy();
            if (game.getState().equals(State.IDLE)) {
                //init game
                game.setUuid(UUID.randomUUID());
                game.setState(State.STARTING);
                game.setParticipants(new ArrayList<>());

                //reset ticks
                tickCounter.set(0);
                //update game
                this.currentGameHolder.update(game);
                return;
            }
            if (game.getState().equals(State.STARTING)) {
                if (tickCounter.get() >= BukkitTimeUnit.ofSeconds(Constants.STARTING_TIME_SECONDS)) {
                    //change state
                    game.setState(State.RUNNING);
                    //reset ticks
                    tickCounter.set(0);
                    //update game
                    this.currentGameHolder.update(game);
                    return;
                }
            }
            if (game.getState().equals(State.RUNNING)) {
                //check for 1 because the tickCounter is updated in the first place
                if (tickCounter.get() == 1) {
                    // TODO: 22.09.2022 init holo
                    return;
                }
                if (tickCounter.get() >= BukkitTimeUnit.ofSeconds(Constants.MAX_GAME_SECONDS)) {
                    //change state
                    game.setState(State.ENDING);
                    //reset ticks
                    tickCounter.set(0);
                    //update game
                    this.currentGameHolder.update(game);
                    return;
                }
            }
            if (game.getState().equals(State.ENDING)) {
                if (tickCounter.get() >= BukkitTimeUnit.ofSeconds(Constants.ENDING_TIME_SECONDS)) {
                    //change state
                    game.setState(State.IDLE);
                    //reset ticks
                    tickCounter.set(0);
                    //update game
                    this.currentGameHolder.update(game);
                }
            }
        }, 0, 1);
    }

    public void stop() {
        Bukkit.getScheduler().cancelTask(this.taskId);
    }

}

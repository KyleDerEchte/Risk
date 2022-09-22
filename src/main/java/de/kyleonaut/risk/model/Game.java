package de.kyleonaut.risk.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    private UUID uuid;
    private List<Participant> participants;
    private GameState state;

    public static Game create() {
        return new Game(UUID.randomUUID(), new ArrayList<>(), GameState.IDLE);
    }

    public enum GameState {
        IDLE,
        RUNNING,
        DONE
    }
}

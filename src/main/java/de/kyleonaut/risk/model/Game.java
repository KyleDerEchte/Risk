package de.kyleonaut.risk.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Game {
    private UUID uuid;
    private List<Participant> participants;
    private GameState state;

    public enum GameState {
        STARTING, //10 seconds
        RUNNING, //up to 10 seconds
        ENDING, //10 seconds
        ENDED // 1/20 second - just a state a game ended.
    }
}

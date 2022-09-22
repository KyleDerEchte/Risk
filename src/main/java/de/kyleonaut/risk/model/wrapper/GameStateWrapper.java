package de.kyleonaut.risk.model.wrapper;

import de.kyleonaut.risk.model.Game;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class GameStateWrapper {
    private Game.GameState state;
    private long living;
}

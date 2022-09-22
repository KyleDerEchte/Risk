package de.kyleonaut.risk.holder;

import de.kyleonaut.risk.model.Game;
import de.kyleonaut.risk.model.State;

import java.util.ArrayList;
import java.util.UUID;

public class CurrentGameHolder {
    private Game instance;

    public synchronized Game copy() {
        if (this.instance == null) {
            this.instance = new Game();
            this.instance.setState(State.IDLE);
            this.instance.setParticipants(new ArrayList<>());
            this.instance.setUuid(UUID.randomUUID());
        }
        return new Game(this.instance.getUuid(), this.instance.getParticipants(), this.instance.getState());
    }

    public synchronized void update(Game game) {
        this.instance = game;
    }
}

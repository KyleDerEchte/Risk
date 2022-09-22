package de.kyleonaut.risk;

import de.kyleonaut.risk.holder.CurrentGameHolder;
import de.kyleonaut.risk.manager.GameLoopManager;
import org.bukkit.plugin.java.JavaPlugin;

public class RiskPlugin extends JavaPlugin {
    private GameLoopManager gameLoopManager;

    @Override
    public void onEnable() {
        final CurrentGameHolder currentGameHolder = new CurrentGameHolder();
        this.gameLoopManager = new GameLoopManager(this, currentGameHolder);
        this.gameLoopManager.start();
    }

    @Override
    public void onDisable() {
        this.gameLoopManager.stop();
    }
}

package de.kyleonaut.risk;

import de.kyleonaut.risk.service.GameService;
import org.bukkit.plugin.java.JavaPlugin;

public class RiskPlugin extends JavaPlugin {

    @Override
    public void onEnable() {

    }

    @Override
    public void onDisable() {
        GameService.getInstance().initializeGame();
    }
}

package de.kyleonaut.risk;

import com.google.inject.Guice;
import com.google.inject.Injector;
import de.kyleonaut.risk.area.listener.Area3DCreateListener;
import de.kyleonaut.risk.area.repository.Area3DRepository;
import de.kyleonaut.risk.area.task.Area3DVisualizer;
import de.kyleonaut.risk.module.RiskModule;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public class RiskPlugin extends JavaPlugin {
    @Getter
    private Injector injector;
    private int visualizerTask;

    @Override
    public void onEnable() {
        RiskModule riskModule = new RiskModule(this);
        this.injector = Guice.createInjector(riskModule);
        this.injector.injectMembers(this);

        this.injector.getInstance(Area3DRepository.class).load();

        Bukkit.getPluginManager().registerEvents(this.injector.getInstance(Area3DCreateListener.class), this);

        this.visualizerTask = Bukkit.getScheduler().scheduleSyncRepeatingTask(this,
                this.injector.getInstance(Area3DVisualizer.class), 0, 5);
    }

    @Override
    public void onDisable() {
        Bukkit.getScheduler().cancelTask(this.visualizerTask);
    }
}

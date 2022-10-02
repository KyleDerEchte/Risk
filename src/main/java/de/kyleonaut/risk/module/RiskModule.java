package de.kyleonaut.risk.module;

import com.google.inject.Binder;
import com.google.inject.Module;
import de.kyleonaut.risk.RiskPlugin;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class RiskModule implements Module {
    private final RiskPlugin plugin;

    @Override
    public void configure(Binder binder) {
        binder.bind(RiskPlugin.class).toInstance(plugin);
    }
}

package de.kyleonaut.risk.area.task;

import de.kyleonaut.risk.RiskPlugin;
import de.kyleonaut.risk.area.holder.Area3DHolder;
import de.kyleonaut.risk.area.model.Area3D;
import de.kyleonaut.risk.area.model.Coordinate;
import lombok.RequiredArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Particle;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor(onConstructor_ = @Inject)
public class Area3DVisualizer implements Runnable {
    private final Area3DHolder area3DHolder;
    private final RiskPlugin plugin;

    @Override
    public void run() {
        final List<Player> players = new ArrayList<>(Bukkit.getOnlinePlayers());
        for (Player player : players) {
            Coordinate coordinate = Coordinate.of(player.getLocation());
            synchronized (area3DHolder) {
                final Area3D area3D = area3DHolder.getArea3D();
                if (area3D == null) {
                    return;
                }
                if (!area3D.isInside(coordinate)) {
                    return;
                }
                if (!player.hasPermission("region.create_area")) {
                    return;
                }
                List<Coordinate> outlines = area3D.getOutlines();
                Bukkit.getScheduler().runTask(plugin, () -> {
                    for (Coordinate outline : outlines) {
                        outline.toBukkitLocation().getWorld().spawnParticle(Particle.REDSTONE, outline.toBukkitLocation(), 1, new Particle.DustOptions(Color.RED, 1));
                    }
                });
            }

        }
    }
}

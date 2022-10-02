package de.kyleonaut.risk.area.listener;

import de.kyleonaut.risk.area.model.Area3D;
import de.kyleonaut.risk.area.model.Coordinate;
import de.kyleonaut.risk.area.repository.Area3DRepository;
import lombok.RequiredArgsConstructor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import javax.inject.Inject;
import javax.inject.Singleton;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Singleton
@RequiredArgsConstructor(onConstructor_ = @Inject)
public class Area3DCreateListener implements Listener {
    private final Area3DRepository area3DRepository;
    private final Map<UUID, Coordinate> coords = new HashMap<>();

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        final Player player = event.getPlayer();
        if (!event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            return;
        }
        if (!player.hasPermission("risk.create_area")) {
            return;
        }
        if (event.getClickedBlock() == null) {
            return;
        }
        if (event.getItem() == null) {
            return;
        }
        if (!event.getItem().getType().equals(Material.PUFFERFISH)) {
            return;
        }
        event.setCancelled(true);
        if (!this.coords.containsKey(player.getUniqueId())) {
            this.coords.put(player.getUniqueId(), Coordinate.of(event.getClickedBlock().getLocation()));
            player.sendMessage("§8[§6Risk§8] §7You've declared the first coordinate.");
            return;
        }
        final Coordinate first = this.coords.get(player.getUniqueId());
        final Coordinate second = Coordinate.of(event.getClickedBlock().getLocation());
        player.sendMessage("§8[§6Risk§8] §7You've declared the second coordinate.");
        this.coords.remove(player.getUniqueId());
        final Area3D area3D = Area3D.ofCoordinate(first, second);
        area3DRepository.save(area3D);
        player.sendMessage("§8[§6Risk§8] §7You've updated the game area.");
    }
}

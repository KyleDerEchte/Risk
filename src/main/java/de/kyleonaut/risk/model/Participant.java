package de.kyleonaut.risk.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Optional;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Participant {
    private UUID uuid;
    private String name;
    private int amount;

    public Optional<Player> toPlayer() {
        return Optional.ofNullable(Bukkit.getPlayer(this.uuid));
    }

    public static Participant ofPlayer(Player player, int amount) {
        return new Participant(player.getUniqueId(), player.getName(), amount);
    }
}

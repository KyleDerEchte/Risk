package de.kyleonaut.risk.area.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.Objects;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Coordinate {
    private int x;
    private int y;
    private int z;
    private String worldName;

    public static Coordinate of(Location location) {
        return new Coordinate(location.getBlockX(), location.getBlockY(), location.getBlockZ(), Objects.requireNonNull(location.getWorld()).getName());
    }

    public Location toBukkitLocation() {
        return new Location(Bukkit.getWorld(this.worldName), x, y, z);
    }

    @Override
    public String toString() {
        return "Coordinate{" +
                "x=" + x +
                ", y=" + y +
                ", z=" + z +
                ", worldName='" + worldName + '\'' +
                '}';
    }
}
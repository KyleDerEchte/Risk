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
    private double x;
    private double y;
    private double z;
    private String worldName;

    public static Coordinate of(Location location) {
        return new Coordinate(location.getX() + 0.5, location.getY() + 0.5, location.getZ() + 0.5, Objects.requireNonNull(location.getWorld()).getName());
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
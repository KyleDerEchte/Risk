package de.kyleonaut.risk.area.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
public class Area3D implements Serializable {
    private double minX;
    private double minY;
    private double minZ;

    private double maxX;
    private double maxY;
    private double maxZ;

    private String world;

    public static Area3D ofCoordinate(Coordinate first, Coordinate second) {
        if (!Objects.equals(first.getWorldName(), second.getWorldName())) {
            throw new RuntimeException("Cannot create an area with two coordinates in different worlds");
        }
        final Area3D area3D = new Area3D();
        area3D.setMinX(Math.min(first.getX(), second.getX()));
        area3D.setMinY(Math.min(first.getY(), second.getY()));
        area3D.setMinZ(Math.min(first.getZ(), second.getZ()));
        area3D.setMaxX(Math.max(first.getX(), second.getX()));
        area3D.setMaxY(Math.max(first.getY(), second.getY()));
        area3D.setMaxZ(Math.max(first.getZ(), second.getZ()));
        area3D.setWorld(first.getWorldName());
        return area3D;
    }

    public List<Coordinate> getOutlines() {
        final List<Coordinate> coordinates = new ArrayList<>();

        for (double x = this.minX; x < this.maxX; x+=0.5) {
            coordinates.add(new Coordinate(x, this.minY, this.minZ, this.world));
            coordinates.add(new Coordinate(x, this.maxY, this.minZ, this.world));
            coordinates.add(new Coordinate(x, this.minY, this.maxZ, this.world));
            coordinates.add(new Coordinate(x, this.maxY, this.maxZ, this.world));
        }
        for (double y = this.minY; y < this.maxY; y+=0.5) {
            coordinates.add(new Coordinate(this.minX, y, this.minZ, this.world));
            coordinates.add(new Coordinate(this.maxX, y, this.minZ, this.world));
            coordinates.add(new Coordinate(this.minX, y, this.maxZ, this.world));
            coordinates.add(new Coordinate(this.maxX, y, this.maxZ, this.world));
        }
        for (double z = this.minZ; z < this.maxZ; z+=0.5) {
            coordinates.add(new Coordinate(this.minX, this.minY, z, this.world));
            coordinates.add(new Coordinate(this.maxX, this.minY, z, this.world));
            coordinates.add(new Coordinate(this.minX, this.maxY, z, this.world));
            coordinates.add(new Coordinate(this.maxX, this.maxY, z, this.world));
        }

        return coordinates;
    }

    public boolean isInside(Coordinate coordinate) {
        return (minX <= coordinate.getX()
                && minY <= coordinate.getY()
                && minZ <= coordinate.getZ()
                && maxX >= coordinate.getX()
                && maxY >= coordinate.getY()
                && maxZ >= coordinate.getZ());
    }
}

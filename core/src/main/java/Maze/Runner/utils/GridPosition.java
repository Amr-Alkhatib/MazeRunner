package com.mazegame.utils;

import Maze.Runner.utils.Direction;

import java.util.Objects;

/**
 * Immutable integer grid coordinate used for maze cells.
 */
public final class GridPosition {

    public final int x;
    public final int y;

    public GridPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public GridPosition add(Direction dir) {
        return new GridPosition(x + (int) dir.getVector().x, y + (int) dir.getVector().y);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof GridPosition)) return false;
        GridPosition that = (GridPosition) o;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    @Override
    public String toString() {
        return "(" + x + "," + y + ")";
    }
}

package Maze.Runner.utils;

import com.badlogic.gdx.math.Vector2;

/**
 * 4‑way movement directions on the grid.
 */
public enum Direction {
    UP(0, 1),
    DOWN(0, -1),
    LEFT(-1, 0),
    RIGHT(1, 0),
    NONE(0, 0);

    private final Vector2 vector;

    Direction(int x, int y) {
        this.vector = new Vector2(x, y);
    }

    public Vector2 getVector() {
        return vector;
    }
}

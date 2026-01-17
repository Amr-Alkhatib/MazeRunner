package Maze.Runner.world;

import Maze.Runner.gameobjects.GameObject;
import Maze.Runner.gameobjects.Wall;
import com.badlogic.gdx.utils.Array;


/**
 * Holds the grid of tiles and objects.
 */
public class Maze {

    private final int width;
    private final int height;
    private final GameObject[][] grid;

    public Maze(int width, int height) {
        this.width = width;
        this.height = height;
        this.grid = new GameObject[width][height];
    }

    public void set(int x, int y, GameObject obj) {
        grid[x][y] = obj;
    }

    public GameObject get(int x, int y) {
        if (!isInside(x, y)) return null;
        return grid[x][y];
    }

    public boolean isWall(int x, int y) {
        return get(x, y) instanceof Wall;
    }

    public boolean isInside(int x, int y) {
        return x >= 0 && y >= 0 && x < width && y < height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public Iterable<GameObject> objects() {
        Array<GameObject> list = new Array<>();
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                if (grid[x][y] != null) list.add(grid[x][y]);
            }
        }
        return list;
    }
}

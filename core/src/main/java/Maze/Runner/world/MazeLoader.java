package Maze.Runner.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import Maze.Runner.gameobjects.*;
import Maze.Runner.utils.Constants;
import com.mazegame.utils.GridPosition;

import java.io.IOException;
import java.util.HashSet;
import java.util.Properties;
import java.util.Set;
import java.util.Stack;

/**
 * Loads maze layout from Java .properties file.
 * Validates that exit is reachable from entry using DFS.
 */
public class MazeLoader {

    public static class MazeLoadResult {
        public Maze maze;
        public Entry entry;
        public Exit exit;
    }

    public MazeLoadResult load(String mapFilePath) {
        FileHandle file = Gdx.files.internal(mapFilePath);
        if (!file.exists()) {
            throw new IllegalArgumentException("Map file not found: " + mapFilePath);
        }

        Properties props = new Properties();
        try {
            props.load(file.read());
        } catch (IOException e) {
            throw new RuntimeException("Failed to load map properties", e);
        }

        int maxX = 0;
        int maxY = 0;
        for (Object keyObj : props.keySet()) {
            String key = (String) keyObj;
            String[] parts = key.split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            if (x > maxX) maxX = x;
            if (y > maxY) maxY = y;
        }

        Maze maze = new Maze(maxX + 1, maxY + 1);

        // Load textures from correct paths
        Texture wallTex = loadTexture("textures/obstacles/trap_spike.png");
        Texture entryTex = loadTexture("textures/characters/line_green.png");
        Texture exitTex = loadTexture("textures/obstacles/laser_grid.png");
        Texture trapTex = loadTexture("textures/obstacles/trap_spike.png");
        Texture patrolEnemyTex = loadTexture("textures/obstacles/enemy_patrol.png");
        Texture kamikazeTex = loadTexture("textures/obstacles/enemy_kamikaze.png");
        Texture keyTex = loadTexture("textures/items/key.png");
        Texture heartTex = loadTexture("textures/items/heart.png");

        Entry entry = null;
        Exit exit = null;

        for (Object keyObj : props.keySet()) {
            String key = (String) keyObj;
            String value = props.getProperty(key);

            String[] parts = key.split(",");
            int x = Integer.parseInt(parts[0]);
            int y = Integer.parseInt(parts[1]);
            int type = Integer.parseInt(value);

            GameObject obj = null;

            switch (type) {
                case 0:  // Wall
                    obj = new Wall(x, y, wallTex);
                    break;
                case 1:  // Entry
                    entry = new Entry(x, y, entryTex);
                    obj = entry;
                    break;
                case 2:  // Exit
                    exit = new Exit(x, y, exitTex);
                    obj = exit;
                    break;
                case 3:  // Trap
                    obj = new Trap(x, y, trapTex);
                    break;
                case 4:  // Enemy
                    obj = new PatrolEnemy(x, y, patrolEnemyTex);
                    break;
                case 5:  // Key
                    obj = new Key(x, y, keyTex);
                    break;
                case 6:  // Heart
                    obj = new HeartPickup(x, y, heartTex);
                    break;
                case 7:  // Laser Grid
                    LaserGrid.FireDirection dir = (x + y) % 2 == 0 ?
                        LaserGrid.FireDirection.HORIZONTAL : LaserGrid.FireDirection.VERTICAL;
                    obj = new LaserGrid(x, y, exitTex, dir);
                    break;
                default:
                    break;
            }

            if (obj != null) {
                maze.set(x, y, obj);
            }
        }

        // Validate exit is reachable
        validateMazeReachability(maze, entry, exit);

        MazeLoadResult result = new MazeLoadResult();
        result.maze = maze;
        result.entry = entry;
        result.exit = exit;
        return result;
    }

    /**
     * Load texture with fallback. If file doesn't exist, creates placeholder.
     */
    private Texture loadTexture(String path) {
        FileHandle file = Gdx.files.internal(path);
        if (file.exists()) {
            return new Texture(file);
        } else {
            Gdx.app.log("WARN", "Texture not found: " + path + " - using placeholder");
            return createPlaceholderTexture();
        }
    }

    /**
     * Create a simple placeholder texture.
     */
    private Texture createPlaceholderTexture() {
        com.badlogic.gdx.graphics.Pixmap pixmap = new com.badlogic.gdx.graphics.Pixmap(32, 32, com.badlogic.gdx.graphics.Pixmap.Format.RGBA8888);
        pixmap.setColor(0.5f, 0.5f, 0.5f, 1);
        pixmap.fill();
        Texture texture = new Texture(pixmap);
        pixmap.dispose();
        return texture;
    }

    /**
     * Use DFS to validate that exit is reachable from entry.
     */
    private void validateMazeReachability(Maze maze, Entry entry, Exit exit) {
        if (entry == null || exit == null) {
            throw new RuntimeException("Entry or Exit is null");
        }

        GridPosition entryPos = new GridPosition((int)entry.getPosition().x, (int)entry.getPosition().y);
        GridPosition exitPos = new GridPosition((int)exit.getPosition().x, (int)exit.getPosition().y);

        Set<GridPosition> visited = new HashSet<>();
        Stack<GridPosition> stack = new Stack<>();
        stack.push(entryPos);

        while (!stack.isEmpty()) {
            GridPosition pos = stack.pop();
            if (visited.contains(pos)) continue;
            visited.add(pos);

            if (pos.equals(exitPos)) {
                Gdx.app.log("MAZE", "Exit is reachable from entry - maze is valid");
                return;
            }

            int[][] directions = {{0,1}, {0,-1}, {1,0}, {-1,0}};
            for (int[] dir : directions) {
                int nextX = pos.x + dir[0];
                int nextY = pos.y + dir[1];
                GridPosition next = new GridPosition(nextX, nextY);

                if (!visited.contains(next) &&
                    maze.isInside(nextX, nextY) &&
                    !maze.isWall(nextX, nextY)) {
                    stack.push(next);
                }
            }
        }

        throw new RuntimeException("Exit unreachable from entry - invalid maze!");
    }
}

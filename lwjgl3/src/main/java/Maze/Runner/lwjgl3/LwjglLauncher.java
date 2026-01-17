package Maze.Runner.lwjgl3;

import Maze.Runner.MazeGame;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

/**
 * Launcher for Desktop (Lwjgl3)
 * Configures and starts the Maze Runner game
 */
public class LwjglLauncher {
    public static void main(String[] args) {
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();

        // ==================== WINDOW CONFIGURATION ====================
        config.setTitle("Maze Runner");

        // Window size (responsive - will adapt if user resizes)
        config.setWindowedMode(2800, 1800);

        // Allow window resizing
        config.setWindowSizeLimits(800, 600, -1, -1);  // Min: 800x600, Max: unlimited

        // ==================== DISPLAY SETTINGS ====================
        // V-Sync (smooth 60 FPS)
        config.useVsync(true);
        config.setForegroundFPS(60);

        // ==================== START GAME ====================
        new Lwjgl3Application(new MazeGame(), config);
    }
}

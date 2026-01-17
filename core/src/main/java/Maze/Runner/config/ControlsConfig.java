package Maze.Runner.config;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.utils.Json;

/**
 * Stores key bindings and persists them as JSON.
 */
public class ControlsConfig {

    public int keyUp = Input.Keys.UP;
    public int keyDown = Input.Keys.DOWN;
    public int keyLeft = Input.Keys.LEFT;
    public int keyRight = Input.Keys.RIGHT;
    public int keyRun = Input.Keys.SHIFT_LEFT;
    public int keyPause = Input.Keys.ESCAPE;
    public int keyConsole = Input.Keys.F1;

    public static ControlsConfig load() {
        FileHandle file = Gdx.files.local(Config.CONTROLS_CONFIG_FILE);
        Json json = new Json();
        if (file.exists()) {
            try {
                return json.fromJson(ControlsConfig.class, file.readString());
            } catch (Exception e) {
                // fall back to defaults
                return new ControlsConfig();
            }
        }
        return new ControlsConfig();
    }

    public void save() {
        FileHandle file = Gdx.files.local(Config.CONTROLS_CONFIG_FILE);
        file.parent().mkdirs();
        Json json = new Json();
        file.writeString(json.prettyPrint(this), false);
    }
}

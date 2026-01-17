package Maze.Runner.gameobjects;

import com.badlogic.gdx.graphics.Texture;

/**
 * Non‑traversable wall tile.
 */
public class Wall extends StaticGameObject {
    public Wall(float x, float y, Texture texture) {
        super(x, y, texture);
    }
}

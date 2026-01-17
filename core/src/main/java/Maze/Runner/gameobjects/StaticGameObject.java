package Maze.Runner.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import Maze.Runner.utils.Constants;

/**
 * Simple stationary object; no special logic.
 */
public abstract class StaticGameObject extends GameObject {

    public StaticGameObject(float x, float y, Texture texture) {
        super(x, y, texture, Constants.TILE_SIZE, Constants.TILE_SIZE);
    }

    public void render(SpriteBatch batch) {
    }
}

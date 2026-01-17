package Maze.Runner.gameobjects;

import com.badlogic.gdx.graphics.Texture;

/**
 * Base class for collectable power‑ups.
 */
public abstract class PowerUp extends StaticGameObject {

    public PowerUp(float x, float y, Texture texture) {
        super(x, y, texture);
    }

    /**
     * Apply this power‑up to the player, then usually deactivate.
     */
    public abstract void apply(Player player);
}

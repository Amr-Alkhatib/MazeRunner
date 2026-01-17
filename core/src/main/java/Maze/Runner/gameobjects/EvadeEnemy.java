package Maze.Runner.gameobjects;

import com.badlogic.gdx.graphics.Texture;

/**
 * Enemy that prefers to keep distance from the player (logic later).
 */
public class EvadeEnemy extends Enemy {

    public EvadeEnemy(float x, float y, Texture texture) {
        super(x, y, texture);
    }

    @Override
    public void update(float deltaTime) {
        // Evade logic will be implemented later.
    }
}

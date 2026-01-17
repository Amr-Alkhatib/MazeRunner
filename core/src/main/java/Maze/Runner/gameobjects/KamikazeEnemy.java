package Maze.Runner.gameobjects;

import Maze.Runner.gameobjects.Enemy;
import com.badlogic.gdx.graphics.Texture;

/**
 * Enemy intended to chase the player using pathfinding (logic added later).
 */
public class KamikazeEnemy extends Enemy {

    public KamikazeEnemy(float x, float y, Texture texture) {
        super(x, y, texture);
    }

    @Override
    public void update(float deltaTime) {
        // Pathfinding towards player will be implemented later in GameScreen context.
    }
}

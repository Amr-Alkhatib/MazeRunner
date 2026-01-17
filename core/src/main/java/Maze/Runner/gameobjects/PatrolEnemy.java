package Maze.Runner.gameobjects;

import Maze.Runner.utils.Direction;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;


/**
 * Enemy that walks back and forth in a simple pattern.
 */
public class PatrolEnemy extends Enemy {

    private Direction currentDir = Direction.LEFT;
    private float moveTimer = 0f;
    private static final float STEP_INTERVAL = 0.4f;

    public PatrolEnemy(float x, float y, Texture texture) {
        super(x, y, texture);
    }

    @Override
    public void update(float deltaTime) {
        moveTimer += deltaTime;
        if (moveTimer >= STEP_INTERVAL) {
            moveTimer = 0f;
            Vector2 step = currentDir.getVector();
            position.add(step);
        }
    }
}

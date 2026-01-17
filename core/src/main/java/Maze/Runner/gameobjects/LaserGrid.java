package Maze.Runner.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import Maze.Runner.utils.Constants;

/**
 * Creative laser grid obstacle that periodically fires beams.
 * Fires along row or column, damaging player if in the line.
 */
public class LaserGrid extends StaticGameObject {

    private float cooldown = 2.0f;
    private float timer = 0f;
    private boolean firing = false;
    private static final float FIRE_DURATION = 0.4f;
    private FireDirection fireDir;

    public enum FireDirection {
        HORIZONTAL,
        VERTICAL
    }

    public LaserGrid(float x, float y, Texture texture, FireDirection direction) {
        super(x, y, texture);
        this.fireDir = direction;
    }

    @Override
    public void update(float deltaTime) {
        timer += deltaTime;

        if (!firing && timer >= cooldown) {
            firing = true;
            timer = 0f;
        } else if (firing && timer >= FIRE_DURATION) {
            firing = false;
            timer = 0f;
        }
    }

    @Override
    public void render(SpriteBatch batch) {
        super.render(batch);
        if (firing) {
            // TODO: draw laser beam visual when firing
        }
    }

    public boolean isFiring() {
        return firing;
    }

    public FireDirection getFireDirection() {
        return fireDir;
    }

    public float getWorldX() {
        return position.x * Constants.TILE_SIZE;
    }

    public float getWorldY() {
        return position.y * Constants.TILE_SIZE;
    }
}

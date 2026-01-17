package Maze.Runner.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import Maze.Runner.utils.Constants;

/**
 * Base class for all visible entities in the maze.
 */
public abstract class GameObject {

    protected Vector2 position;   // grid position (in tiles)
    protected Sprite sprite;
    protected boolean active = true;

    public GameObject(float x, float y, Texture texture, float width, float height) {
        this.position = new Vector2(x, y);
        if (texture != null) {
            this.sprite = new Sprite(texture);
            this.sprite.setBounds(
                x * Constants.TILE_SIZE,
                y * Constants.TILE_SIZE,
                width,
                height
            );
        }
    }

    public void update(float deltaTime) {
        // default no-op
    }

    public void render(SpriteBatch batch) {
        if (!active) return;
        if (sprite != null) {
            sprite.setPosition(position.x * Constants.TILE_SIZE, position.y * Constants.TILE_SIZE);
            sprite.draw(batch);
        }
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(float x, float y) {
        position.set(x, y);
    }

    public boolean isActive() {
        return active;
    }

    public void deactivate() {
        this.active = false;
    }
}

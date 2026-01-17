package Maze.Runner.gameobjects;

import com.badlogic.gdx.graphics.Texture;

/**
 * Collectable that grants an extra life.
 */
public class HeartPickup extends StaticGameObject {
    public HeartPickup(float x, float y, Texture texture) {
        super(x, y, texture);
    }
}

package Maze.Runner.gameobjects;

import com.badlogic.gdx.graphics.Texture;

/**
 * Static obstacle that damages the player on contact.
 */
public class Trap extends StaticGameObject {
    public Trap(float x, float y, Texture texture) {
        super(x, y, texture);
    }
}

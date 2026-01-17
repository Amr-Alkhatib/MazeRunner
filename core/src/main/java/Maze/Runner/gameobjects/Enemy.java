package Maze.Runner.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import Maze.Runner.utils.Constants;

/**
 * Base class for enemies with simple state.
 */
public abstract class Enemy extends GameObject {

    public enum State {
        PATROL,
        ATTACK,
        EVADE,
        RETREAT
    }

    protected State state = State.PATROL;

    public Enemy(float x, float y, Texture texture) {
        super(x, y, texture, Constants.TILE_SIZE, Constants.TILE_SIZE);
    }

    public State getState() {
        return state;
    }

    protected void moveBy(Vector2 delta) {
        position.add(delta);
    }

    /**
     * Called when colliding with the player, to apply damage etc.
     */
    public void onHitPlayer(Player player) {
        player.takeDamage();
    }
}

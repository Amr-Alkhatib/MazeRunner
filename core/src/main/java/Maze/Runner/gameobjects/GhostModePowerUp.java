package Maze.Runner.gameobjects;

import com.badlogic.gdx.graphics.Texture;

/**
 * Allows the player to pass through enemies for a duration.
 */
public class GhostModePowerUp extends PowerUp {

    private final float duration;

    public GhostModePowerUp(float x, float y, Texture texture, float duration) {
        super(x, y, texture);
        this.duration = duration;
    }

    @Override
    public void apply(Player player) {
        player.activateGhostMode(duration);
        deactivate();
    }
}

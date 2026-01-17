package Maze.Runner.gameobjects;

import com.badlogic.gdx.graphics.Texture;

/**
 * Increases player movement speed for a duration.
 */
public class SpeedBoostPowerUp extends PowerUp {

    private final float duration;

    public SpeedBoostPowerUp(float x, float y, Texture texture, float duration) {
        super(x, y, texture);
        this.duration = duration;
    }

    @Override
    public void apply(Player player) {
        player.activateSpeedBoost(duration);
        deactivate();
    }
}

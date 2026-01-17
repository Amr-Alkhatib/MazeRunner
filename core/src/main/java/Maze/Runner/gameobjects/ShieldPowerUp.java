package Maze.Runner.gameobjects;

import com.badlogic.gdx.graphics.Texture;

/**
 * Grants a shield that absorbs one hit or lasts for a duration.
 */
public class ShieldPowerUp extends PowerUp {

    private final float duration;

    public ShieldPowerUp(float x, float y, Texture texture, float duration) {
        super(x, y, texture);
        this.duration = duration;
    }

    @Override
    public void apply(Player player) {
        player.activateShield(duration);
        deactivate();
    }
}

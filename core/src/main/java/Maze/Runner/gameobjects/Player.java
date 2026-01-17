package Maze.Runner.gameobjects;

import Maze.Runner.utils.Direction;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import Maze.Runner.utils.Constants;
/**
 * Inventory system for the player.
 * Tracks collected items: keys, power-ups, etc.
 *
 * @author Team
 * @version 1.0
 */
class Inventory {
    private int keyCount = 0;
    private boolean shieldActive = false;
    private boolean speedBoostActive = false;
    private boolean ghostModeActive = false;

    public void addKey() {
        keyCount++;
    }

    public boolean hasKey() {
        return keyCount > 0;
    }

    public void removeKey() {
        if (keyCount > 0) keyCount--;
    }

    public int getKeyCount() {
        return keyCount;
    }

    public void activateShield(float duration) {
        shieldActive = true;
    }

    public void deactivateShield() {
        shieldActive = false;
    }

    public boolean hasShield() {
        return shieldActive;
    }

    public void activateSpeedBoost(float duration) {
        speedBoostActive = true;
    }

    public void deactivateSpeedBoost() {
        speedBoostActive = false;
    }

    public boolean hasSpeedBoost() {
        return speedBoostActive;
    }

    public void activateGhostMode(float duration) {
        ghostModeActive = true;
    }

    public void deactivateGhostMode() {
        ghostModeActive = false;
    }

    public boolean hasGhostMode() {
        return ghostModeActive;
    }
}

/**
 * Player character - main controllable entity.
 *
 * Features:
 * - Grid-based movement in 4 directions
 * - Running with stamina system
 * - Lives and health
 * - Inventory (keys, power-ups)
 * - Damage response with VFX
 * - Power-up tracking
 *
 * @author Team
 * @version 1.0
 */
public class Player extends GameObject {

    private Inventory inventory;
    private int lives;
    private int maxLives;
    private Direction facingDirection;
    private Direction moveDirection;
    private float stamina;
    private boolean isRunning;
    private float damageFlashTimer;
    private float invulnerabilityTimer;
    private int score;
    private int experience;

    // Power-up timers
    private float shieldTimer;
    private float speedBoostTimer;
    private float ghostModeTimer;

    /**
     * Create a new player character.
     *
     * @param x Starting X coordinate (grid)
     * @param y Starting Y coordinate (grid)
     * @param texture Player texture
     */
    public Player(float x, float y, Texture texture) {
        super(x, y, texture, Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
        this.inventory = new Inventory();
        this.lives = Constants.STARTING_LIVES;
        this.maxLives = Constants.MAX_LIVES;
        this.facingDirection = Direction.RIGHT;
        this.moveDirection = Direction.NONE;
        this.stamina = 100f;
        this.isRunning = false;
        this.damageFlashTimer = 0f;
        this.invulnerabilityTimer = 0f;
        this.shieldTimer = 0f;
        this.speedBoostTimer = 0f;
        this.ghostModeTimer = 0f;
        this.score = 0;
        this.experience = 0;
    }

    /**
     * Update player state each frame.
     *
     * @param deltaTime Time elapsed since last frame
     */
    @Override
    public void update(float deltaTime) {
        // Update timers
        damageFlashTimer = Math.max(0, damageFlashTimer - deltaTime);
        invulnerabilityTimer = Math.max(0, invulnerabilityTimer - deltaTime);

        // Update power-up timers
        shieldTimer = Math.max(0, shieldTimer - deltaTime);
        speedBoostTimer = Math.max(0, speedBoostTimer - deltaTime);
        ghostModeTimer = Math.max(0, ghostModeTimer - deltaTime);

        // Deactivate expired power-ups
        if (shieldTimer <= 0) {
            inventory.deactivateShield();
        }
        if (speedBoostTimer <= 0) {
            inventory.deactivateSpeedBoost();
        }
        if (ghostModeTimer <= 0) {
            inventory.deactivateGhostMode();
        }

        // Regenerate stamina when not running
        if (!isRunning && stamina < 100f) {
            stamina = Math.min(100f, stamina + Constants.STAMINA_REGEN * deltaTime);
        }

        // Update sprite position and appearance
        if (sprite != null) {
            sprite.setPosition(position.x * Constants.TILE_SIZE, position.y * Constants.TILE_SIZE);

            // Apply damage flash effect (turn red briefly)
            if (damageFlashTimer > 0) {
                sprite.setColor(1, 0.5f, 0.5f, 1);
            } else if (ghostModeTimer > 0) {
                // Ghost mode: semi-transparent
                sprite.setColor(1, 1, 1, 0.6f);
            } else {
                sprite.setColor(1, 1, 1, 1);
            }
        }
    }

    /**
     * Move the player in the specified direction.
     * Handles running with stamina consumption.
     *
     * @param direction Direction to move
     * @param deltaTime Time elapsed
     */
    public void move(Direction direction, float deltaTime) {
        if (direction == null || direction == Direction.NONE) {
            moveDirection = Direction.NONE;
            return;
        }

        moveDirection = direction;
        facingDirection = direction;

        // Calculate movement speed multiplier
        float speedMultiplier = 1.0f;

        // Apply speed boost if active
        if (inventory.hasSpeedBoost()) {
            speedMultiplier *= Constants.SPEED_BOOST_MULTIPLIER;
        }

        // Apply running if stamina available
        if (isRunning && stamina > 0) {
            speedMultiplier *= Constants.RUN_MULTIPLIER;
            stamina -= Constants.STAMINA_DRAIN * deltaTime;
        } else if (stamina <= 0) {
            isRunning = false;
        }

        // Apply continuous movement
        float speed = Constants.MOVEMENT_SPEED * speedMultiplier;
        Vector2 movement = direction.getVector().nor().scl(speed * deltaTime);
        position.add(movement);
    }

    /**
     * Set whether the player is attempting to run.
     * Actual running requires stamina.
     *
     * @param running true to run, false to walk
     */
    public void setRunning(boolean running) {
        this.isRunning = running;
    }

    /**
     * Take damage and lose a life.
     * Has invulnerability period after being hit.
     */
    public void takeDamage() {
        // Check if player has shield power-up
        if (inventory.hasShield()) {
            inventory.deactivateShield();
            shieldTimer = 0;
            return;
        }

        // Check invulnerability
        if (invulnerabilityTimer > 0) {
            return;
        }

        // Take damage
        lives--;
        damageFlashTimer = Constants.DAMAGE_FLASH_DURATION;
        invulnerabilityTimer = Constants.INVULNERABILITY_DURATION;
    }

    /**
     * Restore one lost life.
     */
    public void heal() {
        if (lives < maxLives) {
            lives++;
        }
    }

    /**
     * Collect a key from the maze.
     */
    public void collectKey() {
        inventory.addKey();
        addScore(Constants.SCORE_PER_KEY);
    }

    /**
     * Check if player has collected a key.
     *
     * @return true if at least one key collected
     */
    public boolean hasKey() {
        return inventory.hasKey();
    }

    /**
     * Use a collected key to open the exit.
     */
    public void useKey() {
        inventory.removeKey();
    }

    /**
     * Activate shield power-up.
     *
     * @param duration Duration in seconds
     */
    public void activateShield(float duration) {
        inventory.activateShield(duration);
        shieldTimer = duration;
    }

    /**
     * Activate speed boost power-up.
     *
     * @param duration Duration in seconds
     */
    public void activateSpeedBoost(float duration) {
        inventory.activateSpeedBoost(duration);
        speedBoostTimer = duration;
    }

    /**
     * Activate ghost mode power-up.
     * Allows passing through enemies.
     *
     * @param duration Duration in seconds
     */
    public void activateGhostMode(float duration) {
        inventory.activateGhostMode(duration);
        ghostModeTimer = duration;
    }

    /**
     * Add score points.
     */
    public void addScore(int points) {
        this.score += points;
    }

    /**
     * Add experience points.
     */
    public void addExperience(int xp) {
        this.experience += xp;
    }

    /**
     * Render the player sprite.
     */
    @Override
    public void render(SpriteBatch batch) {
        if (sprite != null && active) {
            sprite.draw(batch);
        }
    }

    // ============ GETTERS AND SETTERS ============

    public int getLives() {
        return lives;
    }

    public void setLives(int lives) {
        this.lives = Math.max(0, Math.min(lives, maxLives));
    }

    public int getMaxLives() {
        return maxLives;
    }

    public void setMaxLives(int max) {
        this.maxLives = max;
        if (lives > maxLives) lives = maxLives;
    }

    public boolean isAlive() {
        return lives > 0;
    }

    public Inventory getInventory() {
        return inventory;
    }

    public Direction getFacingDirection() {
        return facingDirection;
    }

    public Direction getMoveDirection() {
        return moveDirection;
    }

    public float getStamina() {
        return stamina;
    }

    public void setStamina(float stamina) {
        this.stamina = Math.max(0, Math.min(stamina, 100f));
    }

    public boolean isRunning() {
        return isRunning && stamina > 0;
    }

    public boolean isInvulnerable() {
        return invulnerabilityTimer > 0;
    }

    public float getInvulnerabilityTimer() {
        return invulnerabilityTimer;
    }

    public boolean hasShield() {
        return shieldTimer > 0;
    }

    public float getShieldTimer() {
        return shieldTimer;
    }

    public boolean hasSpeedBoost() {
        return speedBoostTimer > 0;
    }

    public float getSpeedBoostTimer() {
        return speedBoostTimer;
    }

    public boolean hasGhostMode() {
        return ghostModeTimer > 0;
    }

    public float getGhostModeTimer() {
        return ghostModeTimer;
    }

    public int getScore() {
        return score;
    }

    public int getExperience() {
        return experience;
    }
}

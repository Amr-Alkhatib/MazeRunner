package Maze.Runner.systems;

public class GameState {
    private int currentLevel;
    private boolean levelCompleted;
    private String playerCharacter; // RED, BLUE, GREEN
    private int keysCollected;
    private int health;

    public void levelWon() {
        levelCompleted = true;
    }

    public void levelLost() {
        // trigger GameOver
    }

    public void collectKey() {
        keysCollected++;
    }

    public void takeDamage(int amount) {
        health -= amount;
        if (health <= 0) levelLost();
    }
}


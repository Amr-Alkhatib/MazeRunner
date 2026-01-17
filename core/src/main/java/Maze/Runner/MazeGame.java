package Maze.Runner;

import Maze.Runner.screens.MainMenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class MazeGame extends Game {
    private SpriteBatch batch;

    // ... your other code ...

    @Override
    public void create() {
        batch = new SpriteBatch();
        setScreen((Screen) new MainMenuScreen(this));
    }

    @Override
    public void setScreen(Screen screen) {
        Screen currentScreen = getScreen();
        if (currentScreen != null) {
            currentScreen.hide();
            currentScreen.dispose();
        }
        super.setScreen(screen);
        if (screen != null) {
            screen.show();
        }
    }

    @Override
    public void dispose() {
        Screen screen = getScreen();
        if (screen != null) {
            screen.dispose();
        }
        batch.dispose();
    }

    public SpriteBatch getBatch() {
        return batch;
    }
}

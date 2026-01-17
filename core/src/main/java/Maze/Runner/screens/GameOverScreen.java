package Maze.Runner.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import Maze.Runner.MazeGame;

public class GameOverScreen implements Screen {
    private final MazeGame game;
    private final SpriteBatch batch;

    // Background + headline
    private Texture backgroundTexture;              // e.g. game_over/background.png (if you have one)
    private Texture gameOverHeadlineTexture;       // game_over/gameOver_headline/gameOver_pixel.png

    // Character textures (lost states)
    private Texture redLoseTexture;
    private Texture blueLoseTexture;
    private Texture greenLoseTexture;

    // Buttons
    private Texture homeButtonTexture;
    private Texture homeButtonHoverTexture;
    private Texture homeButtonPressedTexture;

    private Texture restartButtonTexture;
    private Texture restartButtonHoverTexture;
    private Texture restartButtonPressedTexture;

    // Click areas
    private Rectangle characterBounds;
    private Rectangle headlineBounds;
    private Rectangle homeButtonBounds;
    private Rectangle restartButtonBounds;

    // Button states
    private enum ButtonState {NORMAL, HOVER, PRESSED}

    private ButtonState homeState = ButtonState.NORMAL;
    private ButtonState restartState = ButtonState.NORMAL;

    // Which character lost (pass from GameScreen)
    private final String characterColor;   // "RED", "BLUE", or "GREEN"

    public GameOverScreen(MazeGame game, String characterColor) {
        this.game = game;
        this.batch = game.getBatch();
        this.characterColor = characterColor;

        loadAssets();
        initializeLayout();
    }
    private void loadAssets() {
        try {
            // Optional background
            try {
                backgroundTexture = new Texture(Gdx.files.internal("game_over/background.png"));
            } catch (Exception e) {
                backgroundTexture = null;
            }

            // Headline "GAME OVER"
            gameOverHeadlineTexture =
                new Texture(Gdx.files.internal("game_over/gameOver_headline/gameOver_pixel.png"));

            // Character lose sprites
            redLoseTexture   = new Texture(Gdx.files.internal("game_over/character_gameOver/line_red_lose.png"));
            blueLoseTexture  = new Texture(Gdx.files.internal("game_over/character_gameOver/line_blue_lose.png"));
            greenLoseTexture = new Texture(Gdx.files.internal("game_over/character_gameOver/line_green_lose.png"));

            // Buttons – paths exactly as in your screenshot
            homeButtonTexture         = new Texture(Gdx.files.internal("game_over/buttons/home_button.png"));
            homeButtonHoverTexture    = new Texture(Gdx.files.internal("game_over/buttons/home_button_hover.png"));
            homeButtonPressedTexture  = new Texture(Gdx.files.internal("game_over/buttons/home_button_pressed.png"));

            restartButtonTexture        = new Texture(Gdx.files.internal("game_over/buttons/restart_button.png"));
            restartButtonHoverTexture   = new Texture(Gdx.files.internal("game_over/buttons/restart_button_hover.png"));
            restartButtonPressedTexture = new Texture(Gdx.files.internal("game_over/buttons/restart_button_pressed.png"));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private void initializeLayout() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        // Headline – big and in the top third
        float headlineHeight = h * 0.25f;
        float headlineWidth  = headlineHeight * 3.0f;   // adjust ratio to match image
        float headlineX      = (w - headlineWidth) / 2f;
        float headlineY      = h - headlineHeight - h * 0.05f;
        headlineBounds = new Rectangle(headlineX, headlineY, headlineWidth, headlineHeight);

        // Character – roughly center of screen
        float charHeight = h * 0.28f;
        float charWidth  = charHeight;                 // square cube
        float charX      = (w - charWidth) / 2f;
        float charY      = headlineY - charHeight - h * 0.04f;
        characterBounds  = new Rectangle(charX, charY, charWidth, charHeight);

        // Buttons – bottom area, centered as [RESTART][HOME]
        float buttonHeight = h * 0.14f;
        float buttonWidth  = buttonHeight * 2.2f;      // long pixel buttons
        float totalButtonsWidth = buttonWidth * 2 + w * 0.04f;  // spacing between
        float startX = (w - totalButtonsWidth) / 2f;
        float buttonsY = h * 0.08f;

        restartButtonBounds = new Rectangle(startX, buttonsY, buttonWidth, buttonHeight);
        homeButtonBounds    = new Rectangle(startX + buttonWidth + w * 0.04f, buttonsY, buttonWidth, buttonHeight);
    }
    @Override
    public void show() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean mouseMoved(int x, int y) {
                float yy = Gdx.graphics.getHeight() - y;
                homeState = homeButtonBounds.contains(x, yy) ? ButtonState.HOVER : ButtonState.NORMAL;
                restartState = restartButtonBounds.contains(x, yy) ? ButtonState.HOVER : ButtonState.NORMAL;
                return false;
            }

            @Override
            public boolean touchDown(int x, int y, int pointer, int button) {
                float yy = Gdx.graphics.getHeight() - y;
                if (homeButtonBounds.contains(x, yy)) {
                    homeState = ButtonState.PRESSED;
                    return true;
                }
                if (restartButtonBounds.contains(x, yy)) {
                    restartState = ButtonState.PRESSED;
                    return true;
                }
                return false;
            }

            @Override
            public boolean touchUp(int x, int y, int pointer, int button) {
                float yy = Gdx.graphics.getHeight() - y;

                if (homeButtonBounds.contains(x, yy)) {
                    onHomePressed();
                } else if (restartButtonBounds.contains(x, yy)) {
                    onRestartPressed();
                }

                homeState = ButtonState.NORMAL;
                restartState = ButtonState.NORMAL;
                return false;
            }
        });
    }

    @Override public void hide() { Gdx.input.setInputProcessor(null); }
    @Override public void resize(int width, int height) { initializeLayout(); }
    @Override public void pause() {}
    @Override public void resume() {}
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Background
        if (backgroundTexture != null) {
            batch.draw(backgroundTexture, 0, 0,
                Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

        // Headline
        batch.draw(gameOverHeadlineTexture,
            headlineBounds.x, headlineBounds.y,
            headlineBounds.width, headlineBounds.height);

        // Character based on color
        Texture loseTex = redLoseTexture;
        if ("BLUE".equals(characterColor))  loseTex = blueLoseTexture;
        if ("GREEN".equals(characterColor)) loseTex = greenLoseTexture;

        batch.draw(loseTex,
            characterBounds.x, characterBounds.y,
            characterBounds.width, characterBounds.height);

        // Buttons
        drawButton(restartButtonBounds, restartState,
            restartButtonTexture, restartButtonHoverTexture, restartButtonPressedTexture);

        drawButton(homeButtonBounds, homeState,
            homeButtonTexture, homeButtonHoverTexture, homeButtonPressedTexture);

        batch.end();
    }

    private void drawButton(Rectangle b, ButtonState state,
                            Texture normal, Texture hover, Texture pressed) {
        Texture t = normal;
        if (state == ButtonState.HOVER && hover != null) t = hover;
        if (state == ButtonState.PRESSED && pressed != null) t = pressed;
        batch.draw(t, b.x, b.y, b.width, b.height);
    }
    private void onHomePressed() {
        game.setScreen(new MainMenuScreen(game));
    }

    private void onRestartPressed() {
        game.setScreen(new GameScreen(game, characterColor));
    }

    @Override
    public void dispose() {
        if (backgroundTexture != null) backgroundTexture.dispose();
        if (gameOverHeadlineTexture != null) gameOverHeadlineTexture.dispose();
        if (redLoseTexture != null) redLoseTexture.dispose();
        if (blueLoseTexture != null) blueLoseTexture.dispose();
        if (greenLoseTexture != null) greenLoseTexture.dispose();
        if (homeButtonTexture != null) homeButtonTexture.dispose();
        if (homeButtonHoverTexture != null) homeButtonHoverTexture.dispose();
        if (homeButtonPressedTexture != null) homeButtonPressedTexture.dispose();
        if (restartButtonTexture != null) restartButtonTexture.dispose();
        if (restartButtonHoverTexture != null) restartButtonHoverTexture.dispose();
        if (restartButtonPressedTexture != null) restartButtonPressedTexture.dispose();
    }
}



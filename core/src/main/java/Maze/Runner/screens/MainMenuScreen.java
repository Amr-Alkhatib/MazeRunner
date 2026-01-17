package Maze.Runner.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import Maze.Runner.MazeGame;

/**
 * Main Menu Screen - First screen players see when launching the game
 * Displays: Start, Settings, Load Game, Leaderboard, Exit buttons
 */
public class MainMenuScreen implements Screen {
    @Override
    public void show() {
        setupInputHandler();
        System.out.println("🎮 MainMenuScreen SHOWN - Input handler activated");
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
        System.out.println("🎮 MainMenuScreen HIDDEN - Input handler cleared");
    }

    private final MazeGame game;
    private final SpriteBatch batch;


    private Texture backgroundTexture;


    private Texture startButtonTexture;
    private Texture startButtonHoverTexture;
    private Texture startButtonPressedTexture;


    private Texture settingsButtonTexture;
    private Texture settingsButtonHoverTexture;
    private Texture settingsButtonPressedTexture;


    private Texture loadButtonTexture;
    private Texture loadButtonHoverTexture;
    private Texture loadButtonPressedTexture;


    private Texture leaderboardButtonTexture;
    private Texture leaderboardButtonHoverTexture;
    private Texture leaderboardButtonPressedTexture;


    private Texture exitButtonTexture;
    private Texture exitButtonHoverTexture;
    private Texture exitButtonPressedTexture;

    // ==================== BUTTON CLICK AREAS ====================
    private Rectangle startButtonBounds;
    private Rectangle settingsButtonBounds;
    private Rectangle loadButtonBounds;
    private Rectangle leaderboardButtonBounds;
    private Rectangle exitButtonBounds;

    // ==================== BUTTON STATES ====================
    private ButtonState startButtonState = ButtonState.NORMAL;
    private ButtonState settingsButtonState = ButtonState.NORMAL;
    private ButtonState loadButtonState = ButtonState.NORMAL;
    private ButtonState leaderboardButtonState = ButtonState.NORMAL;
    private ButtonState exitButtonState = ButtonState.NORMAL;

    // ==================== UI CONSTANTS ====================
    public static final float BUTTON_WIDTH = 280f;
    private static final float BUTTON_HEIGHT = 70f;

    public static final float BUTTON_SPACING = 100f;
    public static final float BOTTOM_PADDING = 100f;  // Distance from bottom of screen


    enum ButtonState {
        NORMAL, HOVER, PRESSED
    }

    // ==================== CONSTRUCTOR ====================
    public MainMenuScreen(MazeGame game) {
        this.game = game;
        this.batch = game.getBatch();

        loadAssets();
        initializeButtonBounds();
        setupInputHandler();
    }

    // ==================== LOAD ASSETS ====================
    // ==================== LOAD ASSETS ====================
    private void loadAssets() {
        try {
            // Background
            backgroundTexture = new Texture(Gdx.files.internal("background.png"));

            // Start Button (3 states)
            startButtonTexture = new Texture(Gdx.files.internal("main_menu/start_button.png"));
            startButtonHoverTexture = new Texture(Gdx.files.internal("main_menu/start_button_hover.png"));
            startButtonPressedTexture = new Texture(Gdx.files.internal("main_menu/start_button_pressed.png"));

            // Settings Button (3 states)
            settingsButtonTexture = new Texture(Gdx.files.internal("main_menu/settings_button.png"));
            settingsButtonHoverTexture = new Texture(Gdx.files.internal("main_menu/settings_button_hover.png"));
            settingsButtonPressedTexture = new Texture(Gdx.files.internal("main_menu/settings_button_pressed.png"));

            // Load Button (3 states)
            loadButtonTexture = new Texture(Gdx.files.internal("main_menu/load_button.png"));
            loadButtonHoverTexture = new Texture(Gdx.files.internal("main_menu/load_button_hover.png"));
            loadButtonPressedTexture = new Texture(Gdx.files.internal("main_menu/load_button_pressed.png"));

            // Leaderboard Button (3 states)
            leaderboardButtonTexture = new Texture(Gdx.files.internal("main_menu/empty_buttons/button.png"));
            leaderboardButtonHoverTexture = new Texture(Gdx.files.internal("main_menu/empty_buttons/button_hover.png"));
            leaderboardButtonPressedTexture = new Texture(Gdx.files.internal("main_menu/empty_buttons/button_pressed.png"));

            // Exit Button (3 states)
            exitButtonTexture = new Texture(Gdx.files.internal("main_menu/exit_button.png"));
            exitButtonHoverTexture = new Texture(Gdx.files.internal("main_menu/exit_button_hover.png"));
            exitButtonPressedTexture = new Texture(Gdx.files.internal("main_menu/exit_button_pressed.png"));

            System.out.println("✅ All assets loaded successfully!");

        } catch (Exception e) {
            System.out.println("❌ ASSET LOADING ERROR: " + e.getMessage());
            System.out.println("⚠️ Make sure button files are in: assets/main_menu/ folder");
        }
    }


    // ==================== INITIALIZE BUTTON BOUNDS ====================
    private void initializeButtonBounds() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();
        float centerX = screenWidth / 2f - BUTTON_WIDTH / 2f;
        float bottomY = BOTTOM_PADDING;

        startButtonBounds = new Rectangle(centerX, bottomY + BUTTON_SPACING * 4, BUTTON_WIDTH, BUTTON_HEIGHT);
        settingsButtonBounds = new Rectangle(centerX, bottomY + BUTTON_SPACING * 3, BUTTON_WIDTH, BUTTON_HEIGHT);
        loadButtonBounds = new Rectangle(centerX, bottomY + BUTTON_SPACING * 2, BUTTON_WIDTH, BUTTON_HEIGHT);
        leaderboardButtonBounds = new Rectangle(centerX, bottomY + BUTTON_SPACING , BUTTON_WIDTH, BUTTON_HEIGHT);
        exitButtonBounds = new Rectangle(centerX, bottomY , BUTTON_WIDTH, BUTTON_HEIGHT);

        System.out.println("📍 Buttons positioned at screen width: " + screenWidth + ", height: " + screenHeight);
    }


    // ==================== SETUP INPUT HANDLER ====================
    private void setupInputHandler() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                float y = Gdx.graphics.getHeight() - screenY;

                startButtonState = startButtonBounds.contains(screenX, y) ? ButtonState.HOVER : ButtonState.NORMAL;
                settingsButtonState = settingsButtonBounds.contains(screenX, y) ? ButtonState.HOVER : ButtonState.NORMAL;
                loadButtonState = loadButtonBounds.contains(screenX, y) ? ButtonState.HOVER : ButtonState.NORMAL;
                leaderboardButtonState = leaderboardButtonBounds.contains(screenX, y) ? ButtonState.HOVER : ButtonState.NORMAL;
                exitButtonState = exitButtonBounds.contains(screenX, y) ? ButtonState.HOVER : ButtonState.NORMAL;

                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                float y = Gdx.graphics.getHeight() - screenY;

                if (startButtonBounds.contains(screenX, y)) {
                    startButtonState = ButtonState.PRESSED;
                    return true;
                }
                if (settingsButtonBounds.contains(screenX, y)) {
                    settingsButtonState = ButtonState.PRESSED;
                    return true;
                }
                if (loadButtonBounds.contains(screenX, y)) {
                    loadButtonState = ButtonState.PRESSED;
                    return true;
                }
                if (leaderboardButtonBounds.contains(screenX, y)) {
                    leaderboardButtonState = ButtonState.PRESSED;
                    return true;
                }
                if (exitButtonBounds.contains(screenX, y)) {
                    exitButtonState = ButtonState.PRESSED;
                    return true;
                }

                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                float y = Gdx.graphics.getHeight() - screenY;

                if (startButtonBounds.contains(screenX, y)) {
                    handleStartPressed();
                }
                if (settingsButtonBounds.contains(screenX, y)) {
                    handleSettingsPressed();
                }
                if (loadButtonBounds.contains(screenX, y)) {
                    handleLoadPressed();
                }
                if (leaderboardButtonBounds.contains(screenX, y)) {
                    handleLeaderboardPressed();
                }
                if (exitButtonBounds.contains(screenX, y)) {
                    handleExitPressed();
                }

                startButtonState = ButtonState.NORMAL;
                settingsButtonState = ButtonState.NORMAL;
                loadButtonState = ButtonState.NORMAL;
                leaderboardButtonState = ButtonState.NORMAL;
                exitButtonState = ButtonState.NORMAL;

                return false;
            }
        });
    }

    // ==================== RENDER ====================
    public void render(float deltaTime) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // Draw background
        if (backgroundTexture != null) {
            batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }

        // Draw buttons
        drawButton(startButtonBounds, startButtonState, startButtonTexture, startButtonHoverTexture, startButtonPressedTexture);
        drawButton(settingsButtonBounds, settingsButtonState, settingsButtonTexture, settingsButtonHoverTexture, settingsButtonPressedTexture);
        drawButton(loadButtonBounds, loadButtonState, loadButtonTexture, loadButtonHoverTexture, loadButtonPressedTexture);
        drawButton(leaderboardButtonBounds, leaderboardButtonState, leaderboardButtonTexture, leaderboardButtonHoverTexture, leaderboardButtonPressedTexture);
        drawButton(exitButtonBounds, exitButtonState, exitButtonTexture, exitButtonHoverTexture, exitButtonPressedTexture);

        batch.end();
    }

    // ==================== DRAW BUTTON HELPER ====================
    private void drawButton(Rectangle bounds, ButtonState state, Texture normalTex, Texture hoverTex, Texture pressedTex) {
        Texture texture = normalTex;

        if (state == ButtonState.HOVER && hoverTex != null) {
            texture = hoverTex;
        } else if (state == ButtonState.PRESSED && pressedTex != null) {
            texture = pressedTex;
        }

        if (texture != null) {
            batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
        }
    }

    // ==================== BUTTON HANDLERS ====================
    private void handleStartPressed() {
        System.out.println("✅ START button clicked");

         game.setScreen(new CharacterSelectionScreen(game));
    }

    private void handleSettingsPressed() {
        System.out.println("✅ SETTINGS button clicked");
        // [TODO] Uncomment and replace with SettingsScreen when created
        // game.setScreen(new SettingsScreen(game));
    }

    private void handleLoadPressed() {
        System.out.println("✅ LOAD button clicked");
        // [TODO] Uncomment and replace with LoadGameScreen when created
        // game.setScreen(new LoadGameScreen(game));
    }

    private void handleLeaderboardPressed() {
        System.out.println("✅ LEADERBOARD button clicked");
        // [TODO] Uncomment and replace with LeaderboardScreen when created
        // game.setScreen(new LeaderboardScreen(game));
    }

    private void handleExitPressed() {
        System.out.println("✅ EXIT button clicked - closing game");
        Gdx.app.exit();
    }

    // ==================== DISPOSE ====================
    public void dispose() {
        Gdx.input.setInputProcessor(null);

        if (backgroundTexture != null) backgroundTexture.dispose();
        if (startButtonTexture != null) startButtonTexture.dispose();
        if (startButtonHoverTexture != null) startButtonHoverTexture.dispose();
        if (startButtonPressedTexture != null) startButtonPressedTexture.dispose();
        if (settingsButtonTexture != null) settingsButtonTexture.dispose();
        if (settingsButtonHoverTexture != null) settingsButtonHoverTexture.dispose();
        if (settingsButtonPressedTexture != null) settingsButtonPressedTexture.dispose();
        if (loadButtonTexture != null) loadButtonTexture.dispose();
        if (loadButtonHoverTexture != null) loadButtonHoverTexture.dispose();
        if (loadButtonPressedTexture != null) loadButtonPressedTexture.dispose();
        if (leaderboardButtonTexture != null) leaderboardButtonTexture.dispose();
        if (leaderboardButtonHoverTexture != null) leaderboardButtonHoverTexture.dispose();
        if (leaderboardButtonPressedTexture != null) leaderboardButtonPressedTexture.dispose();
        if (exitButtonTexture != null) exitButtonTexture.dispose();
        if (exitButtonHoverTexture != null) exitButtonHoverTexture.dispose();
        if (exitButtonPressedTexture != null) exitButtonPressedTexture.dispose();
    }
}

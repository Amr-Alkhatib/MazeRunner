package Maze.Runner.screens;

import Maze.Runner.MazeGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

public class GameScreen implements Screen {
    private final MazeGame game;
    private final SpriteBatch batch;

    // ---------------- GAME UI ASSETS ----------------
    private Texture keyIconTexture;              // game_ui/key/key.png
    private Texture keyBarEmptyTexture;         // game_ui/key/key_bar_empty.png
    private Texture keyBarFullTexture;          // game_ui/key/key_bar_full.png
    private Texture keyBar1Texture;             // game_ui/key/key_bar_1.png
    private Texture keyBar2Texture;             // game_ui/key/key_bar_2.png
    private Texture fullKeyTexture;             // game_ui/key/full_key.png
    private Texture emptyKeyTexture;            // game_ui/key/empty_key.png

    // Health bar heads per color
    private Texture redHeadTexture;             // game_ui/healthbar/red_health/line_red_head.png
    private Texture greenHeadTexture;           // game_ui/healthbar/green_health/line_green_head.png
    private Texture blueHeadTexture;            // game_ui/healthbar/blue_health/line_blue_head.png

    // Generic health bar fill/empty
    private Texture healthEmptyTexture;         // game_ui/healthbar/example_healthbar/health_empty.png
    private Texture healthFullTexture;          // game_ui/healthbar/example_healthbar/health_full.png

    // Direction / pause button UI
    private Texture directionBackgroundTexture; // game_ui/show_direction/direction_background.png
    private Texture directionExitTexture;       // game_ui/show_direction/direction_exit.png

    // ---------------- PAUSE MENU ASSETS ----------------
    private Texture pauseOverlayTexture;        // optional; else draw a semi-transparent rect
    private Texture pauseStartButtonTexture;    // main/buttons/start_button.png
    private Texture pauseStartHoverTexture;
    private Texture pauseStartPressedTexture;

    private Texture pauseLoadButtonTexture;     // main/buttons/load_button.png
    private Texture pauseLoadHoverTexture;
    private Texture pauseLoadPressedTexture;

    private Texture pauseSettingsButtonTexture; // main/buttons/settings_button.png
    private Texture pauseSettingsHoverTexture;
    private Texture pauseSettingsPressedTexture;

    private Texture pauseExitButtonTexture;     // main/buttons/exit_button.png
    private Texture pauseExitHoverTexture;
    private Texture pauseExitPressedTexture;

    // ---------------- UI STATE ----------------
    private Rectangle healthBarBounds;
    private Rectangle healthFillBounds;
    private Rectangle healthHeadBounds;

    private Rectangle keyIconBounds;
    private Rectangle keyBarBounds;
    private Rectangle keySlot1Bounds;
    private Rectangle keySlot2Bounds;
    private Rectangle keySlot3Bounds;

    private Rectangle pauseButtonBounds;        // arrow in the top middle (direction UI)
    private Rectangle pausePanelBounds;
    private Rectangle pauseStartBounds;
    private Rectangle pauseLoadBounds;
    private Rectangle pauseSettingsBounds;
    private Rectangle pauseExitBounds;

    private boolean isPaused = false;

    private enum ButtonState { NORMAL, HOVER, PRESSED }
    private ButtonState pauseStartState   = ButtonState.NORMAL;
    private ButtonState pauseLoadState    = ButtonState.NORMAL;
    private ButtonState pauseSettingsState= ButtonState.NORMAL;
    private ButtonState pauseExitState    = ButtonState.NORMAL;

    // From CharacterSelectScreen
    private final String characterColor; // "RED", "GREEN", "BLUE"

    // Game state values used for UI only
    private int currentHealth = 3;   // 0‑3, adjust to match your actual game logic
    private int maxHealth = 3;
    private int collectedKeys = 0;   // 0‑3
    private int maxKeys = 3;


    private void loadUiAssets() {
        // Key UI
        keyIconTexture      = new Texture(Gdx.files.internal("game_ui/key/key.png"));
        keyBarEmptyTexture  = new Texture(Gdx.files.internal("game_ui/key/key_bar_empty.png"));
        keyBarFullTexture   = new Texture(Gdx.files.internal("game_ui/key/key_bar_full.png"));
        keyBar1Texture      = new Texture(Gdx.files.internal("game_ui/key/key_bar_1.png"));
        keyBar2Texture      = new Texture(Gdx.files.internal("game_ui/key/key_bar_2.png"));
        fullKeyTexture      = new Texture(Gdx.files.internal("game_ui/key/full_key.png"));
        emptyKeyTexture     = new Texture(Gdx.files.internal("game_ui/key/empty_key.png"));

        // Health bar
        healthEmptyTexture  = new Texture(Gdx.files.internal("game_ui/healthbar/example_healthbar/health_empty.png"));
        healthFullTexture   = new Texture(Gdx.files.internal("game_ui/healthbar/example_healthbar/health_full.png"));
        redHeadTexture      = new Texture(Gdx.files.internal("game_ui/healthbar/red_health/line_red_head.png"));
        greenHeadTexture    = new Texture(Gdx.files.internal("game_ui/healthbar/green_health/line_green_head.png"));
        blueHeadTexture     = new Texture(Gdx.files.internal("game_ui/healthbar/blue_health/line_blue_head.png"));

        // Direction / pause arrow
        directionBackgroundTexture = new Texture(Gdx.files.internal("game_ui/show_direction/direction_background.png"));
        directionExitTexture       = new Texture(Gdx.files.internal("game_ui/show_direction/direction_exit.png"));

        // Pause menu buttons (same assets as main menu)
        pauseStartButtonTexture    = new Texture(Gdx.files.internal("main_menu/start_button.png"));
        pauseStartHoverTexture     = new Texture(Gdx.files.internal("main_menu/start_button_hover.png"));
        pauseStartPressedTexture   = new Texture(Gdx.files.internal("main_menu/start_button_pressed.png"));

        pauseLoadButtonTexture     = new Texture(Gdx.files.internal("main_menu/load_button.png"));
        pauseLoadHoverTexture      = new Texture(Gdx.files.internal("main_menu/load_button_hover.png"));
        pauseLoadPressedTexture    = new Texture(Gdx.files.internal("main_menu/load_button_pressed.png"));

        pauseSettingsButtonTexture = new Texture(Gdx.files.internal("main_menu/settings_button.png"));
        pauseSettingsHoverTexture  = new Texture(Gdx.files.internal("main_menu/settings_button_hover.png"));
        pauseSettingsPressedTexture= new Texture(Gdx.files.internal("main_menu/settings_button_pressed.png"));

        pauseExitButtonTexture     = new Texture(Gdx.files.internal("main_menu/exit_button.png"));
        pauseExitHoverTexture      = new Texture(Gdx.files.internal("main_menu/exit_button_hover.png"));
        pauseExitPressedTexture    = new Texture(Gdx.files.internal("main_menu/exit_button_pressed.png"));

    }
    public GameScreen(MazeGame game, String characterColor) {
        this.game = game;
        this.batch = game.getBatch();
        this.characterColor = characterColor;

        // your existing maze/player setup...

        loadUiAssets();
        initUiLayout();
        setupInput();
    }


    private void initUiLayout() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        float margin = h * 0.03f;

        // --- KEY ICON far left ---
        float keyIconSize = h * 0.10f;
        float keyIconX    = margin;
        float keyIconY    = h - keyIconSize - margin;
        keyIconBounds     = new Rectangle(keyIconX, keyIconY, keyIconSize, keyIconSize);

        // --- KEY BAR next to key icon (left side) ---
        float keyBarHeight = keyIconSize * 0.45f;
        float keyBarWidth  = keyBarHeight * 5.0f;
        float keyBarX      = keyIconX + keyIconSize + margin * 0.4f;
        float keyBarY      = keyIconY + (keyIconSize - keyBarHeight) / 2f;
        keyBarBounds       = new Rectangle(keyBarX, keyBarY, keyBarWidth, keyBarHeight);

        float slotSize = keyBarHeight * 0.9f;
        float slotY    = keyBarY + (keyBarHeight - slotSize) / 2f;
        keySlot1Bounds = new Rectangle(keyBarX + slotSize * 0.2f, slotY, slotSize, slotSize);
        keySlot2Bounds = new Rectangle(keyBarX + slotSize * 1.6f, slotY, slotSize, slotSize);
        keySlot3Bounds = new Rectangle(keyBarX + slotSize * 3.0f, slotY, slotSize, slotSize);

        // --- BIG HEALTH BAR on the RIGHT top ---
        float healthHeight = h * 0.09f;           // bigger bar
        float healthWidth  = healthHeight * 5.0f; // longer bar
        float healthX      = w - healthWidth - margin * 2.0f;
        float healthY      = h - healthHeight - margin;
        healthBarBounds    = new Rectangle(healthX, healthY, healthWidth, healthHeight);
        healthFillBounds   = new Rectangle(healthX, healthY, healthWidth, healthHeight);

        float headSize   = healthHeight * 1.4f;
        float headX      = healthX + healthWidth + margin * 0.3f;
        float headY      = healthY - (headSize - healthHeight) / 2f;
        healthHeadBounds = new Rectangle(headX, headY, headSize, headSize);

        // --- PAUSE ARROW centered at the TOP ---
        float pauseSize = h * 0.10f;
        float pauseX    = (w - pauseSize) / 2f;
        float pauseY    = h - pauseSize - margin;
        pauseButtonBounds = new Rectangle(pauseX, pauseY, pauseSize, pauseSize);

        // --- PAUSE PANEL & BUTTONS (unchanged) ---
        float panelWidth  = w * 0.4f;
        float panelHeight = h * 0.5f;
        float panelX      = (w - panelWidth) / 2f;
        float panelY      = (h - panelHeight) / 2f;
        pausePanelBounds  = new Rectangle(panelX, panelY, panelWidth, panelHeight);

        float btnHeight   = panelHeight * 0.16f;
        float btnWidth    = panelWidth * 0.8f;
        float btnX        = panelX + (panelWidth - btnWidth) / 2f;
        float gap         = panelHeight * 0.04f;
        float firstBtnY   = panelY + panelHeight - btnHeight - gap * 1.5f;

        pauseStartBounds    = new Rectangle(btnX, firstBtnY, btnWidth, btnHeight);
        pauseLoadBounds     = new Rectangle(btnX, firstBtnY - (btnHeight + gap), btnWidth, btnHeight);
        pauseSettingsBounds = new Rectangle(btnX, firstBtnY - 2f * (btnHeight + gap), btnWidth, btnHeight);
        pauseExitBounds     = new Rectangle(btnX, firstBtnY - 3f * (btnHeight + gap), btnWidth, btnHeight);
    }


    private void setupInput() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean mouseMoved(int x, int y) {
                float yy = Gdx.graphics.getHeight() - y;



                if (!isPaused) {
                    // Only hover for pause arrow when running
                    return false;
                }

                // In pause menu: hover buttons
                pauseStartState = pauseStartBounds.contains(x, yy) ? ButtonState.HOVER : ButtonState.NORMAL;
                pauseLoadState = pauseLoadBounds.contains(x, yy)     ? ButtonState.HOVER : ButtonState.NORMAL;
                pauseSettingsState = pauseSettingsBounds.contains(x, yy) ? ButtonState.HOVER : ButtonState.NORMAL;
                pauseExitState = pauseExitBounds.contains(x, yy)     ? ButtonState.HOVER : ButtonState.NORMAL;
                return false;
            }

            @Override
            public boolean touchDown(int x, int y, int pointer, int button) {
                float yy = Gdx.graphics.getHeight() - y;

                if (!isPaused) {
                    // Click on pause arrow
                    if (pauseButtonBounds.contains(x, yy)) {
                        isPaused = true;
                        return true;
                    }
                    return false;
                }
                if (pauseStartBounds.contains(x, yy)) {
                    pauseStartState = ButtonState.PRESSED;
                    return true;
                }
                // Inside pause menu
                if (pauseStartBounds.contains(x, yy)) {
                    pauseStartState = ButtonState.PRESSED;
                    return true;
                }
                if (pauseLoadBounds.contains(x, yy)) {
                    pauseLoadState = ButtonState.PRESSED;
                    return true;
                }
                if (pauseSettingsBounds.contains(x, yy)) {
                    pauseSettingsState = ButtonState.PRESSED;
                    return true;
                }
                if (pauseExitBounds.contains(x, yy)) {
                    pauseExitState = ButtonState.PRESSED;
                    return true;
                }

                return false;
            }

            @Override
            public boolean touchUp(int x, int y, int pointer, int button) {
                float yy = Gdx.graphics.getHeight() - y;

                if (!isPaused) return false;

                if (pauseStartBounds.contains(x, yy)) {
                    onPauseStartClicked();   // resume / restart
                } else if (pauseLoadBounds.contains(x, yy)) {
                    onPauseLoadClicked();
                } else if (pauseSettingsBounds.contains(x, yy)) {
                    onPauseSettingsClicked();
                } else if (pauseExitBounds.contains(x, yy)) {
                    onPauseExitClicked();
                }

                // Reset states
                pauseStartState = pauseLoadState = pauseSettingsState = pauseExitState = ButtonState.NORMAL;
                return false;
            }
        });
    }
    private void onPauseStartClicked() {
        // Example: resume game
        isPaused = false;
    }

    private void onPauseLoadClicked() {
        // TODO: implement load
    }

    private void onPauseSettingsClicked() {
        // TODO: implement settings
    }

    private void onPauseExitClicked() {
        game.setScreen(new MainMenuScreen(game));
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        if (!isPaused) {
            // your existing game update logic:
            // updatePlayer(delta);
            // updateEnemies(delta);
            // etc.
        }

        // your existing clear + maze drawing
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();

        // 1) draw your maze, player, enemies, etc. here...

        // 2) draw UI overlay, always on top
        drawHealthBar();
        drawKeyUi();
        drawPauseArrow();

        if (isPaused) {
            drawPauseOverlay();
            drawPauseMenuButtons();
        }

        batch.end();
    }

    private void drawHealthBar() {
        // bar background
        batch.draw(healthEmptyTexture,
            healthBarBounds.x, healthBarBounds.y,
            healthBarBounds.width, healthBarBounds.height);

        // filled portion based on health ratio
        float ratio = Math.max(0f, Math.min(1f, (float) currentHealth / maxHealth));
        float filledWidth = healthBarBounds.width * ratio;
        if (filledWidth > 0) {
            batch.draw(healthFullTexture,
                healthFillBounds.x, healthFillBounds.y,
                filledWidth, healthFillBounds.height);
        }

        // colored head according to characterColor
        Texture headTex = redHeadTexture;
        if ("GREEN".equals(characterColor)) headTex = greenHeadTexture;
        if ("BLUE".equals(characterColor))  headTex = blueHeadTexture;

        batch.draw(headTex,
            healthHeadBounds.x, healthHeadBounds.y,
            healthHeadBounds.width, healthHeadBounds.height);
    }

    private void drawKeyUi() {
        // key icon
        batch.draw(keyIconTexture,
            keyIconBounds.x, keyIconBounds.y,
            keyIconBounds.width, keyIconBounds.height);

        // bar background (optional)
        batch.draw(keyBarEmptyTexture,
            keyBarBounds.x, keyBarBounds.y,
            keyBarBounds.width, keyBarBounds.height);

        // slots – full / empty based on collectedKeys
        drawKeySlot(keySlot1Bounds, collectedKeys >= 1);
        drawKeySlot(keySlot2Bounds, collectedKeys >= 2);
        drawKeySlot(keySlot3Bounds, collectedKeys >= 3);
    }

    private void drawKeySlot(Rectangle bounds, boolean full) {
        Texture tex = full ? fullKeyTexture : emptyKeyTexture;
        batch.draw(tex, bounds.x, bounds.y, bounds.width, bounds.height);
    }

    private void drawPauseArrow() {
        // background circle/square
        batch.draw(directionBackgroundTexture,
            pauseButtonBounds.x, pauseButtonBounds.y,
            pauseButtonBounds.width, pauseButtonBounds.height);

        // arrow icon centered inside
        float arrowSize = pauseButtonBounds.width * 0.6f;
        float ax = pauseButtonBounds.x + (pauseButtonBounds.width - arrowSize) / 2f;
        float ay = pauseButtonBounds.y + (pauseButtonBounds.height - arrowSize) / 2f;
        batch.draw(directionExitTexture, ax, ay, arrowSize, arrowSize);
    }

    private void drawPauseOverlay() {
        // half‑transparent dark layer over entire screen
        batch.setColor(0, 0, 0, 0.5f);
        batch.draw(healthEmptyTexture,  // any 1x1 or simple texture; reused here
            0, 0,
            Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.setColor(1, 1, 1, 1);

        // central panel (optional slightly lighter)
        batch.setColor(0, 0, 0, 0.8f);
        batch.draw(healthEmptyTexture,
            pausePanelBounds.x, pausePanelBounds.y,
            pausePanelBounds.width, pausePanelBounds.height);
        batch.setColor(1, 1, 1, 1);
    }

    private void drawPauseMenuButtons() {
        drawPauseButton(pauseStartBounds, pauseStartState,
            pauseStartButtonTexture, pauseStartHoverTexture, pauseStartPressedTexture);

        drawPauseButton(pauseLoadBounds, pauseLoadState,
            pauseLoadButtonTexture, pauseLoadHoverTexture, pauseLoadPressedTexture);

        drawPauseButton(pauseSettingsBounds, pauseSettingsState,
            pauseSettingsButtonTexture, pauseSettingsHoverTexture, pauseSettingsPressedTexture);

        drawPauseButton(pauseExitBounds, pauseExitState,
            pauseExitButtonTexture, pauseExitHoverTexture, pauseExitPressedTexture);
    }

    private void drawPauseButton(Rectangle b, ButtonState state,
                                 Texture normal, Texture hover, Texture pressed) {
        Texture t = normal;
        if (state == ButtonState.HOVER && hover != null)   t = hover;
        if (state == ButtonState.PRESSED && pressed != null) t = pressed;
        batch.draw(t, b.x, b.y, b.width, b.height);
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

    }

    @Override
    public void dispose() {
        // health
        if (healthEmptyTexture != null) healthEmptyTexture.dispose();
        if (healthFullTexture != null) healthFullTexture.dispose();
        if (redHeadTexture != null) redHeadTexture.dispose();
        if (greenHeadTexture != null) greenHeadTexture.dispose();
        if (blueHeadTexture != null) blueHeadTexture.dispose();

// keys
        if (keyIconTexture != null) keyIconTexture.dispose();
        if (keyBarEmptyTexture != null) keyBarEmptyTexture.dispose();
        if (keyBarFullTexture != null) keyBarFullTexture.dispose();
        if (keyBar1Texture != null) keyBar1Texture.dispose();
        if (keyBar2Texture != null) keyBar2Texture.dispose();
        if (fullKeyTexture != null) fullKeyTexture.dispose();
        if (emptyKeyTexture != null) emptyKeyTexture.dispose();

// direction / pause
        if (directionBackgroundTexture != null) directionBackgroundTexture.dispose();
        if (directionExitTexture != null) directionExitTexture.dispose();

// pause buttons
        if (pauseStartButtonTexture != null) pauseStartButtonTexture.dispose();
        if (pauseStartHoverTexture != null) pauseStartHoverTexture.dispose();
        if (pauseStartPressedTexture != null) pauseStartPressedTexture.dispose();

        if (pauseLoadButtonTexture != null) pauseLoadButtonTexture.dispose();
        if (pauseLoadHoverTexture != null) pauseLoadHoverTexture.dispose();
        if (pauseLoadPressedTexture != null) pauseLoadPressedTexture.dispose();

        if (pauseSettingsButtonTexture != null) pauseSettingsButtonTexture.dispose();
        if (pauseSettingsHoverTexture != null) pauseSettingsHoverTexture.dispose();
        if (pauseSettingsPressedTexture != null) pauseSettingsPressedTexture.dispose();

        if (pauseExitButtonTexture != null) pauseExitButtonTexture.dispose();
        if (pauseExitHoverTexture != null) pauseExitHoverTexture.dispose();
        if (pauseExitPressedTexture != null) pauseExitPressedTexture.dispose();

    }
}

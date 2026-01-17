package Maze.Runner.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import Maze.Runner.MazeGame;

/**
 * Character Select Screen - Choose character color before playing
 * Layout: TOP 50%: TITLE
 *         BOTTOM 50%: [HOME] ← [CHARACTER] → [PLAY]
 * FULLY RESPONSIVE - All positions and sizes scale with screen size
 */
public class CharacterSelectionScreen implements Screen {
    private final MazeGame game;
    private final SpriteBatch batch;
    private BitmapFont titleFont;


    // ==================== ASSETS ====================
    private Texture redCharacterSelectedTexture;
    private Texture blueCharacterSelectedTexture;
    private Texture greenCharacterSelectedTexture;


    private Texture leftArrowTexture;
    private Texture rightArrowTexture;

    private Texture homeButtonTexture;
    private Texture homeButtonHoverTexture;
    private Texture homeButtonPressedTexture;

    private Texture playButtonTexture;
    private Texture playButtonHoverTexture;
    private Texture playButtonPressedTexture;

    // ==================== BUTTON CLICK AREAS ====================
    private Rectangle leftArrowBounds;
    private Rectangle rightArrowBounds;
    private Rectangle characterDisplayBounds;
    private Rectangle homeButtonBounds;
    private Rectangle playButtonBounds;
    private Rectangle selectYourCharacterBounds;


    // ==================== BUTTON STATES ====================
    private ButtonState leftArrowState = ButtonState.NORMAL;
    private ButtonState rightArrowState = ButtonState.NORMAL;
    private ButtonState homeButtonState = ButtonState.NORMAL;
    private ButtonState playButtonState = ButtonState.NORMAL;

    // ==================== SELECTED CHARACTER ====================
    private int currentCharacterIndex = 0;  // 0=RED, 1=BLUE, 2=GREEN
    private String[] characters = {"RED", "BLUE", "GREEN"};
    private Texture[] characterTextures;

    public Texture backgroundTexture;
    private Texture selectYourCharacterTexture;

    enum ButtonState {
        NORMAL, HOVER, PRESSED
    }

    // ==================== CONSTRUCTOR ====================
    public CharacterSelectionScreen(MazeGame game) {
        this.game = game;
        this.batch = game.getBatch();

        loadAssets();
        characterTextures = new Texture[]{redCharacterSelectedTexture, blueCharacterSelectedTexture, greenCharacterSelectedTexture};
        initializeButtonBounds();


    }

    // ==================== LOAD ASSETS ====================
    private void loadAssets() {
        try {
            // Background texture
            backgroundTexture = new Texture(Gdx.files.internal("background.png"));

            // Select Your Character title image
            try {
                selectYourCharacterTexture = new Texture(Gdx.files.internal("select_your_character/select_character_pixel.png"));
            } catch (Exception e) {
                System.out.println("⚠️  Select Your Character texture not found");
                selectYourCharacterTexture = null;
            }

            redCharacterSelectedTexture = new Texture(Gdx.files.internal("select_your_character/character/line_red_selected.png"));
            blueCharacterSelectedTexture = new Texture(Gdx.files.internal("select_your_character/character/line_blue_selected.png"));
            greenCharacterSelectedTexture = new Texture(Gdx.files.internal("select_your_character/character/line_green_selected.png"));

            // Arrows
            leftArrowTexture = new Texture(Gdx.files.internal("select_your_character/play(2).png"));
            rightArrowTexture = new Texture(Gdx.files.internal("select_your_character/play(2).png"));

            // Buttons
            homeButtonTexture = new Texture(Gdx.files.internal("select_your_character/buttons/home_button.png"));
            homeButtonHoverTexture = new Texture(Gdx.files.internal("select_your_character/buttons/home_button_hover.png"));
            homeButtonPressedTexture = new Texture(Gdx.files.internal("select_your_character/buttons/home_button_pressed.png"));

            playButtonTexture = new Texture(Gdx.files.internal("select_your_character/buttons/button_play.png"));
            playButtonHoverTexture = new Texture(Gdx.files.internal("select_your_character/buttons/button_play_hover.png"));
            playButtonPressedTexture = new Texture(Gdx.files.internal("select_your_character/buttons/button_play_pressed.png"));

            // Create bitmap font - VERY LARGE for title
            titleFont = new BitmapFont();
            titleFont.getData().setScale(4.0f); // MUCH LARGER font for title

            System.out.println("✅ Character assets loaded successfully!");

        } catch (Exception e) {
            System.out.println("❌ ASSET LOADING ERROR: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // ==================== INITIALIZE BUTTON BOUNDS (FULLY RESPONSIVE) ====================
    private void initializeButtonBounds() {
        float screenWidth = Gdx.graphics.getWidth();
        float screenHeight = Gdx.graphics.getHeight();

        // ===== TITLE IMAGE (TOP CENTER) =====
        float titleHeight = screenHeight * 0.4f;  // 40% of screen height
        float titleWidth = titleHeight * 2.1f;      // Maintain aspect ratio (adjust multiplier as needed)
        float titleX = (screenWidth / 2f) - (titleWidth / 2f);
        float titleY = screenHeight - titleHeight - 20;  // Top half, with padding
        selectYourCharacterBounds = new Rectangle(titleX, titleY, titleWidth, titleHeight);


        // ===== SPLIT SCREEN IN HALF =====
        float titleZoneHeight = screenHeight * 0.5f;    // Top 50% for title
        float contentZoneHeight = screenHeight * 0.5f;  // Bottom 50% for content
        float contentZoneY = 0;                         // Start at bottom
        float contentCenterY = contentZoneY + (contentZoneHeight / 2f);

        // ===== CHARACTER DISPLAY (SMALLER, CENTER) =====
        float characterHeight = contentZoneHeight * 0.55f;  // 55% of content zone
        float characterWidth = characterHeight * 0.6f;      // Maintain aspect ratio
        float characterX = (screenWidth / 2f) - (characterWidth / 2f);
        float characterY = contentCenterY - (characterHeight / 2f);

        characterDisplayBounds = new Rectangle(characterX, characterY, characterWidth, characterHeight);

        // ===== ARROWS (SMALLER, LEFT & RIGHT OF CHARACTER) =====
        float arrowHeight = characterHeight * 0.25f;     // 25% of character height (SMALLER)
        float arrowWidth = arrowHeight * 0.8f;           // Maintain aspect ratio
        float arrowPadding = screenWidth * 0.04f;        // Padding from character

        // Left Arrow (LEFT of character)
        float leftArrowX = characterX - arrowPadding - arrowWidth;
        float leftArrowY = characterY + (characterHeight / 2f) - (arrowHeight / 2f);
        leftArrowBounds = new Rectangle(leftArrowX, leftArrowY, arrowWidth, arrowHeight);

        // Right Arrow (RIGHT of character)
        float rightArrowX = characterX + characterWidth + arrowPadding;
        float rightArrowY = characterY + (characterHeight / 2f) - (arrowHeight / 2f);
        rightArrowBounds = new Rectangle(rightArrowX, rightArrowY, arrowWidth, arrowHeight);

        // ===== BUTTONS NEXT TO ARROWS (AT BOTTOM) =====
        float buttonHeight = arrowHeight * 2f;         // Slightly taller than arrows
        float buttonWidth = buttonHeight * 1.2f;        // Maintain aspect ratio
        float buttonPadding = screenWidth * 0.15f;       // Padding from edges
        float buttonY = leftArrowY - (buttonHeight / 2f) + (arrowHeight / 2f); // Align with arrows, bring down a bit

        // Home button at FAR LEFT (next to left arrow)
        float homeButtonX = buttonPadding;
        homeButtonBounds = new Rectangle(homeButtonX, buttonY, buttonWidth, buttonHeight);

        // Play button at FAR RIGHT (next to right arrow)
        float playButtonX = screenWidth - buttonPadding - buttonWidth;
        playButtonBounds = new Rectangle(playButtonX, buttonY, buttonWidth, buttonHeight);

        System.out.println("📍 ===== CHARACTER SELECT LAYOUT =====");
        System.out.println("📍 Title Zone: " + titleZoneHeight + " - " + screenHeight);
        System.out.println("📍 Content Zone: 0 - " + contentZoneHeight);
        System.out.println("📍 Character Height: " + characterHeight);
        System.out.println("📍 Arrow Height: " + arrowHeight);
        System.out.println("📍 Button Height: " + buttonHeight);
        System.out.println("📍 Screen size: " + screenWidth + "x" + screenHeight);
    }

    // ==================== SCREEN INTERFACE METHODS ====================
    @Override
    public void show() {
        setupInputHandler();
    }

    @Override
    public void hide() {
        Gdx.input.setInputProcessor(null);
    }

    @Override
    public void resize(int width, int height) {
        initializeButtonBounds();
    }

    @Override
    public void pause() {}

    @Override
    public void resume() {}


    // ==================== SETUP INPUT HANDLER ====================
    private void setupInputHandler() {
        Gdx.input.setInputProcessor(new InputAdapter() {
            @Override
            public boolean mouseMoved(int screenX, int screenY) {
                float y = Gdx.graphics.getHeight() - screenY;

                leftArrowState = leftArrowBounds.contains(screenX, y) ? ButtonState.HOVER : ButtonState.NORMAL;
                rightArrowState = rightArrowBounds.contains(screenX, y) ? ButtonState.HOVER : ButtonState.NORMAL;
                homeButtonState = homeButtonBounds.contains(screenX, y) ? ButtonState.HOVER : ButtonState.NORMAL;
                playButtonState = playButtonBounds.contains(screenX, y) ? ButtonState.HOVER : ButtonState.NORMAL;

                return false;
            }

            @Override
            public boolean touchDown(int screenX, int screenY, int pointer, int button) {
                float y = Gdx.graphics.getHeight() - screenY;

                if (leftArrowBounds.contains(screenX, y)) {
                    leftArrowState = ButtonState.PRESSED;
                    return true;
                }
                if (rightArrowBounds.contains(screenX, y)) {
                    rightArrowState = ButtonState.PRESSED;
                    return true;
                }
                if (homeButtonBounds.contains(screenX, y)) {
                    homeButtonState = ButtonState.PRESSED;
                    return true;
                }
                if (playButtonBounds.contains(screenX, y)) {
                    playButtonState = ButtonState.PRESSED;
                    return true;
                }

                return false;
            }

            @Override
            public boolean touchUp(int screenX, int screenY, int pointer, int button) {
                float y = Gdx.graphics.getHeight() - screenY;

                if (leftArrowBounds.contains(screenX, y)) {
                    handleLeftArrowPressed();
                }
                if (rightArrowBounds.contains(screenX, y)) {
                    handleRightArrowPressed();
                }
                if (homeButtonBounds.contains(screenX, y)) {
                    handleHomePressed();
                }
                if (playButtonBounds.contains(screenX, y)) {
                    handlePlayPressed();
                }

                leftArrowState = ButtonState.NORMAL;
                rightArrowState = ButtonState.NORMAL;
                homeButtonState = ButtonState.NORMAL;
                playButtonState = ButtonState.NORMAL;

                return false;
            }
        });
    }

    // ==================== RENDER ====================
    @Override
    public void render(float deltaTime) {
        // Clear screen with black background
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        // Draw BACKGROUND


        batch.begin();
        if (backgroundTexture != null) {
            batch.draw(backgroundTexture, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        }
        // ===== DRAW TITLE AT TOP (LARGER, BIGGER) =====

        // Draw SELECT YOUR CHARACTER title image
        if (selectYourCharacterTexture != null) {
            batch.draw(selectYourCharacterTexture, selectYourCharacterBounds.x, selectYourCharacterBounds.y,
                selectYourCharacterBounds.width, selectYourCharacterBounds.height);
        }


        // ===== DRAW HOME BUTTON =====
        drawButton(homeButtonBounds, homeButtonState, homeButtonTexture, homeButtonHoverTexture, homeButtonPressedTexture);

        // ===== DRAW LEFT ARROW =====
        drawArrow(leftArrowBounds, leftArrowState, leftArrowTexture, false);

        // ===== DRAW CHARACTER DISPLAY (CENTER) =====
        if (characterTextures != null && characterTextures[currentCharacterIndex] != null) {
            batch.draw(characterTextures[currentCharacterIndex],
                characterDisplayBounds.x, characterDisplayBounds.y,
                characterDisplayBounds.width, characterDisplayBounds.height);
        }

        // ===== DRAW RIGHT ARROW =====
        drawArrow(rightArrowBounds, rightArrowState, rightArrowTexture, true);

        // ===== DRAW PLAY BUTTON =====
        drawButton(playButtonBounds, playButtonState, playButtonTexture, playButtonHoverTexture, playButtonPressedTexture);

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

    // ==================== DRAW ARROW HELPER ====================
    private void drawArrow(Rectangle bounds, ButtonState state, Texture texture, boolean flipped) {
        if (texture != null) {
            // Apply opacity/brightness based on state
            if (state == ButtonState.HOVER) {
                batch.setColor(1, 1, 1, 0.8f); // Slightly transparent on hover
            } else if (state == ButtonState.PRESSED) {
                batch.setColor(0.7f, 0.7f, 0.7f, 1); // Darker when pressed
            }

            // Draw arrow (flipped for right arrow)
            if (flipped) {
                batch.draw(texture, bounds.x + bounds.width, bounds.y, -bounds.width, bounds.height);
            } else {
                batch.draw(texture, bounds.x, bounds.y, bounds.width, bounds.height);
            }

            batch.setColor(1, 1, 1, 1); // Reset color
        }
    }

    // ==================== BUTTON HANDLERS ====================
    private void handleLeftArrowPressed() {
        currentCharacterIndex = (currentCharacterIndex - 1 + characters.length) % characters.length;
        System.out.println("⬅️ Previous character: " + characters[currentCharacterIndex]);
    }

    private void handleRightArrowPressed() {
        currentCharacterIndex = (currentCharacterIndex + 1) % characters.length;
        System.out.println("➡️ Next character: " + characters[currentCharacterIndex]);
    }

    private void handleHomePressed() {
        System.out.println("✅ HOME button clicked - returning to Main Menu");
        game.setScreen(new MainMenuScreen(game));
    }

    private void handlePlayPressed() {
        System.out.println("✅ PLAY button clicked with " + characters[currentCharacterIndex] + " character!");
        game.setScreen(new GameScreen(game, characters[currentCharacterIndex]));
    }

    // ==================== DISPOSE ====================
    @Override
    public void dispose() {
        Gdx.input.setInputProcessor(null);
        if (redCharacterSelectedTexture != null) redCharacterSelectedTexture.dispose();
        if (blueCharacterSelectedTexture != null) blueCharacterSelectedTexture.dispose();
        if (greenCharacterSelectedTexture != null) greenCharacterSelectedTexture.dispose();

        if (leftArrowTexture != null) leftArrowTexture.dispose();
        if (rightArrowTexture != null) rightArrowTexture.dispose();

        if (homeButtonTexture != null) homeButtonTexture.dispose();
        if (homeButtonHoverTexture != null) homeButtonHoverTexture.dispose();
        if (homeButtonPressedTexture != null) homeButtonPressedTexture.dispose();

        if (playButtonTexture != null) playButtonTexture.dispose();
        if (playButtonHoverTexture != null) playButtonHoverTexture.dispose();
        if (playButtonPressedTexture != null) playButtonPressedTexture.dispose();

        if (titleFont != null) titleFont.dispose();
        if (backgroundTexture != null) backgroundTexture.dispose();

        if (selectYourCharacterTexture != null) selectYourCharacterTexture.dispose();



    }
}

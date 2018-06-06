package com.uaa.rotationfrenzy.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.ArrayMap;
import com.badlogic.gdx.utils.Json;
import com.uaa.rotationfrenzy.RotationFrenzy;
import com.uaa.rotationfrenzy.graph.BasicGraph;
import com.uaa.rotationfrenzy.level.Level;
import com.uaa.rotationfrenzy.screen.ui.BasicMenu;

import sun.management.BaseOperatingSystemImpl;


// This is the main game screen that runs, taking user input
public class GameScreen implements Screen, GestureDetector.GestureListener, InputProcessor {
    private boolean isPaused = false;
    private Level level;
    private final RotationFrenzy game;

    private OrthographicCamera camera;
    private Vector3 touchPoint = new Vector3();

    private BasicGraph chart;

    private boolean userPrompted = false;
    private float angleEntered = 0.0f;

    private BasicMenu gameOverMenu;
    private BasicMenu inputMenu;

    private String levelFilename;


    public GameScreen(final RotationFrenzy inGame, String levelName){
        this.game = inGame;
        this.levelFilename = levelName;
        //this.level = new Level();

        // TODO: Depending on how big the level gets, may need to move this into a loading loop
        // so the player doesn't think the game froze, for not it loads fast so doesn't matter
        Json json = new Json();
        this.level = json.fromJson(Level.class, Gdx.files.internal("levels/" + levelName));
        this.level.buildLevel();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, RotationFrenzy.SCREEN_WIDTH ,RotationFrenzy.SCREEN_HEIGHT);

        setupInput();

        chart = new BasicGraph(new Vector2(RotationFrenzy.SCREEN_WIDTH - 50, 150));
    }

    // This goes with option 1 above, could be pulled out to it's own class
    public class MyTextInputListener implements Input.TextInputListener {
        @Override
        public void input (String text) {
            try {
                angleEntered = Float.parseFloat(text);

                // TODO: Validate data range 0-360, and 0-N radians
                System.out.println("Text entered:" + text);
                level.setUserAngle(angleEntered);

                // If the user did not guess the correct guess, and we have attempts left, continue
                if (level.areAttemptsLeft() && !level.isLevelComplete()){
                    userPrompted = false;
                }

            }catch(NumberFormatException e){
                // TODO: Display message for user that the value was invalid.
                canceled();
            }
        }

        @Override
        public void canceled () {
            userPrompted = false;
        }
    }

    // AngleType is a string to display either "Degrees" or "Radians"
    private void getAngleFromUser(String angleType){
        Texture menuBackground = RotationFrenzy.assetManager.get("textures/simple_tile.png");
        inputMenu = new BasicMenu(menuBackground, "Enter the Angle", new Vector2(Gdx.graphics.getWidth() * (3.0f/4.0f), Gdx.graphics.getHeight() * (3.0f/4.0f)), angleType);
        inputMenu.setLeftButtonText("Ok");
        inputMenu.setRightButtonText("Exit to Menu");
        inputMenu.BuildMenu();

        // Option 1, popup a textbox to request the degrees
        // MyTextInputListener listener = new MyTextInputListener();
        // Gdx.input.getTextInput(listener, "Enter Angle in " + angleType, "", angleType);
        this.userPrompted = true;
    }


    @Override
    public void render(float delta) {
        // We pass the delta, which is the change in time since the last time render was called
        update(delta);
        draw(delta);
    }

    private void update(float delta){
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        if (Gdx.input.isKeyPressed(Input.Keys.P)) {
            this.isPaused = !this.isPaused;
        }

        if (!this.isPaused) {
            this.level.update(delta);
        }

        chart.update(delta, level.getWheelRotationDegrees());
      
        // If the level requires text input, and we have not yet prompted the user, prompt them
        if (level.hasTextualInput() && !userPrompted){
            getAngleFromUser(level.getAngleUnitType());
        }

        if (gameOverMenu != null){
            gameOverMenu.update(delta);
            String whichButton = gameOverMenu.checkButtonPressed();
            if (whichButton.equalsIgnoreCase("left")){
                this.dispose();
                game.setScreen(new GameScreen(game, levelFilename));
            }else if (whichButton.equalsIgnoreCase("right")){
                this.dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        }

        if (inputMenu != null){
            inputMenu.update(delta);
            String whichButton = inputMenu.checkButtonPressed();
            if (whichButton.equalsIgnoreCase("left")){
                String text = inputMenu.getUserInput();

                // Prevent empty string error (Exception in thread "LWJGL Application" java.lang.NumberFormatException: empty String)

                System.out.println("Text entered:" + text);
                try {
                    angleEntered = Float.parseFloat(text);
                    // TODO: Validate data range 0-360, and 0-N radians

                    level.setUserAngle(angleEntered);

                    // If the user did not guess the correct guess, and we have attempts left, continue
                    if (level.areAttemptsLeft() && !level.isLevelComplete()){
                        userPrompted = false;
                    }
                    inputMenu = null;
                }
                catch (NumberFormatException e) {
                    System.err.println("ERROR: Invalid number! " + e.getMessage());

                    // TODO: let user know somehow they entered invalid data
                    // Maybe shake the messagebox and create an audible ding?

                    // Reset so the messagebox will appear again
                    inputMenu = null;
                    userPrompted = false;
                }
            }else if (whichButton.equalsIgnoreCase("right")){
                this.dispose();
                game.setScreen(new MainMenuScreen(game));
            }
        }
    }

    private void draw(float delta){
        Gdx.gl.glClearColor(0.1f, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);

        //if (!this.isPaused) {
            game.batch.begin();
        game.font.setColor(Color.WHITE);
            level.draw(game, delta);
            game.batch.end();

            chart.draw(delta, game, camera);

            if (level.isLevelComplete()) {
                game.batch.begin();
                game.font.setColor(Color.YELLOW);
                game.font.draw(game.batch, "Game Over",
                        RotationFrenzy.SCREEN_WIDTH - 100,
                        RotationFrenzy.SCREEN_HEIGHT - 50,
                        100,
                        Align.left,
                        true);
                game.batch.end();

                buildGameOverMenu();
            }

        if (gameOverMenu != null){
            gameOverMenu.draw();
        }

        if (inputMenu != null){
            inputMenu.draw();
        }
        //}
    }

    private void buildGameOverMenu(){
        if (gameOverMenu == null && level.isLevelFailed()){
            Texture menuBackground = RotationFrenzy.assetManager.get("textures/simple_tile.png");

            ArrayMap<String, String> items = new ArrayMap<String, String>();
            items.put("Demonstration only", "Remove these");
            items.put("Times played", "2");
            items.put("Max Stars", "3");
            items.put("Times Successful", "0");
            items.put("Times Failed", "2");

            gameOverMenu = new BasicMenu(items, menuBackground, "Game Over Screen");
            gameOverMenu.setLeftButtonText("Retry");
            gameOverMenu.setRightButtonText("Exit");
            gameOverMenu.BuildMenu();
        }
    }

    private void setupInput(){
        //Setup all the Input handling
        InputMultiplexer im = new InputMultiplexer();
        GestureDetector gd = new GestureDetector(this);
        im.addProcessor(gd);
        im.addProcessor(this);

        //Set the input processor to the multiplexed
        InputMultiplexer inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(im);

        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    @Override
    public void show() {
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("GameScreen.resize() method called");
    }

    @Override
    public void pause() {
        System.out.println("GameScreen.pause() method called");

        isPaused = true;
    }

    @Override
    public void resume() {
        System.out.println("GameScreen.resume() method called");

        isPaused = false;
    }

    @Override
    public void hide() {
        System.out.println("GameScreen.hide() method called");
    }

    @Override
    public void dispose() {
        //This does not get called on Desktop when the window is closed.  Instead it calls pause() and that is all.
        System.out.println("GameScreen.dispose() method called");
        level.dispose();
    }

    /*
        TOUCH, MOUSE, and KEYBOARD callbacks
     */

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("Touch Down!");

        // Store the touch-down location, so we can calculate the angle difference
        touchPoint.x = screenX;	//only gets input from the first touch
        touchPoint.y = screenY;	//only gets input from the first touch
        touchPoint.z = 0;

        touchPoint = camera.unproject(touchPoint);

        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("Touch Up!");
        if (!level.isLevelComplete())
            this.level.checkForCompletion();
        return false;
    }


    // This appears to be the method we want to use to get the position of the touch/mouse drag
    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        // Convert screen input to camera position
        Vector3 touchPos = new Vector3();
        touchPos.x = screenX;	//only gets input from the first touch
        touchPos.y = screenY;	//only gets input from the first touch
        touchPos.z = 0;

        Vector3 screenPos = camera.unproject(touchPos);

        //System.out.println("Touch Dragging it out! " + touchPos);

        if (this.level.isTouchInput() && !level.isLevelComplete()) {
            this.level.touchDragged(screenPos, pointer, touchPoint);
        }

        // Update the touchPoint so we can get a DELTA
        touchPoint.x = screenX;	//only gets input from the first touch
        touchPoint.y = screenY;	//only gets input from the first touch
        touchPoint.z = 0;
        touchPoint = camera.unproject(touchPoint);
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    // Don't see us using this, it's specific to mousewheel
    @Override
    public boolean scrolled(int amount) {
        System.out.println("Scrolled: 0" + amount);
        return false;
    }

    @Override
    public boolean touchDown(float x, float y, int pointer, int button) {
        System.out.println("Touch Down! again?");
        return false;
    }

    @Override
    public boolean tap(float x, float y, int count, int button) {
        System.out.println("Tab my mana");
        return false;
    }

    @Override
    public boolean longPress(float x, float y) {
        System.out.println("When is this press going to end!");
        return false;
    }

    @Override
    public boolean fling(float velocityX, float velocityY, int button) {
        System.out.println("Fling: It was a short lived relationship. vX:" + velocityX + " vY:" +  velocityY);
        return false;
    }

    @Override
    public boolean pan(float x, float y, float deltaX, float deltaY) {
        //System.out.println("Pan, Peter pan.");
        return false;
    }

    @Override
    public boolean panStop(float x, float y, int pointer, int button) {
        System.out.println("Ok jokes over.");
        return false;
    }

    @Override
    public boolean zoom(float initialDistance, float distance) {
        System.out.println("Mazda!");
        return false;
    }

    @Override
    public boolean pinch(Vector2 initialPointer1, Vector2 initialPointer2, Vector2 pointer1, Vector2 pointer2) {
        System.out.println("Ouch!");
        return false;
    }

    @Override
    public void pinchStop() {
        System.out.println("Thank you");
    }
}

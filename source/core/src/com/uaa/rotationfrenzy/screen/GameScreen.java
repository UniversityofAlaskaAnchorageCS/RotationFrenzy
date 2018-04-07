package com.uaa.rotationfrenzy.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.input.GestureDetector;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Json;
import com.uaa.rotationfrenzy.RotationFrenzy;
import com.uaa.rotationfrenzy.level.Level;

// This is the main game screen that runs, taking user input
public class GameScreen implements Screen, GestureDetector.GestureListener, InputProcessor {
    private boolean isPaused = false;
    private Level level;
    private final RotationFrenzy game;

    private OrthographicCamera camera;

    public GameScreen(final RotationFrenzy inGame){
        this.game = inGame;
        //this.level = new Level();

        Json json = new Json();
        this.level = json.fromJson(Level.class, Gdx.files.internal("levels/level4.json"));
        this.level.buildLevel();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, RotationFrenzy.SCREEN_WIDTH ,RotationFrenzy.SCREEN_HEIGHT);

        setupInput();
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
    }

    private void draw(float delta){
        Gdx.gl.glClearColor(0.1f, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        game.batch.setProjectionMatrix(camera.combined);

        if (!this.isPaused) {
            game.batch.begin();
            level.draw(game, delta);
            game.batch.end();
        }
    }


    private void setupInput(){

        //Setup all the Input handling
        InputMultiplexer im = new InputMultiplexer();
        GestureDetector gd = new GestureDetector(this);
        im.addProcessor(gd);
        im.addProcessor(this);

        //Set the input processor to the multiplexer
        Gdx.input.setInputProcessor(im);
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
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("Touch Up!");
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

        this.level.touchDragged(screenPos, pointer);
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

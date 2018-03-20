package com.uaa.rotationfrenzy.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.utils.Json;
import com.uaa.rotationfrenzy.RotationFrenzy;
import com.uaa.rotationfrenzy.level.Level;

// This is the main game screen that runs, taking user input
public class GameScreen implements Screen {
    private boolean isPaused = false;
    private Level level;
    private final RotationFrenzy game;

    private OrthographicCamera camera;

    public GameScreen(final RotationFrenzy inGame){
        this.game = inGame;
        //this.level = new Level();

        Json json = new Json();
        this.level = json.fromJson(Level.class, Gdx.files.internal("levels/level1.json"));
        this.level.buildLevel();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, RotationFrenzy.SCREEN_WIDTH ,RotationFrenzy.SCREEN_HEIGHT);
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

}

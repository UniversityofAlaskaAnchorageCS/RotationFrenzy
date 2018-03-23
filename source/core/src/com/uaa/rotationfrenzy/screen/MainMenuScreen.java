package com.uaa.rotationfrenzy.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.uaa.rotationfrenzy.RotationFrenzy;

import java.util.ArrayDeque;
import java.util.Queue;

/**
 * Created by thisisme1 on 3/22/2018.
 */

public class MainMenuScreen implements Screen {

    protected final RotationFrenzy game;

    private OrthographicCamera camera;
    private Queue<String> loadingText;

    private boolean assetsLoaded;

    public MainMenuScreen(final RotationFrenzy rotationFrenzy){
        game = rotationFrenzy;

        camera = new OrthographicCamera();
        camera.setToOrtho(false, RotationFrenzy.SCREEN_WIDTH, RotationFrenzy.SCREEN_HEIGHT);
    }

    @Override
    public void show() {
        loadingText = new ArrayDeque<String>();
        loadingText.add(".");
        loadingText.add("..");
        loadingText.add("...");
        loadingText.add("....");
        loadingText.add(".....");
        loadingText.add("......");
        loadingText.add(".......");
    }

    @Override
    public void render(float delta) {
        update(delta);

        if (!assetsLoaded){
            loadAssets(delta);
            loadingText.add(loadingText.remove()); // This will take the first item in the queue, and add it to the end "rotate" the queue
        }
        else {
            // This would be in the menu system instead when we design that
            game.setScreen(new GameScreen(game));
        }

        draw(delta);
    }

    private void update(float delta){
        camera.update();
        game.batch.setProjectionMatrix(camera.combined);
    }

    private void draw(float delta){
        //Clear the screen and draw the background
        Gdx.gl.glClearColor(0, 0, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        game.batch.begin();

        if (!assetsLoaded) {
            game.font.draw(game.batch,
                    "Loading Assets" + loadingText.peek(),
                    RotationFrenzy.SCREEN_WIDTH / 2,
                    RotationFrenzy.SCREEN_HEIGHT / 2);
        }

        game.batch.end();
    }

    private void loadAssets(float delta){

        // Returns "TRUE" if all assets are loaded
        if (RotationFrenzy.assetManager.update()){
            assetsLoaded = true;
            //game.setMusic((Music) Merge.assetManager.get("data/sounds/Thats_a_Wrap.mp3"));
            //game.startMusic(1.0f);
        }
    }

    @Override
    public void resize(int width, int height) {

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

    }
}

package com.uaa.rotationfrenzy.screen;

import com.badlogic.gdx.Screen;
import com.uaa.rotationfrenzy.RotationFrenzy;
import com.uaa.rotationfrenzy.level.Level;

/**
 * Created by thisisme1 on 2/28/2018.
 */

// This is the main game screen that runs, taking user input
public class GameScreen implements Screen {
    private boolean isPaused = false;
    private Level level;
    private final RotationFrenzy game;

    public GameScreen(final RotationFrenzy inGame){
        this.game = inGame;
    }

    @Override
    public void render(float delta) {
        // We pass the delta, which is the change in time since the last time render was called
        update(delta);
        draw(delta);
    }

    private void update(float delta){

    }

    private void draw(float delta){

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

        isPaused = true;   //Start the game paused
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

package com.uaa.rotationfrenzy;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uaa.rotationfrenzy.screen.GameScreen;

// Main enterance into our game
// Starts the GameScreen which will do all the game interaction
// Game is a nice class that allows us to call setScreen and move between screens easily.
public class RotationFrenzy extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public static final float SCREEN_HEIGHT = 600;
	public static final float SCREEN_WIDTH = 800;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();

		setScreen(new GameScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
		batch.dispose();
	}
}

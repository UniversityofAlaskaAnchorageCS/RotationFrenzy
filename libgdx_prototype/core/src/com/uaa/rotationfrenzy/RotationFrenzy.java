package com.uaa.rotationfrenzy;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.uaa.rotationfrenzy.screen.GameScreen;

// Main enterance into our game
// Starts the GameScreen which will do all the game interaction
// Game is a nice class that allows us to call setScreen and move between screens easily.
public class RotationFrenzy extends Game {
	public SpriteBatch batch;
	
	@Override
	public void create () {
		batch = new SpriteBatch();

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

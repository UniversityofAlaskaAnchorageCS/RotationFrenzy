package com.uaa.rotationfrenzy;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Json;
import com.uaa.rotationfrenzy.level.AssetProperties;
import com.uaa.rotationfrenzy.screen.GameScreen;
import com.uaa.rotationfrenzy.screen.MainMenuScreen;

// Main enterance into our game
// Starts the GameScreen which will do all the game interaction
// Game is a nice class that allows us to call setScreen and move between screens easily.
public class RotationFrenzy extends Game {
	public SpriteBatch batch;
	public BitmapFont font;
	public static final float SCREEN_HEIGHT = 600;
	public static final float SCREEN_WIDTH = 800;
	public static AssetManager assetManager;
	public static AssetProperties assetProperties;
	
	@Override
	public void create () {
		batch = new SpriteBatch();
		font = new BitmapFont();

		assetManager = new AssetManager();
		Json json = new Json();
		assetProperties = (AssetProperties) json.fromJson(AssetProperties.class, Gdx.files.internal("levels/asset_properties.json"));

		//Queue up all the Sound effects to be loaded later
		for(String s: assetProperties.getSoundNames()){
			assetManager.load(s, Sound.class);
		}

		//Queue up all the Music to be loaded later
		for(String m: assetProperties.getMusicNames()){
			assetManager.load(m, Music.class);
		}

		//Queue up all the Textures to be loaded later
		for(String t: assetProperties.getTextureNames()){
			assetManager.load(t, Texture.class);
		}

		setScreen(new MainMenuScreen(this));
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

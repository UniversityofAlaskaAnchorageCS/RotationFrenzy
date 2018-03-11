package com.uaa.rotationfrenzy.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;
import com.uaa.rotationfrenzy.RotationFrenzy;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.title = "Rotation Frenzy";
		//if these are set then the images are distorted
		config.width = (int)RotationFrenzy.SCREEN_WIDTH;
		config.height = (int)RotationFrenzy.SCREEN_HEIGHT;
		new LwjglApplication(new RotationFrenzy(), config);
	}
}

package nz.co.canadia.gameoffjam2017.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import nz.co.canadia.gameoffjam2017.Constants;
import nz.co.canadia.gameoffjam2017.GameOffJam;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Constants.DESKTOP_WIDTH;
		config.height = Constants.DESKTOP_HEIGHT;
		config.title = Constants.GAME_NAME;
		new LwjglApplication(new GameOffJam(), config);
	}
}

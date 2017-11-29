package nz.co.canadia.coolsodacan.desktop;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

import nz.co.canadia.coolsodacan.Constants;
import nz.co.canadia.coolsodacan.CoolSodaCan;

public class DesktopLauncher {
	public static void main (String[] arg) {
		LwjglApplicationConfiguration config = new LwjglApplicationConfiguration();
		config.width = Constants.DESKTOP_WIDTH;
		config.height = Constants.DESKTOP_HEIGHT;
		config.title = Constants.GAME_NAME;
		new LwjglApplication(new CoolSodaCan(), config);
	}
}

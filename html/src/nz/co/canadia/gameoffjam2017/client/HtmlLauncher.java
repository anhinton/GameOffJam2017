package nz.co.canadia.gameoffjam2017.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

import nz.co.canadia.gameoffjam2017.Constants;
import nz.co.canadia.gameoffjam2017.GameOffJam;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(
                        Constants.HTML_WIDTH,
                        Constants.HTML_HEIGHT);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new GameOffJam();
        }
}
package nz.co.canadia.coolsodacan.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

import nz.co.canadia.coolsodacan.Constants;
import nz.co.canadia.coolsodacan.CoolSodaCan;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                return new GwtApplicationConfiguration(
                        Constants.HTML_WIDTH,
                        Constants.HTML_HEIGHT);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new CoolSodaCan();
        }
}
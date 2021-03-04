package nz.co.canadia.coolsodacan.client;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.backends.gwt.GwtApplication;
import com.badlogic.gdx.backends.gwt.GwtApplicationConfiguration;

import nz.co.canadia.coolsodacan.Constants;
import nz.co.canadia.coolsodacan.CoolSodaCan;

public class HtmlLauncher extends GwtApplication {

        @Override
        public GwtApplicationConfiguration getConfig () {
                // Resizable application, uses available space in browser
//                return new GwtApplicationConfiguration(true);
                // Fixed size application:
                return new GwtApplicationConfiguration(Constants.HTML_WIDTH, Constants.HTML_HEIGHT);
        }

        @Override
        public ApplicationListener createApplicationListener () {
                return new CoolSodaCan(new HtmlFontLoader(), new HtmlFormatter());
        }
}
package nz.co.canadia.coolsodacan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.assets.AssetManager;
import com.badlogic.gdx.assets.loaders.FileHandleResolver;
import com.badlogic.gdx.assets.loaders.resolvers.InternalFileHandleResolver;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGenerator;
import com.badlogic.gdx.graphics.g2d.freetype.FreeTypeFontGeneratorLoader;
import com.badlogic.gdx.graphics.g2d.freetype.FreetypeFontLoader;
import com.badlogic.gdx.math.MathUtils;

public class AndroidFontLoader implements FontLoader {
    @Override
    public void loadGameUiFont(AssetManager manager) {
        FileHandleResolver resolver = new InternalFileHandleResolver();
        manager.setLoader(FreeTypeFontGenerator.class, new FreeTypeFontGeneratorLoader(resolver));
        manager.setLoader(BitmapFont.class, ".ttf", new FreetypeFontLoader(resolver));

        FreetypeFontLoader.FreeTypeFontLoaderParameter gameUiFont = new FreetypeFontLoader.FreeTypeFontLoaderParameter();
        gameUiFont.fontFileName = "fonts/Podkova-VariableFont_wght.ttf";
        gameUiFont.fontParameters.size = MathUtils.round((float) Constants.GAMEUI_FONT_SIZE / Constants.GAME_HEIGHT * Gdx.graphics.getBackBufferHeight());
        gameUiFont.fontParameters.color = Constants.FONT_COLOR;
        gameUiFont.fontParameters.shadowColor = Constants.FONT_SHADOW_COLOR;
        gameUiFont.fontParameters.shadowOffsetX = MathUtils.round((float) Constants.FONT_SHADOW_OFFSET / Constants.GAME_HEIGHT * Gdx.graphics.getBackBufferHeight());
        gameUiFont.fontParameters.shadowOffsetY = MathUtils.round((float) Constants.FONT_SHADOW_OFFSET / Constants.GAME_HEIGHT * Gdx.graphics.getBackBufferHeight());
        manager.load("fonts/Podkova-VariableFont_wght.ttf", BitmapFont.class, gameUiFont);
    }
}

package nz.co.canadia.gameoffjam2017;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * The player object
 */

class Player {
    private Texture bitmap;
    private Sprite sprite;

    Player() {
        bitmap = new Texture(Gdx.files.internal("gpig.png"));
        bitmap.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite = new Sprite(new TextureRegion(
                bitmap, 0,0, bitmap.getWidth(), bitmap.getHeight()));
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        sprite.setPosition(0, Constants.CANVAS_HEIGHT / 2);
        sprite.setSize(1, 1);
    }

    void update(Viewport viewport) {

    }

    void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    void dispose() {
        bitmap.dispose();
    }
}

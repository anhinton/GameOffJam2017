package nz.co.canadia.gameoffjam2017;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * The player object
 */

class Player {
    private Texture bitmap;
    private Sprite sprite;
    private Vector2 xy;

    Player() {
        xy = new Vector2(0, Constants.CANVAS_HEIGHT / 2);
        bitmap = new Texture(Gdx.files.internal("gpig.png"));
        bitmap.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite = new Sprite(new TextureRegion(
                bitmap, 0,0, bitmap.getWidth(), bitmap.getHeight()));
        sprite.setOrigin(sprite.getWidth() / 2, sprite.getHeight() / 2);
        sprite.setPosition(xy.x, xy.y);
        sprite.setSize(1, 1);
    }

    void update(Viewport viewport) {
        sprite.setPosition(xy.x, xy.y);
    }

    void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    void dispose() {
        bitmap.dispose();
    }

    void move(int x, int y, Viewport viewport) {
        xy = viewport.unproject(new Vector2(x, y));
    }
}

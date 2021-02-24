package nz.co.canadia.coolsodacan;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.Viewport;

/**
 * The player object
 */

class Player {
    private final Sprite sprite;
    private final int gameHeight;
    private Vector2 targetXY;

    Player(int gameHeight, TextureAtlas atlas) {
        this.gameHeight = gameHeight;
        targetXY = new Vector2(Constants.GAME_WIDTH / 2f, 0);
        sprite = atlas.createSprite("blue_soda_small");
        sprite.setPosition(targetXY.x - sprite.getWidth() / 2, targetXY.y);
        sprite.setSize(sprite.getWidth(), sprite.getHeight());
    }

    void update() {
        move();
//        Gdx.app.log("Player", "X: " + String.valueOf(sprite.getX()) + " Y: " + String.valueOf(sprite.getY()));
        clamp();
    }

    void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    private void clamp() {
        if (sprite.getX() < 0) {
            sprite.setX(0);
        }
        if (sprite.getX() + sprite.getWidth() > Constants.GAME_WIDTH) {
            sprite.setX(Constants.GAME_WIDTH - sprite.getWidth());
        }
        if (sprite.getY() < 0) {
            sprite.setY(0);
        }
        if (sprite.getY() + sprite.getHeight() > gameHeight) {
            sprite.setY(gameHeight - sprite.getHeight());
        }
    }

    private void move() {
        // calculate change in X and Y positions
        float deltaX = targetXY.x - sprite.getX() - sprite.getWidth() / 2;
        float deltaY = targetXY.y - sprite.getY();
        float length = (float) Math.sqrt(deltaX * deltaX + deltaY * deltaY);
        if (length > Constants.PLAYER_MOVEMENT_THRESHOLD) {
            // if the distance to move is large enough move towards the target
            float changeX = deltaX / length;
            float changeY = deltaY / length;
            sprite.setX(sprite.getX() + changeX * Constants.PLAYER_SPEED * Gdx.graphics.getDeltaTime());
            sprite.setY(sprite.getY() + changeY * Constants.PLAYER_SPEED * Gdx.graphics.getDeltaTime());
        } else {
            // if it's a small distance just go straight there
            sprite.setX(targetXY.x - sprite.getWidth() / 2);
            sprite.setY(targetXY.y);
        }
    }

    void setTargetXY(int x, int y, Viewport viewport) {
        targetXY = viewport.unproject(new Vector2(x, y));
        if (Gdx.app.getType() == Application.ApplicationType.Android | Gdx.app.getType() == Application.ApplicationType.iOS) {
            targetXY.y += sprite.getHeight();
        }
    }
}

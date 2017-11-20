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
    private Vector2 targetXY;

    Player() {
        targetXY = new Vector2(Constants.CANVAS_WIDTH / 2, 0);
        bitmap = new Texture(Gdx.files.internal("PlayerCan.png"));
        bitmap.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        sprite = new Sprite(new TextureRegion(
                bitmap, 0,0, bitmap.getWidth(), bitmap.getHeight()));
        sprite.setPosition(targetXY.x - Constants.PLAYER_WIDTH / 2, targetXY.y);
        sprite.setSize(Constants.PLAYER_WIDTH, Constants.PLAYER_HEIGHT);
    }

    void update(Viewport viewport) {
        move();
        clamp();
    }

    void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    void dispose() {
        bitmap.dispose();
    }

    private void clamp() {
        if (sprite.getX() < Constants.GAME_LEFT) {
            sprite.setX(Constants.GAME_LEFT);
        }
        if (sprite.getX() + Constants.PLAYER_WIDTH > Constants.GAME_RIGHT) {
            sprite.setX(Constants.GAME_RIGHT - Constants.PLAYER_WIDTH);
        }
        if (sprite.getY() < Constants.GAME_BOTTOM) {
            sprite.setY(Constants.GAME_BOTTOM);
        }
        if (sprite.getY() + Constants.PLAYER_HEIGHT > Constants.GAME_TOP) {
            sprite.setY(Constants.GAME_TOP - Constants.PLAYER_HEIGHT);
        }
    }

    private void move() {
        // calculate change in X and Y positions
        float deltaX = targetXY.x - sprite.getX() - Constants.PLAYER_WIDTH / 2;
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
            sprite.setX(targetXY.x - Constants.PLAYER_WIDTH / 2);
            sprite.setY(targetXY.y);
        }
    }

    void setTargetXY(int x, int y, Viewport viewport) {
        targetXY = viewport.unproject(new Vector2(x, y));
    }
}

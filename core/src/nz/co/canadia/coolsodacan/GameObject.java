package nz.co.canadia.coolsodacan;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public interface GameObject {
    void update(float delta);

    void draw(SpriteBatch batch);
}

package nz.co.canadia.coolsodacan;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

public class Grass implements GameObject {
    private final Sprite sprite;

    Grass(int y, TextureAtlas atlas) {
        Array<String> grassArray = new Array<>(4);
        grassArray.add("grass01");
        grassArray.add("grass02");
        grassArray.add("grass03");
        grassArray.add("grass04");
        sprite = atlas.createSprite(grassArray.random());
        sprite.setCenterX(MathUtils.random(0, Constants.GAME_WIDTH));
        sprite.setY(y);
    }

    @Override
    public void update(float delta) {
        sprite.setY(sprite.getY() - Constants.OBJECT_MOVEMENT_SPEED * delta);
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }
}

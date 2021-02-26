package nz.co.canadia.coolsodacan;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Array;

import java.util.Comparator;

@SuppressWarnings("NullableProblems")
public class Animal implements GameObject, Comparable<GameObject>, Comparator<GameObject> {
    private final Sprite sprite;

    Animal(int y, TextureAtlas atlas) {
        Array<String> animalNameArray = new Array<>();
        animalNameArray.add("coco");
        animalNameArray.add("horse01");
        animalNameArray.add("horse02");
        sprite = atlas.createSprite(animalNameArray.random());
        sprite.flip(MathUtils.randomBoolean(), false);
        sprite.setCenterX(MathUtils.random(0, Constants.GAME_WIDTH));
        sprite.setY(y);
    }

    @Override
    public void update(float delta) {
        sprite.setY(sprite.getY() - Constants.WORLD_MOVEMENT_SPEED * delta);
    }

    public void draw(SpriteBatch batch) {
        sprite.draw(batch);
    }

    @Override
    public float getY() {
        return sprite.getY();
    }

    @Override
    public float getTopY() {
         return getY() + sprite.getHeight();
    }

    @Override
    public int compare(GameObject o1, GameObject o2) {
        float diff = o1.getY() - o2.getY();
        if (diff == 0) {
            return 0;
        } else if (diff > 0) {
            return 1;
        } else {
            return -1;
        }
    }

    @Override
    public int compareTo(GameObject o) {
        return compare(this, o);
    }
}
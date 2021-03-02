package nz.co.canadia.coolsodacan;

import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.util.Comparator;

@SuppressWarnings("NullableProblems")
public class Animal implements GameObject, Hittable, Comparable<GameObject>, Comparator<GameObject> {
    private final Sprite sprite;
    private float rot;

    Animal(int y, TextureAtlas atlas) {
        Array<String> animalNameArray = new Array<>();
        animalNameArray.add("coco");
        animalNameArray.add("horse01");
        animalNameArray.add("horse02");
        sprite = atlas.createSprite(animalNameArray.random());
        sprite.flip(MathUtils.randomBoolean(), false);
        sprite.setCenterX(MathUtils.random(0 + sprite.getWidth() / 2, Constants.GAME_WIDTH - sprite.getWidth() / 2));
        sprite.setY(y);
        rot = MathUtils.random(0, 360f);
        wiggle(0);
    }

    // Wiggle!
    void wiggle(float delta) {
        rot = (rot + delta * Constants.DEGREES_PER_SECOND) % 360;
        float shake = MathUtils.sin(rot) * Constants.SHAKE_AMPLITUDE_IN_DEGREES;
        sprite.setRotation(shake);
    }

    @Override
    public void update(float delta) {
        wiggle(delta);
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

    @Override
    public Rectangle getHitBox() {
        return new Rectangle(
                sprite.getX(),
                sprite.getY() + sprite.getHeight() / 2,
                sprite.getWidth(),
                sprite.getHeight() / 2);
    }

    @Override
    public void hit() {

    }

    @Override
    public boolean isHittable() {
        return false;
    }
}

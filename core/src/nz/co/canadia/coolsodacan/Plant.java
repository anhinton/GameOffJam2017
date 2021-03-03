package nz.co.canadia.coolsodacan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.util.Comparator;

@SuppressWarnings("NullableProblems")
public class Plant implements GameObject, Hittable, Comparable<GameObject>, Comparator<GameObject> {
    private final Sprite sprite;
    private final ParticleEffect explosion;
    private Constants.HittableState hitState;
    private int hitCount;

    Plant(int y, TextureAtlas atlas) {
        hitCount = 0;
        hitState = Constants.HittableState.NORMAL;
        Array<String> plantNameArray = new Array<>();
        plantNameArray.add("tree01");
        plantNameArray.add("tree02");
        plantNameArray.add("fern01");
        plantNameArray.add("flower01");
        String plantName = plantNameArray.random();
        sprite = atlas.createSprite(plantName);
        sprite.flip(MathUtils.randomBoolean(), false);
        sprite.setCenterX(MathUtils.random(0, Constants.GAME_WIDTH));
        sprite.setY(y);

        explosion = new ParticleEffect();
        explosion.load(Gdx.files.internal("particleEffects/explosion.p"), atlas);
        // Set explosion dimensions to sprite size
        explosion.getEmitters().first().getSpawnWidth().setHigh(sprite.getWidth());
        explosion.getEmitters().first().getSpawnHeight().setHigh(sprite.getHeight());
        // Increase scale of particle to match sprite (THIS IS SO BAD)
        explosion.getEmitters().first().getXScale().setHigh(Math.min(sprite.getWidth(), sprite.getHeight()));
    }

    @Override
    public void update(float delta) {
        sprite.setY(sprite.getY() - Constants.WORLD_MOVEMENT_SPEED * delta);
        explosion.setPosition(sprite.getX() + sprite.getWidth() / 2, sprite.getY() + sprite.getHeight() / 2);
        explosion.update(delta);
    }

    public void draw(SpriteBatch batch) {
        if (hitState == Constants.HittableState.NORMAL) {
            sprite.draw(batch);
        } else {
            explosion.draw(batch);
        }
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
        hitCount += 1;
        if (hitCount >= 3) {
            hitState = Constants.HittableState.HIT;
            explosion.start();
        }
    }

    @Override
    public boolean isHittable() {
        return hitState == Constants.HittableState.NORMAL;
    }
}

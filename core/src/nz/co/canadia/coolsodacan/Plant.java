package nz.co.canadia.coolsodacan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;

import java.util.Comparator;

@SuppressWarnings("NullableProblems")
public class Plant implements GameObject, Hittable, Comparable<GameObject>, Comparator<GameObject> {
    private Sprite normalSprite;
    private Sprite hitSprite;
    private Sprite currentSprite;
    private final ParticleEffect explosion;
    private Constants.HittableState hitState;
    private int hitCount;

    Plant(int y, TextureAtlas atlas) {
        hitCount = 0;
        hitState = Constants.HittableState.NORMAL;

        Constants.PlantName plantName =
                Constants.PlantName.values()[
                        MathUtils.random(Constants.PlantName.values().length - 1)];
        switch(plantName) {
            case FERN01:
                normalSprite = atlas.createSprite(Constants.FERN01_TEXTURE);
                hitSprite = atlas.createSprite(Constants.FERN01_HIT_TEXTURE);
                break;
            case FLOWER01:
                normalSprite = atlas.createSprite(Constants.FLOWER01_TEXTURE);
                hitSprite = atlas.createSprite(Constants.FLOWER01_HIT_TEXTURE);
                break;
            case TREE01:
                normalSprite = atlas.createSprite(Constants.TREE01_TEXTURE);
                hitSprite = atlas.createSprite(Constants.TREE01_HIT_TEXTURE);
                break;
            case TREE02:
                normalSprite = atlas.createSprite(Constants.TREE02_TEXTURE);
                hitSprite = atlas.createSprite(Constants.TREE02_HIT_TEXTURE);
                break;
        }

        boolean flipSprite = MathUtils.randomBoolean();
        normalSprite.flip(flipSprite, false);
        hitSprite.flip(flipSprite, false);

        currentSprite = normalSprite;
        currentSprite.setCenterX(MathUtils.random(0, Constants.GAME_WIDTH));
        currentSprite.setY(y);

        explosion = new ParticleEffect();
        explosion.load(Gdx.files.internal("particleEffects/explosion.p"), atlas);
        // Set explosion dimensions to sprite size
        explosion.getEmitters().first().getSpawnWidth().setHigh(currentSprite.getWidth());
        explosion.getEmitters().first().getSpawnHeight().setHigh(currentSprite.getHeight());
        // Increase scale of particle to match sprite (THIS IS SO BAD)
        explosion.getEmitters().first().getXScale().setHigh(
                Math.min(currentSprite.getWidth(), currentSprite.getHeight()) * Constants.PLANT_PARTICLE_SCALE);
    }

    @Override
    public void update(float delta) {
        currentSprite.setY(currentSprite.getY() - Constants.WORLD_MOVEMENT_SPEED * delta);
        explosion.setPosition(currentSprite.getX() + currentSprite.getWidth() / 2, currentSprite.getY() + currentSprite.getHeight() / 2);
        explosion.update(delta);
    }

    public void draw(SpriteBatch batch) {
        currentSprite.draw(batch);
        if (hitState == Constants.HittableState.HIT) {
            explosion.draw(batch);
        }
    }

    @Override
    public float getY() {
        return currentSprite.getY();
    }

    @Override
    public float getTopY() {
         return getY() + currentSprite.getHeight();
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
                currentSprite.getX(),
                currentSprite.getY() + currentSprite.getHeight() / 2,
                currentSprite.getWidth(),
                currentSprite.getHeight() / 2);
    }

    @Override
    public void hit() {
        hitCount += 1;
        if (hitCount >= 3) {
            hitSprite.setPosition(currentSprite.getX(), currentSprite.getY());
            currentSprite = hitSprite;
            hitState = Constants.HittableState.HIT;
            explosion.start();
        }
    }

    @Override
    public boolean isHittable() {
        return hitState == Constants.HittableState.NORMAL;
    }
}

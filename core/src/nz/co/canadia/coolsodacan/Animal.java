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
public class Animal implements GameObject, Hittable, Comparable<GameObject>, Comparator<GameObject> {
    private Sprite normalSprite;
    private Sprite superhitSprite;
    private final ParticleEffect explosion;
    private float rot;
    private int hitCount;
    private State hitState;
    private Sprite currentSprite;

    Animal(int y, TextureAtlas atlas) {
        hitCount = 0;
        hitState = State.NORMAL;

        Constants.AnimalName animalName = Constants.AnimalName.values()[MathUtils.random(Constants.AnimalName.values().length - 1)];
        switch (animalName) {
            case COCO:
                normalSprite = atlas.createSprite(Constants.COCO_TEXTURE);
                superhitSprite = atlas.createSprite(Constants.COCO_SUPERHIT_TEXTURE);
                break;
            case HORSE01:
                normalSprite = atlas.createSprite(Constants.HORSE01_TEXTURE);
                superhitSprite = atlas.createSprite(Constants.HORSE01_SUPERHIT_TEXTURE);
                break;
            case HORSE02:
                normalSprite = atlas.createSprite(Constants.HORSE02_TEXTURE);
                superhitSprite = atlas.createSprite(Constants.HORSE02_SUPERHIT_TEXTURE);
                break;
        }
        boolean flipSprite = MathUtils.randomBoolean();
        normalSprite.flip(flipSprite, false);
        superhitSprite.flip(flipSprite, false);

        currentSprite = normalSprite;
        currentSprite.setCenterX(MathUtils.random(0 + normalSprite.getWidth() / 2, Constants.GAME_WIDTH - normalSprite.getWidth() / 2));
        currentSprite.setY(y);
        rot = MathUtils.random(0, 360f);
        wiggle(0);

        explosion = new ParticleEffect();
        explosion.load(Gdx.files.internal("particleEffects/explosion.p"), atlas);
        // Set explosion dimensions to sprite size
        explosion.getEmitters().first().getSpawnWidth().setHigh(currentSprite.getWidth());
        explosion.getEmitters().first().getSpawnHeight().setHigh(currentSprite.getHeight());
        // Increase scale of particle to match half sprite size (not so big as Plants)
        explosion.getEmitters().first().getXScale().setHigh(
                Math.min(currentSprite.getWidth(), currentSprite.getHeight()) * Constants.ANIMAL_PARTICLE_SCALE);
    }

    // Wiggle!
    void wiggle(float delta) {
        rot = (rot + delta * Constants.DEGREES_PER_SECOND) % 360;
        float shake = MathUtils.sin(rot) * Constants.SHAKE_AMPLITUDE_IN_DEGREES;
        currentSprite.setRotation(shake);
    }

    @Override
    public void update(float delta) {
        if (hitState != State.SUPER_HIT) {
            wiggle(delta);
        } else {
            explosion.update(delta);
        }
        currentSprite.setY(currentSprite.getY() - Constants.WORLD_MOVEMENT_SPEED * delta);
        superhitSprite.setPosition(currentSprite.getX(), currentSprite.getY());
        explosion.setPosition(currentSprite.getX() + currentSprite.getWidth() / 2, currentSprite.getY() + currentSprite.getHeight() / 2);
    }

    public void draw(SpriteBatch batch) {
        currentSprite.draw(batch);
        if (hitState == State.HIT) {
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
        if (hitCount < 3) {
            hitState = State.HIT;
            currentSprite.flip(true, false);
        } else if (hitCount == 3) {
            currentSprite = superhitSprite;
            currentSprite.flip(false, true);
            hitState = State.SUPER_HIT;
            explosion.start();
        }

    }

    @Override
    public boolean isHittable() {
        return hitState == State.NORMAL | hitState == State.HIT;
    }

    @Override
    public int getSodasDrunk() {
        return Constants.ANIMAL_SODAS_DRUNK;
    }

    @Override
    public int getScore() {
        if (hitCount < 2) {
            return Constants.ANIMAL_BASE_SCORE;
        } else {
            return Constants.ANIMAL_HIGH_SCORE;
        }
    }
}

package nz.co.canadia.coolsodacan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.ParticleEffect;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class AnimatedCan {

    private final Animation<TextureRegion> animation;
    private final ParticleEffect explosion;
    private float timeElapsed;
    private Constants.AnimatedCanState canState;
    private TextureRegion currentFrame;
    private float x;
    private float y;

    public AnimatedCan(Player player, TextureAtlas atlas) throws IllegalStateException {
        timeElapsed = 0;
        canState = Constants.AnimatedCanState.ACTIVE;
        String animationName;
        switch(player.getName()) {
            case "blue_soda_small":
                animationName = "blue_anim";
                break;
            case "orange_soda_small":
                animationName = "orange_anim";
                break;
            case "purple_soda_small":
                animationName = "purple_anim";
                break;
            case "silver_soda_small":
                animationName = "silver_anim";
                break;
            case "yellow_soda_small":
                animationName = "yellow_anim";
                break;
            default:
                throw new IllegalStateException("Unexpected animationName value: " + player.getName());
        }
        animation = new Animation<TextureRegion>(
                Constants.CAN_FRAME_DURATION,
                atlas.findRegions(animationName),
                Animation.PlayMode.LOOP);
        currentFrame = animation.getKeyFrames()[0];
        x = player.getAnimationX() - currentFrame.getRegionWidth() / 2f;
        y = player.getAnimationY();

        explosion = new ParticleEffect();
        explosion.load(Gdx.files.internal("particleEffects/explosion.p"), atlas);
    }

    void update(float delta) {
        timeElapsed += delta;
        if (isActive()) {
            y += Constants.ANIMATED_CAN_SPEED * delta;
        } else {
            y -= Constants.WORLD_MOVEMENT_SPEED * delta;
        }
        explosion.update(delta);
    }

    void draw(SpriteBatch batch) {
        if (isActive()) {
            currentFrame = animation.getKeyFrame(timeElapsed, true);
            batch.draw(currentFrame, x, y);
        } else {
            explosion.setPosition(x, y);
            explosion.draw(batch);
        }
    }

    public float getY() {
        return y;
    }

    public Rectangle getHitBox() {
        return new Rectangle(x, y, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
    }

    public void hit() {
        canState = Constants.AnimatedCanState.INACTIVE;
        explosion.start();
    }

    public boolean isActive() {
        return canState == Constants.AnimatedCanState.ACTIVE;
    }
}

package nz.co.canadia.coolsodacan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
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
        Color particleColor;
        switch(player.getName()) {
            case BLUE:
                animationName = Constants.BLUE_ANIM_NAME;
                particleColor = Constants.BLUE_EXPLOSION;
                break;
            case ORANGE:
                animationName = Constants.ORANGE_ANIM_NAME;
                particleColor = Constants.ORANGE_EXPLOSION;
                break;
            case PURPLE:
                animationName = Constants.PURPLE_ANIM_NAME;
                particleColor = Constants.PURPLE_EXPLOSION;
                break;
            case SILVER:
                animationName = Constants.SILVER_ANIM_NAME;
                particleColor = Constants.SILVER_EXPLOSION;
                break;
            case YELLOW:
                animationName = Constants.YELLOW_ANIM_NAME;
                particleColor = Constants.YELLOW_EXPLOSION;
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
        // Set tint of particle effect
        float[] tint = new float[3];
        tint[0] = particleColor.r;
        tint[1] = particleColor.g;
        tint[2] = particleColor.b;
        explosion.getEmitters().first().getTint().setColors(tint);
        explosion.setPosition(x, y);
    }

    void update(float delta) {
        timeElapsed += delta;
        if (isActive()) {
            y += Constants.ANIMATED_CAN_SPEED * delta;
        } else {
            y -= Constants.WORLD_MOVEMENT_SPEED * delta;
        }
        currentFrame = animation.getKeyFrame(timeElapsed, true);
        explosion.update(delta);
        explosion.setPosition(x, y);
    }

    void draw(SpriteBatch batch) {
        if (isActive()) {
            batch.draw(currentFrame, x, y);
        } else {
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

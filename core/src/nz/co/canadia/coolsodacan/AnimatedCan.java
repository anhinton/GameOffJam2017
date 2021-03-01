package nz.co.canadia.coolsodacan;

import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;

public class AnimatedCan {

    private final Animation<TextureRegion> animation;
    private TextureRegion currentFrame;
    private float x;
    private float y;

    public AnimatedCan(Player player, TextureAtlas atlas) {
        String animationName;
        switch(player.getName()) {
            case "blue_soda_small":
            default:
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
        }
        animation = new Animation<TextureRegion>(
                Constants.CAN_FRAME_DURATION,
                atlas.findRegions(animationName),
                Animation.PlayMode.LOOP);
        currentFrame = animation.getKeyFrames()[0];
        x = player.getAnimationX() - currentFrame.getRegionWidth() / 2f;
        y = player.getAnimationY() - currentFrame.getRegionHeight();
    }

    void update(float delta) {
        y += Constants.ANIMATED_CAN_SPEED * delta;
    }

    void draw(SpriteBatch batch, float timeElapsed) {
        currentFrame = animation.getKeyFrame(timeElapsed, true);
        batch.draw(currentFrame, x, y);
    }

    public float getY() {
        return y;
    }

    public Rectangle getHitBox() {
        return new Rectangle(x, y, currentFrame.getRegionWidth(), currentFrame.getRegionHeight());
    }
}

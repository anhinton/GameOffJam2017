package nz.co.canadia.coolsodacan;

import com.badlogic.gdx.math.Rectangle;

public interface Hittable {
    Rectangle getHitBox();

    void hit();

    boolean isHittable();
}

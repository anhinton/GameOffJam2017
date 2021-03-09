package nz.co.canadia.coolsodacan;

import com.badlogic.gdx.math.Rectangle;

public interface Hittable {
    enum State { NORMAL, HIT, SUPER_HIT }

    Rectangle getHitBox();

    State getHitState();

    void hit();

    boolean isHittable();

    int getSodasDrunk();

    int getPoints();

    String getType();
}

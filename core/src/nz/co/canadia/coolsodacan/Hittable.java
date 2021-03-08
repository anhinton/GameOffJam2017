package nz.co.canadia.coolsodacan;

import com.badlogic.gdx.math.Rectangle;

public interface Hittable {
    enum State { NORMAL, HIT, SUPER_HIT }

    Rectangle getHitBox();

    void hit();

    boolean isHittable();

    int getSodasDrunk();

    int getScore();
}

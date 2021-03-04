package nz.co.canadia.coolsodacan;

import java.text.NumberFormat;

public class IOSFormatter implements Formatter {
    @Override
    public String printScore(int score) {
        return NumberFormat.getIntegerInstance().format(score);
    }
}

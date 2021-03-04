package nz.co.canadia.coolsodacan.desktop;

import java.text.NumberFormat;

import nz.co.canadia.coolsodacan.Formatter;

public class DesktopFormatter implements Formatter {
    @Override
    public String printScore(int score) {
        return NumberFormat.getIntegerInstance().format(score);
    }
}

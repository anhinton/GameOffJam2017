package nz.co.canadia.coolsodacan.client;

import com.google.gwt.i18n.client.NumberFormat;

import nz.co.canadia.coolsodacan.Formatter;

public class HtmlFormatter implements Formatter {
    @Override
    public String printScore(int score) {
        return NumberFormat.getDecimalFormat().format(score);
    }
}

package nz.co.canadia.coolsodacan;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.utils.ObjectMap;

public class Statistics {
    private final Preferences statistics;
    private int highScore;
    private float longestSession;
    private int totalCansDelivered;
    private int totalCansThrown;
    private int totalPointsScored;
    private float totalTimePlayed;

    public Statistics() {
        statistics = Gdx.app.getPreferences(Constants.GAME_STATISTICS_PATH);
    }

    void load() {
        try {
            highScore = statistics.getInteger("highScore", 0);
        } catch (RuntimeException e) {
            Gdx.app.error("Statistics", "highScore value is invalid " + e.getLocalizedMessage());
            highScore = 0;
        }
        try {
            longestSession = statistics.getFloat("longestSession", 0);
        } catch (RuntimeException e) {
            Gdx.app.error("Statistics", "longestSession value is invalid " + e.getLocalizedMessage());
            longestSession = 0;
        }
        try {
            totalCansDelivered = statistics.getInteger("totalCansDelivered", 0);
        } catch (RuntimeException e) {
            Gdx.app.error("Statistics", "totalCansDelivered value is invalid " + e.getLocalizedMessage());
            totalCansDelivered = 0;
        }
        try {
            totalCansThrown = statistics.getInteger("totalCansThrown", 0);
        } catch (RuntimeException e) {
            Gdx.app.error("Statistics", "totalCansThrown value is invalid " + e.getLocalizedMessage());
            totalCansThrown = 0;
        }
        try {
            totalPointsScored = statistics.getInteger("totalPointsScored", 0);
        } catch (RuntimeException e) {
            Gdx.app.error("Statistics", "totalPointsScored value is invalid " + e.getLocalizedMessage());
            totalPointsScored = 0;
        }
        try {
            totalTimePlayed = statistics.getFloat("totalTimePlayed", 0);
        } catch (RuntimeException e) {
            Gdx.app.error("Statistics", "totalTimePlayed value is invalid " + e.getLocalizedMessage());
            totalTimePlayed = 0;
        }
    }

    public void save() {
        statistics.flush();
    }

    public int getHighScore() {
        return highScore;
    }

    public float getLongestSession() {
        return longestSession;
    }

    public int getTotalCansDelivered() {
        return totalCansDelivered;
    }

    public int getTotalCansThrown() {
        return totalCansThrown;
    }

    public int getTotalPointsScored() {
        return totalPointsScored;
    }

    public float getTotalTimePlayed() {
        return totalTimePlayed;
    }

    public void updateHighScore(int score) {
        this.highScore = Math.max(highScore, score);
        statistics.putInteger("highScore", highScore);
    }

    public void updateTotalPointsScored(int points) {
        this.totalPointsScored += points;
        statistics.putInteger("totalPointsScored", totalPointsScored);

    }

    public void updateLongestSession(float timeElapsed) {
        this.longestSession = Math.max(longestSession, timeElapsed);
        statistics.putFloat("longestSession", longestSession);
    }

    public void updateTotalTimePlayed(float delta) {
        this.totalTimePlayed += delta;
        statistics.putFloat("totalTimePlayed", totalTimePlayed);
    }

    public void updateTotalCansDelivered(int nCans) {
        this.totalCansDelivered += nCans;
        statistics.putInteger("totalCansDelivered", totalCansDelivered);
    }

    public void incrementTotalCansThrown() {
        this.totalCansThrown++;
        statistics.putInteger("totalCansThrown", totalCansThrown);
    }
}

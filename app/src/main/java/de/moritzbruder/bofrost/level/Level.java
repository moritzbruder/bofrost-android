package de.moritzbruder.bofrost.level;

/**
 * Created by morit on 10.06.2017.
 */
public class Level {

    private static final int[] steps = {10, 50, 100, 200, 300, 400, 500};

    public static int getLevelByPoints (int points) {
        for (int i = 0; i < steps.length; i++) {
            if (points < steps[i]) return i;
        }
        return steps.length;
    }

    public static int getPointsToNext (int points) {
        int level = getLevelByPoints(points);
        if (level > (steps.length - 1)) return -1; //Top level
        else return steps[level] - points;
    }

    public static int getProgress (int points) {
        int level = getLevelByPoints(points);
        if (level > (steps.length - 1)) return 100; //Top level
        int next = steps[level];
        if (level == 0) return (points * 100) / steps[level];

        int prev = steps[level - 1];
        return ((points - prev) * 100) / (steps[level] - prev);
    }
}

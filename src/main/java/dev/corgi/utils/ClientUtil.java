package dev.corgi.utils;

import java.util.Random;

public class ClientUtil {

    public static double getColor(double n, int d) {
        if (d == 0) {
            return (double)Math.round(n);
        } else {
            double p = Math.pow(10.0D, (double)d);
            return (double)Math.round(n * p) / p;
        }
    }

    private static final Random RANDOM = new Random();

    public static int random(int min, int max) {
        return RANDOM.nextInt(max - min) + min;
    }

}

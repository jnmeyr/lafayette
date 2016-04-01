package de.jnmeyr.lafayette;

public class Distance {

    public static double euclid(final double x0, final double y0, final double x1, final double y1) {
        return (Math.pow(Math.pow(Math.abs(x0 - x1), 2.0d) + Math.pow(Math.abs(y0 - y1), 2.0d), 0.5d));
    }

    public static double euclid(final Position from, final Position to) {
        return Distance.euclid(from.x, from.y, to.x, to.y);
    }

    public static double chebyshev(final double x0, final double y0, final double x1, final double y1) {
        return Math.max(Math.abs(x0 - x1), Math.abs(y0 - y1));
    }

    public static double chebyshev(final Position from, final Position to) {
        return Distance.chebyshev(from.x, from.y, to.x, to.y);
    }

}

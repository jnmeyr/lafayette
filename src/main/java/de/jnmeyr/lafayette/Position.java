package de.jnmeyr.lafayette;

import java.util.ArrayList;
import java.util.List;

public class Position {

    private static final int NTH = 64;

    public final double x;
    public final double y;

    public Position(final double x, final double y) {
        this.x = x;
        this.y = y;
    }

    public static List<Position> reduce(final List<Position> allPositions) {
        final List<Position> reducedPositions = new ArrayList<Position>(NTH);

        double allDistance = 0.0f;
        for (int index = 0; index < allPositions.size() - 1; index++) {
            final Position firstPosition = allPositions.get(index);
            final Position secondPosition = allPositions.get(index + 1);

            allDistance += Distance.euclid(firstPosition, secondPosition);
        }
        final double NTHDistance = allDistance / NTH;

        double distance = 0.0f;
        for (int index = 0; index < allPositions.size() - 1; index++) {
            final Position firstPosition = allPositions.get(index);
            final Position secondPosition = allPositions.get(index + 1);

            distance += Distance.euclid(firstPosition, secondPosition);

            if (distance >= NTHDistance) {
                reducedPositions.add(firstPosition);
                distance -= NTHDistance;
            }
        }

        return reducedPositions;
    }

    public static List<Position> normalize(final List<Position> unnormalizedPositions) {
        final List<Position> normalizedPositions = new ArrayList<Position>(NTH);

        Position leftBottom = new Position(Double.MAX_VALUE, Double.MAX_VALUE);
        Position rightTop = new Position(Double.MIN_VALUE, Double.MIN_VALUE);
        for (final Position position : unnormalizedPositions) {
            if (position.x < leftBottom.x) {
                leftBottom = new Position(position.x, leftBottom.y);
            }

            if (position.x > rightTop.x) {
                rightTop = new Position(position.x, rightTop.y);
            }

            if (position.y < leftBottom.y) {
                leftBottom = new Position(leftBottom.x, position.y);
            }

            if (position.y > rightTop.y) {
                rightTop = new Position(rightTop.x, position.y);
            }
        }

        final double xSubtrahend = leftBottom.x;
        final double ySubtrahend = leftBottom.y;

        final double xDivisor = (rightTop.x - xSubtrahend) / (NTH - 1);
        final double yDivisor = (rightTop.y - ySubtrahend) / (NTH - 1);

        for (final Position position : unnormalizedPositions) {
            normalizedPositions.add(new Position((position.x - xSubtrahend) / xDivisor, (position.y - ySubtrahend) / yDivisor));
        }

        return normalizedPositions;
    }

    public static List<Direction> horizontalTransitions(final List<Position> positions) {
        final List<Direction> horizontalTransitions = new ArrayList<Direction>();

        double meridian = 0.0d;
        for (final Position position : positions) {
            if (position.x / 2.0d  > meridian) {
                meridian = position.x / 2.0d;
            }
        }

        for (int index = 0; index < positions.size() - 1; index++) {
            final Position firstPosition = positions.get(index);
            final Position secondPosition = positions.get(index + 1);

            if (firstPosition.x < meridian && secondPosition.x >= meridian) {
                horizontalTransitions.add(Direction.RIGHT);
            } else if (firstPosition.x >= meridian && secondPosition.x < meridian) {
                horizontalTransitions.add(Direction.LEFT);
            }
        }

        return horizontalTransitions;
    }

    public static List<Direction> verticalTransitions(final List<Position> positions) {
        final List<Direction> verticalTransitions = new ArrayList<Direction>();

        double equator = 0.0d;
        for (final Position position : positions) {
            if (position.y / 2.0f  > equator) {
                equator = position.y / 2.0d;
            }
        }

        for (int index = 0; index < positions.size() - 1; index++) {
            final Position firstPosition = positions.get(index);
            final Position secondPosition = positions.get(index + 1);

            if (firstPosition.y < equator && secondPosition.y >= equator) {
                verticalTransitions.add(Direction.DOWN);
            } else if (firstPosition.y >= equator && secondPosition.y < equator) {
                verticalTransitions.add(Direction.UP);
            }
        }

        return verticalTransitions;
    }

    public static boolean closed(final List<Position> positions) {
        if (positions.size() > 1) {
            return Distance.euclid(positions.get(0), positions.get(positions.size() - 1)) < (NTH / 4);
        }

        return true;
    }

    private static boolean cross(final Position firstLineFirstPosition, final Position firstLineSecondPosition, final Position secondLineFirstPosition, final Position secondLineSecondPosition) {
        final double denominator = (secondLineSecondPosition.y - secondLineFirstPosition.y) * (firstLineSecondPosition.x - firstLineFirstPosition.x) - (secondLineSecondPosition.x - secondLineFirstPosition.x) * (firstLineSecondPosition.y - firstLineFirstPosition.y);

        if (Math.abs(denominator) >= 0.0001d) {
            final double firstLinePosition = ((secondLineSecondPosition.x - secondLineFirstPosition.x) * (firstLineFirstPosition.y - secondLineFirstPosition.y) - (secondLineSecondPosition.y - secondLineFirstPosition.y) * (firstLineFirstPosition.x - secondLineFirstPosition.x)) / denominator;
            final double secondLinePosition = ((firstLineSecondPosition.x - firstLineFirstPosition.x) * (firstLineFirstPosition.y - secondLineFirstPosition.y) - (firstLineSecondPosition.y - firstLineFirstPosition.y) * (firstLineFirstPosition.x - secondLineFirstPosition.x)) / denominator;

            return firstLinePosition > 0 && firstLinePosition < 1 && secondLinePosition > 0 && secondLinePosition < 1;
        }

        return false;
    }

    public static int crosses(final List<Position> positions) {
        int crosses = 0;

        for (int firstIndex = 0; firstIndex < positions.size() - 1; firstIndex++) {
            final Position firstLineFirstPosition = positions.get(firstIndex);
            final Position firstLineSecondPosition = positions.get(firstIndex + 1);
            for (int secondIndex = firstIndex + 1; secondIndex < positions.size() - 1; secondIndex++) {
                final Position secondLineFirstPosition = positions.get(secondIndex);
                final Position secondLineSecondPosition = positions.get(secondIndex + 1);

                if (cross(firstLineFirstPosition, firstLineSecondPosition, secondLineFirstPosition, secondLineSecondPosition)) {
                    crosses += 1;
                }
            }
        }

        return crosses;
    }

}

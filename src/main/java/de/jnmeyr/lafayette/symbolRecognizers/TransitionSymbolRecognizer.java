package de.jnmeyr.lafayette.symbolRecognizers;

import de.jnmeyr.lafayette.Declaration;
import de.jnmeyr.lafayette.Direction;
import de.jnmeyr.lafayette.Position;
import de.jnmeyr.lafayette.Symbol;

import java.util.List;

class TransitionSymbolRecognizer extends SymbolRecognizer {

    private final List<Direction> horizontalTransitions;
    private final List<Direction> verticalTransitions;

    public TransitionSymbolRecognizer(final List<Position> positions) {
        super(positions);

        this.horizontalTransitions = Position.horizontalTransitions(this.positions);
        this.verticalTransitions = Position.verticalTransitions(this.positions);
    }

    @Override
    public int score(final Symbol symbol, final Declaration declaration) {
        if (!this.symbolScores.containsKey(symbol)) {
            int score = 0;

            score += Math.abs(this.horizontalTransitions.size() - declaration.getHorizontalTransitions().size());
            for (int index = 0; index < Math.min(this.horizontalTransitions.size(), declaration.getHorizontalTransitions().size()); index++) {
                if (this.horizontalTransitions.get(index) != declaration.getHorizontalTransitions().get(index)) {
                    score += 1;
                }
            }

            score += Math.abs(this.verticalTransitions.size() - declaration.getVerticalTransitions().size());
            for (int index = 0; index < Math.min(this.verticalTransitions.size(), declaration.getVerticalTransitions().size()); index++) {
                if (this.verticalTransitions.get(index) != declaration.getVerticalTransitions().get(index)) {
                    score += 1;
                }
            }

            this.symbolScores.put(symbol, score);
        }

        return super.score(symbol);
    }

    @Override
    public boolean is(final Symbol symbol, final Declaration declaration) {
        return this.score(symbol, declaration) <= 0;
    }

    @Override
    public boolean isNot(final Symbol symbol, final Declaration declaration) {
        return this.score(symbol, declaration) >= 1;
    }

}

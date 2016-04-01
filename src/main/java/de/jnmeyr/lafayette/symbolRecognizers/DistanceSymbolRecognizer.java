package de.jnmeyr.lafayette.symbolRecognizers;

import de.jnmeyr.lafayette.Declaration;
import de.jnmeyr.lafayette.Distance;
import de.jnmeyr.lafayette.Position;
import de.jnmeyr.lafayette.Symbol;

import java.util.List;

class DistanceSymbolRecognizer extends SymbolRecognizer {

    public DistanceSymbolRecognizer(final List<Position> positions) {
        super(positions);
    }

    @Override
    public int score(final Symbol symbol, final Declaration declaration) {
        if (!this.symbolScores.containsKey(symbol)) {
            double score = 0.0d;

            score += Math.pow(Math.abs(this.positions.size() - declaration.getPositions().size()), 4);
            for (int index = 0; index < Math.min(this.positions.size(), declaration.getPositions().size()); index++) {
                score += Math.pow(Distance.euclid(this.positions.get(index), declaration.getPositions().get(index)), 2);
            }

            this.symbolScores.put(symbol, (int) score);
        }

        return super.score(symbol);
    }

    @Override
    public boolean is(final Symbol symbol, final Declaration declaration) {
        return this.score(symbol, declaration) <= 50000;
    }

    @Override
    public boolean isNot(final Symbol symbol, final Declaration declaration) {
        return this.score(symbol, declaration) >= 100000;
    }

}

package de.jnmeyr.lafayette.symbolRecognizers;

import de.jnmeyr.lafayette.Declaration;
import de.jnmeyr.lafayette.Position;
import de.jnmeyr.lafayette.Symbol;

import java.util.List;

class CrossesSymbolRecognizer extends SymbolRecognizer {

    private final int crosses;

    public CrossesSymbolRecognizer(final List<Position> positions) {
        super(positions);

        this.crosses = Position.crosses(this.positions);
    }

    @Override
    public int score(final Symbol symbol, final Declaration declaration) {
        if (!this.symbolScores.containsKey(symbol)) {
            final int score = Math.abs(this.crosses - declaration.getCrosses());

            this.symbolScores.put(symbol, score);
        }

        return super.score(symbol);
    }

    @Override
    public boolean is(final Symbol symbol, final Declaration declaration) {
        return this.score(symbol, declaration) <= 1;
    }

    @Override
    public boolean isNot(final Symbol symbol, final Declaration declaration) {
        return this.score(symbol, declaration) >= 3;
    }

}

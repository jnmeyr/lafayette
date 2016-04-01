package de.jnmeyr.lafayette.symbolRecognizers;

import de.jnmeyr.lafayette.Declaration;
import de.jnmeyr.lafayette.Position;
import de.jnmeyr.lafayette.Symbol;

import java.util.List;

class ClosedSymbolRecognizer extends SymbolRecognizer {

    private final boolean closed;

    public ClosedSymbolRecognizer(final List<Position> positions) {
        super(positions);

        this.closed = Position.closed(this.positions);
    }

    @Override
    public int score(final Symbol symbol, final Declaration declaration) {
        if (!this.symbolScores.containsKey(symbol)) {
            final int score = this.closed == declaration.getClosed() ? 0 : 1;

            this.symbolScores.put(symbol, score);
        }

        return super.score(symbol);
    }

    @Override
    public boolean is(final Symbol symbol, final Declaration declaration) {
        return this.score(symbol, declaration) == 0;
    }

    @Override
    public boolean isNot(final Symbol symbol, final Declaration declaration) {
        return this.score(symbol, declaration) != 0;
    }

}

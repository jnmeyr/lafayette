package de.jnmeyr.lafayette.symbolRecognizers;

import de.jnmeyr.lafayette.Declaration;
import de.jnmeyr.lafayette.Position;
import de.jnmeyr.lafayette.Symbol;

import java.util.ArrayList;
import java.util.List;

public class TheSymbolRecognizer extends SymbolRecognizer {

    private final List<SymbolRecognizer> symbolRecognizers;

    public TheSymbolRecognizer(final List<Position> positions) {
        super(positions);

        this.symbolRecognizers = new ArrayList<SymbolRecognizer>() {{
            this.add(new TransitionSymbolRecognizer(TheSymbolRecognizer.this.positions));
            this.add(new DistanceSymbolRecognizer(TheSymbolRecognizer.this.positions));
            this.add(new ClosedSymbolRecognizer(TheSymbolRecognizer.this.positions));
            this.add(new CrossesSymbolRecognizer(TheSymbolRecognizer.this.positions));
        }};
    }

    @Override
    public int score(final Symbol symbol) {
        if (!this.symbolScores.containsKey(symbol)) {
            int score = 0;

            for (final SymbolRecognizer symbolRecognizer : this.symbolRecognizers) {
                score += symbolRecognizer.index(symbol);
            }

            this.symbolScores.put(symbol, score);
        }

        return super.score(symbol);
    }

    @Override
    public int score(final Symbol symbol, final Declaration declaration) {
        return this.score(symbol);
    }

    @Override
    public boolean is(final Symbol symbol, final Declaration declaration) {
        boolean is = true;

        for (final SymbolRecognizer symbolRecognizer : this.symbolRecognizers) {
            is = symbolRecognizer.is(symbol, declaration) && is;
        }

        return is;
    }

    @Override
    public boolean isNot(final Symbol symbol, final Declaration declaration) {
        boolean isNot = false;

        for (final SymbolRecognizer symbolRecognizer : this.symbolRecognizers) {
            isNot = symbolRecognizer.isNot(symbol, declaration) || isNot;
        }

        return isNot;
    }

}

package de.jnmeyr.lafayette.symbolRecognizers;

import de.jnmeyr.lafayette.Declaration;
import de.jnmeyr.lafayette.Position;
import de.jnmeyr.lafayette.Symbol;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public abstract class SymbolRecognizer {

    protected final List<Position> positions;
    protected final Map<Symbol, Integer> symbolScores;

    public SymbolRecognizer(final List<Position> positions) {
        this.positions = Position.normalize(Position.reduce(positions));
        this.symbolScores = new EnumMap<Symbol, Integer>(Symbol.class);
    }

    public int score(final Symbol symbol) {
        if (this.symbolScores.containsKey(symbol)) {
            return this.symbolScores.get(symbol);
        }

        return 0;
    }

    public int score(final Symbol symbol, final Declaration declaration) {
        return this.score(symbol);
    }

    public int index(final Symbol symbol) {
        final Integer score = this.symbolScores.get(symbol);

        if (score != null) {
            int index = 0;

            for (final Map.Entry<Symbol, Integer> symbolScore : this.symbolScores.entrySet()) {
                if (symbolScore.getValue() < score) {
                    index += 1;
                }
            }

            return index;
        }

        return Integer.MAX_VALUE;
    }

    public abstract boolean is(final Symbol symbol, final Declaration declaration);

    public abstract boolean isNot(final Symbol symbol, final Declaration declaration);

}

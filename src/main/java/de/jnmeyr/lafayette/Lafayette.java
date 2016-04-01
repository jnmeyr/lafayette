package de.jnmeyr.lafayette;

import de.jnmeyr.lafayette.symbolRecognizers.SymbolRecognizer;
import de.jnmeyr.lafayette.symbolRecognizers.TheSymbolRecognizer;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Lafayette {

    private final Mode mode;

    private final Map<Symbol, Declaration> symbolDeclarations;
    private final List<Position> positions;

    public Lafayette(final Mode Mode) {
        this.mode = Mode;

        this.symbolDeclarations = Declaration.loadDeclarations();
        this.positions = new ArrayList<Position>();
    }

    public Lafayette() {
        this(Mode.STRICT);
    }

    public void extend(final double x, final double y) {
        this.positions.add(new Position(x, y));
    }

    public void reset() {
        this.positions.clear();
    }

    public Symbol execute() {
        final SymbolRecognizer theSymbolRecognizer = new TheSymbolRecognizer(this.positions);

        final List<Symbol> symbols = new ArrayList<Symbol>();
        for (final Map.Entry<Symbol, Declaration> symbolDeclaration : this.symbolDeclarations.entrySet()) {
            switch (this.mode) {
                case STRICT:
                    if (theSymbolRecognizer.is(symbolDeclaration.getKey(), symbolDeclaration.getValue())) {
                        symbols.add(symbolDeclaration.getKey());
                    }
                    break;
                case LENIENT:
                    if (!theSymbolRecognizer.isNot(symbolDeclaration.getKey(), symbolDeclaration.getValue())) {
                        symbols.add(symbolDeclaration.getKey());
                    }
                    break;
            }
        }

        final Map<Symbol, Integer> symbolScores = new EnumMap<Symbol, Integer>(Symbol.class);
        for (final Symbol symbol : symbols) {
            symbolScores.put(symbol, theSymbolRecognizer.score(symbol));
        }

        Symbol symbol = null;
        int score = Integer.MAX_VALUE;
        for (final Map.Entry<Symbol, Integer> symbolScore : symbolScores.entrySet()) {
            if (symbol == null || score > symbolScore.getValue()) {
                symbol = symbolScore.getKey();
                score = symbolScore.getValue();
            }
        }

        this.positions.clear();

        return symbol;
    }

}

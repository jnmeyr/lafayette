package de.jnmeyr.lafayette;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;

public class Declaration {

    private List<Direction> horizontalTransitions;
    private List<Direction> verticalTransitions;
    private Boolean closed;
    private Integer crosses;
    private List<Position> positions;

    public static Map<Symbol, Declaration> loadDeclarations() {
        final InputStream inputStream = Thread.currentThread().getContextClassLoader().getResourceAsStream("declarations.json");
        final BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        return new Gson().fromJson(bufferedReader, new TypeToken<EnumMap<Symbol, Declaration>>() {}.getType());
    }

    public List<Direction> getHorizontalTransitions() {
        return this.horizontalTransitions;
    }

    public void setHorizontalTransitions(final List<Direction> horizontalTransitions) {
        this.horizontalTransitions = horizontalTransitions;
    }

    public List<Direction> getVerticalTransitions() {
        return this.verticalTransitions;
    }

    public void setVerticalTransitions(final List<Direction> verticalTransitions) {
        this.verticalTransitions = verticalTransitions;
    }

    public Boolean getClosed() {
        return this.closed;
    }

    public void setClosed(final Boolean closed) {
        this.closed = closed;
    }

    public Integer getCrosses() {
        return this.crosses;
    }

    public void setCrosses(final Integer crosses) {
        this.crosses = crosses;
    }

    public List<Position> getPositions() {
        return this.positions;
    }

    public void setPositions(final List<Position> positions) {
        this.positions = positions;
    }

}

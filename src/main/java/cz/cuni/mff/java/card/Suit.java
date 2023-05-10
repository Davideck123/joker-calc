package cz.cuni.mff.java.card;

import java.util.Map;

public enum Suit {

    CLUBS(SuitAlias.CLUBS),
    DIAMONDS(SuitAlias.DIAMONDS),
    HEARTS(SuitAlias.HEARTS),
    SPADES(SuitAlias.SPADES);

    private static final Map<Character, Suit> aliasMap = Map.of(
            SuitAlias.CLUBS, CLUBS,
            SuitAlias.DIAMONDS, DIAMONDS,
            SuitAlias.HEARTS, HEARTS,
            SuitAlias.SPADES, SPADES
    );
    private final char alias;

    Suit(char alias) {
        this.alias = alias;
    }

    public char getAlias() {
        return alias;
    }

    public static Suit fromAlias(char alias) {
        return aliasMap.get(alias);
    }

    public static Suit fromAlias(String alias) {
        assert alias.length() == 1;
        return fromAlias(alias.charAt(0));
    }
}

class SuitAlias {
    static final char CLUBS = 'c';
    static final char DIAMONDS = 'd';
    static final char HEARTS = 'h';
    static final char SPADES = 's';
};

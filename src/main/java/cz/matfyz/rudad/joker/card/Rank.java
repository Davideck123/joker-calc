package cz.matfyz.rudad.joker.card;

import java.util.Map;

/**
 * Represents the ranks of a card in a deck.
 */
public enum Rank {
    TWO(RankAlias.TWO),
    THREE(RankAlias.THREE),
    FOUR(RankAlias.FOUR),
    FIVE(RankAlias.FIVE),
    SIX(RankAlias.SIX),
    SEVEN(RankAlias.SEVEN),
    EIGHT(RankAlias.EIGHT),
    NINE(RankAlias.NINE),
    TEN(RankAlias.TEN),
    JACK(RankAlias.JACK),
    QUEEN(RankAlias.QUEEN),
    KING(RankAlias.KING),
    ACE(RankAlias.ACE);

    private static final Map<Character, Rank> aliasMap = Map.ofEntries(
            Map.entry(RankAlias.TWO, TWO),
            Map.entry(RankAlias.THREE, THREE),
            Map.entry(RankAlias.FOUR, FOUR),
            Map.entry(RankAlias.FIVE, FIVE),
            Map.entry(RankAlias.SIX, SIX),
            Map.entry(RankAlias.SEVEN, SEVEN),
            Map.entry(RankAlias.EIGHT, EIGHT),
            Map.entry(RankAlias.NINE, NINE),
            Map.entry(RankAlias.TEN, TEN),
            Map.entry(RankAlias.JACK, JACK),
            Map.entry(RankAlias.QUEEN, QUEEN),
            Map.entry(RankAlias.KING, KING),
            Map.entry(RankAlias.ACE, ACE)
    );

    private final char alias;

    /**
     * Constructs a new Rank with the specified alias.
     *
     * @param alias the character alias for the rank
     */
    Rank(char alias) {
        this.alias = alias;
    }

    /**
     * Returns the Rank corresponding to the given alias.
     *
     * @param alias the character alias to look up
     * @return the Rank associated with the alias
     * @throws IllegalArgumentException if the alias is invalid
     */
    public static Rank fromAlias(char alias) throws IllegalArgumentException {
        var rank = aliasMap.get(Character.toUpperCase(alias));
        if (rank == null) {
            throw new IllegalArgumentException(String.format("Invalid rank alias \"%c\"", alias));
        }
        return rank;
    }

    /**
     * Returns the Rank corresponding to the given alias.
     *
     * @param alias the string alias to look up
     * @return the Rank associated with the alias
     * @throws IllegalArgumentException if the alias is invalid
     */
    public static Rank fromAlias(String alias) throws IllegalArgumentException {
        assert alias.length() == 1;
        return fromAlias(alias.charAt(0));
    }

    /**
     * Returns the character alias of the rank.
     *
     * @return the character alias
     */
    public char getAlias() {
        return alias;
    }

    /**
     * Returns the string representation of the rank.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return String.valueOf(alias);
    }
}

/**
 * Contains aliases for the ranks.
 */
class RankAlias {
    static final char TWO = '2';
    static final char THREE = '3';
    static final char FOUR = '4';
    static final char FIVE = '5';
    static final char SIX = '6';
    static final char SEVEN = '7';
    static final char EIGHT = '8';
    static final char NINE = '9';
    static final char TEN = 'T';
    static final char JACK = 'J';
    static final char QUEEN = 'Q';
    static final char KING = 'K';
    static final char ACE = 'A';
}

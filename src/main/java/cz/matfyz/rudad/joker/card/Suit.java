package cz.matfyz.rudad.joker.card;

import java.util.Map;

/**
 * Represents the suits of a card in a deck.
 */
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

    /**
     * Constructs a new Suit with the specified alias.
     *
     * @param alias the character alias for the suit
     */
    Suit(char alias) {
        this.alias = alias;
    }

    /**
     * Returns the Suit corresponding to the given alias.
     *
     * @param alias the character alias to look up
     * @return the Suit associated with the alias
     * @throws IllegalArgumentException if the alias is invalid
     */
    public static Suit fromAlias(char alias) {
        var suit = aliasMap.get(Character.toLowerCase(alias));
        if (suit == null) {
            throw new IllegalArgumentException(String.format("Invalid suit alias \"%c\"", alias));
        }
        return suit;
    }

    /**
     * Returns the Suit corresponding to the given alias.
     *
     * @param alias the string alias to look up
     * @return the Suit associated with the alias
     * @throws IllegalArgumentException if the alias is invalid
     */
    public static Suit fromAlias(String alias) {
        assert alias.length() == 1;
        return fromAlias(alias.charAt(0));
    }

    /**
     * Returns the character alias of the suit.
     *
     * @return the character alias
     */
    public char getAlias() {
        return alias;
    }

    /**
     * Returns the string representation of the suit.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return String.valueOf(alias);
    }
}

/**
 * Contains aliases for the suits.
 */
class SuitAlias {
    static final char CLUBS = 'c';
    static final char DIAMONDS = 'd';
    static final char HEARTS = 'h';
    static final char SPADES = 's';
}

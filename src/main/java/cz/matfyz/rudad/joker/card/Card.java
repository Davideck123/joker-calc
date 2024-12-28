package cz.matfyz.rudad.joker.card;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents a card in a deck.
 */
public class Card implements AutoCloseable {

    private static final int NUMBER_OF_CARDS = Rank.values().length * Suit.values().length;
    private static final Map<Integer, Card> createdCards = new HashMap<>();
    private final Rank rank;
    private final Suit suit;
    private final int cardId;

    private static boolean printSuitEmojis = false;

    /**
     * Constructs a Card instance with the given card ID.
     *
     * @param cardId the ID of the card
     */
    private Card(int cardId) {
        rank = Rank.values()[cardId / Suit.values().length];
        suit = Suit.values()[cardId % Suit.values().length];
        this.cardId = cardId;
    }

    /**
     * Returns a Card instance based on the provided card alias.
     *
     * @param cardAlias the card alias as a two-character string
     * @return the Card instance
     * @throws IllegalArgumentException if the card alias is invalid
     */
    public static Card getInstance(String cardAlias) throws IllegalArgumentException {
        if (cardAlias.length() != 2) {
            throw new IllegalArgumentException("Invalid card alias " + cardAlias);
        }
        Rank rank = Rank.fromAlias(cardAlias.charAt(0));
        Suit suit = Suit.fromAlias(cardAlias.charAt(1));
        return getInstance(countCardId(rank, suit));
    }

    /**
     * Returns a Card instance based on the provided rank and suit.
     *
     * @param rank the rank of the card
     * @param suit the suit of the card
     * @return the Card instance
     * @throws IllegalArgumentException if the rank or suit is invalid
     */
    public static Card getInstance(Rank rank, Suit suit) throws IllegalArgumentException {
        return getInstance(countCardId(rank, suit));
    }

    /**
     * Returns a Card instance based on the provided card ID.
     *
     * @param cardId the ID of the card
     * @return the Card instance
     * @throws IllegalArgumentException if the card ID is invalid
     */
    public static Card getInstance(int cardId) throws IllegalArgumentException {
        if (!isValidCardId(cardId)) {
            throw new IllegalArgumentException("Invalid card ID " + cardId);
        }
        if (createdCards.get(cardId) != null) {
            return createdCards.get(cardId);
        }
        Card card = new Card(cardId);
        createdCards.put(cardId, card);
        return card;
    }

    /**
     * Checks if the given card ID is valid.
     *
     * @param cardId the ID of the card
     * @return {@code true} if the card ID is valid, which means it is in the range [0, 51], {@code false} otherwise
     */
    private static boolean isValidCardId(int cardId) {
        return cardId >= 0 && cardId < NUMBER_OF_CARDS;
    }

    /**
     * Calculates the card ID based on the given rank and suit.
     *
     * @param rank the rank of the card
     * @param suit the suit of the card
     * @return the calculated card ID
     */
    private static int countCardId(Rank rank, Suit suit) {
        return suit.ordinal() + Suit.values().length * rank.ordinal();
    }

    /**
     * Closes the Card instance by removing it from the created cards collection.
     *
     * @throws Exception if an error occurs during the closing operation
     */
    @Override
    public void close() throws Exception {
        createdCards.remove(this.cardId);
    }

    /**
     * Returns the rank of the card.
     *
     * @return the rank
     */
    public Rank getRank() {
        return rank;
    }

    /**
     * Returns the suit of the card.
     *
     * @return the suit
     */
    public Suit getSuit() {
        return suit;
    }

    /**
     * Returns the ID of the card.
     *
     * @return the card ID
     */
    public int getCardId() {
        return cardId;
    }

    /**
     * Returns if the suit emoji should be printed.
     *
     * @return {@code true} if the suit emoji should be printed, {@code false} otherwise
     */
    public static boolean isPrintSuitEmojis() {
        return printSuitEmojis;
    }

    /**
     * Sets if the suit emoji should be printed.
     *
     * @param printSuitEmojis {@code true} if the suit emoji should be printed, {@code false} otherwise
     */
    public static void setPrintSuitEmojis(boolean printSuitEmojis) {
        Card.printSuitEmojis = printSuitEmojis;
    }

    /**
     * Returns the string representation of the card.
     *
     * @return the string representation
     */
    @Override
    public String toString() {
        return rank.toString() + (printSuitEmojis ? suit.emoji() : suit);
    }

}

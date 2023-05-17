package cz.matfyz.rudad.joker.calculator;

import cz.matfyz.rudad.joker.card.Card;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Represents a player in a poker game.
 */
public class Player {
    private final AtomicInteger numberOfWins = new AtomicInteger();
    private final AtomicInteger numberOfTies = new AtomicInteger();
    private final Card[] cards;
    private Double winProbability = null;
    private Double tieProbability = null;

    /**
     * Constructs a player with a hand given by the aliases of the cards.
     *
     * @param cardAliases the aliases of the cards
     * @throws IllegalArgumentException if the card aliases are invalid
     */
    public Player(String... cardAliases) throws IllegalArgumentException {
        cards = Arrays.stream(cardAliases).map(Card::getInstance).toArray(Card[]::new);
    }

    /**
     * Constructs a player with a hand given by the cards.
     *
     * @param cards the cards held by the player
     */
    public Player(Card... cards) {
        this.cards = cards;
    }

    /**
     * Constructs a player with a hand given by the cards.
     *
     * @param cards the list of cards held by the player
     */
    public Player(List<Card> cards) {
        this.cards = cards.toArray(Card[]::new);
    }

    /**
     * Increments the number of wins for the player.
     */
    public void addWin() {
        numberOfWins.incrementAndGet();
    }

    /**
     * Increments the number of ties (splits) for the player.
     */
    public void addTie() {
        numberOfTies.incrementAndGet();
    }

    /**
     * Returns the number of wins for the player.
     *
     * @return the number of wins
     */
    public int getNumberOfWins() {
        return numberOfWins.get();
    }

    /**
     * Returns the number of ties (splits) for the player.
     *
     * @return the number of ties
     */
    public int getNumberOfTies() {
        return numberOfTies.get();
    }

    /**
     * Returns the cards held by the player.
     *
     * @return the cards held by the player
     */
    public Card[] getCards() {
        return cards;
    }

    /**
     * Returns the win probability of the player.
     *
     * @return the win probability
     */
    public double getWinProbability() {
        return winProbability;
    }

    /**
     * Sets the win probability of the player based on the total number of hands.
     *
     * @param numberOfHands the total number of hands played
     */
    public void setWinProbability(int numberOfHands) {
        this.winProbability = numberOfWins.doubleValue() / numberOfHands;
    }

    /**
     * Returns the tie probability of the player.
     *
     * @return the tie probability
     */
    public double getTieProbability() {
        return tieProbability;
    }

    /**
     * Sets the tie probability of the player based on the total number of hands.
     *
     * @param numberOfHands the total number of hands played
     */
    public void setTieProbability(int numberOfHands) {
        this.tieProbability = numberOfTies.doubleValue() / numberOfHands;
    }

    /**
     * Returns a string representation of the player's cards.
     *
     * @return a string representation of the cards
     */
    @Override
    public String toString() {
        return Arrays.stream(cards).map(Card::toString).reduce("", String::concat);
    }
}

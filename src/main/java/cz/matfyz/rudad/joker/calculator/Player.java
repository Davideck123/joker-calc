package cz.matfyz.rudad.joker.calculator;

import cz.matfyz.rudad.joker.card.Card;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class Player {
    private final AtomicInteger numberOfWins = new AtomicInteger();
    private final AtomicInteger numberOfSplits = new AtomicInteger();
    private double winProbability;
    private double splitProbability;
    private final Card[] cards;

    public Player(String... cardAliases) throws IllegalArgumentException {
        cards = Arrays.stream(cardAliases).map(Card::getInstance).toArray(Card[]::new);
    }

    public Player(Card... cards) {
        this.cards = cards;
    }

    public Player(List<Card> cards) {
        this.cards = cards.toArray(Card[]::new);
    }

    public void addWin() {
        numberOfWins.incrementAndGet();
    }

    public void addSplit() {
        numberOfSplits.incrementAndGet();
    }

    public int getNumberOfWins() {
        return numberOfWins.get();
    }

    public int getNumberOfSplits() {
        return numberOfSplits.get();
    }

    public Card[] getCards() {
        return cards;
    }

    public double getWinProbability() {
        return winProbability;
    }

    public double getSplitProbability() {
        return splitProbability;
    }

    public void setWinProbability(double winProbability) {
        this.winProbability = winProbability;
    }

    public void setSplitProbability(double splitProbability) {
        this.splitProbability = splitProbability;
    }

    @Override
    public String toString() {
        return Arrays.stream(cards).map(Card::toString).reduce("", String::concat);
    }
}

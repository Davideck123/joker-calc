package cz.cuni.mff.java.card;

public class Card {

    public Card(String cardAlias) {
        assert cardAlias.length() == 2;
        rank = Rank.fromAlias(cardAlias.charAt(0));
        suit = Suit.fromAlias(cardAlias.charAt(1));
        cardId = suit.ordinal() + Suit.values().length * rank.ordinal();
    }

    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
        cardId = suit.ordinal() + Suit.values().length * rank.ordinal();
    }

    public Card(int cardId) {
        rank = Rank.values()[cardId / Suit.values().length];
        suit = Suit.values()[cardId % Suit.values().length];
        this.cardId = cardId;
    }

    public Rank getRank() {
        return rank;
    }

    public Suit getSuit() {
        return suit;
    }

    public int getCardId() {
        return cardId;
    }

    private final Rank rank;
    private final Suit suit;
    private final int cardId;
}

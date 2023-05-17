package cz.matfyz.rudad.joker.evaluator;

import cz.matfyz.rudad.joker.card.Card;

import java.util.List;

/**
 * The Evaluator class provides methods for evaluating poker hands with 7 cards.
 * It uses a combination of hash tables and lookup tables for efficient evaluation.
 * Source: <a href="https://github.com/HenryRLee/PokerHandEvaluator/blob/master/cpp/src/evaluator7.c">evaluator7.c</a>
 */
public final class Evaluator {

    /**
     * Private constructor to prevent instantiation.
     */
    private Evaluator() {

    }

    /**
     * Determines if two poker hands are tied.
     *
     * @param handValue1 the value of the first hand
     * @param handValue2 the value of the second hand
     * @return {@code true} if the hands are tied, {@code false} otherwise
     */
    public static boolean isTied(short handValue1, short handValue2) {
        return handValue1 == handValue2;
    }

    /**
     * Determines if the first poker hand is winning against the second poker hand.
     * Lower value means stronger hand.
     *
     * @param handValue1 the value of the first hand
     * @param handValue2 the value of the second hand
     * @return {@code true} if the first hand is winning, {@code false} otherwise
     */
    public static boolean isFirstHandWinning(short handValue1, short handValue2) {
        return handValue1 < handValue2;
    }

    /**
     * Determines if the second poker hand is winning against the first poker hand.
     * Lower value means stronger hand.
     *
     * @param handValue1 the value of the first hand
     * @param handValue2 the value of the second hand
     * @return {@code true} if the second hand is winning, {@code false} otherwise
     */
    public static boolean isSecondHandWinning(short handValue1, short handValue2) {
        return handValue1 > handValue2;
    }

    /**
     * Evaluates the strength of a poker hand with 7 cards specified as string aliases.
     *
     * @param a the first card alias
     * @param b the second card alias
     * @param c the third card alias
     * @param d the fourth card alias
     * @param e the fifth card alias
     * @param f the sixth card alias
     * @param g the seventh card alias
     * @return the evaluation result as a short value
     */
    public static short evaluate7Cards(String a, String b, String c, String d, String e, String f, String g) {
        return evaluate7Cards(
                Card.getInstance(a),
                Card.getInstance(b),
                Card.getInstance(c),
                Card.getInstance(d),
                Card.getInstance(e),
                Card.getInstance(f),
                Card.getInstance(g)
        );
    }

    /**
     * Evaluates the strength of a poker hand with 7 cards given as a list of Card objects.
     *
     * @param hand the list of 7 Card objects representing the hand
     * @return the evaluation result as a short value
     * @throws AssertionError if the hand does not contain exactly 7 cards
     */
    public static short evaluate7Cards(List<Card> hand) {
        assert hand.size() == 7;
        return evaluate7Cards(
                hand.get(0),
                hand.get(1),
                hand.get(2),
                hand.get(3),
                hand.get(4),
                hand.get(5),
                hand.get(6)
        );
    }

    /**
     * Evaluates the strength of a poker hand with 7 cards given as individual Card objects.
     *
     * @param a the first card
     * @param b the second card
     * @param c the third card
     * @param d the fourth card
     * @param e the fifth card
     * @param f the sixth card
     * @param g the seventh card
     * @return the evaluation result as a short value
     */
    public static short evaluate7Cards(Card a, Card b, Card c, Card d, Card e, Card f, Card g) {
        return evaluate7Cards(
                a.getCardId(),
                b.getCardId(),
                c.getCardId(),
                d.getCardId(),
                e.getCardId(),
                f.getCardId(),
                g.getCardId()
        );
    }

    /**
     * Evaluates the strength of a poker hand with 7 cards given their card IDs.
     * <p>
     * Card IDs are integers ranged from 0-51.
     * The two least significant bits represent the suit, ranged from 0-3.
     * The rest of it represent the rank, ranged from 0-12.
     * 13 * 4 gives 52 IDs.
     * Source: <a href="https://github.com/HenryRLee/PokerHandEvaluator/blob/master/cpp/src/evaluator7.c#L59">evaluator7.c</a>
     *
     * @param a the card ID of the first card
     * @param b the card ID of the second card
     * @param c the card ID of the third card
     * @param d the card ID of the fourth card
     * @param e the card ID of the fifth card
     * @param f the card ID of the sixth card
     * @param g the card ID of the seventh card
     * @return the evaluation result as a short value
     */
    public static short evaluate7Cards(int a, int b, int c, int d, int e, int f, int g) {
        int suitHash = 0;

        suitHash += SUITBIT_BY_ID[a];
        suitHash += SUITBIT_BY_ID[b];
        suitHash += SUITBIT_BY_ID[c];
        suitHash += SUITBIT_BY_ID[d];
        suitHash += SUITBIT_BY_ID[e];
        suitHash += SUITBIT_BY_ID[f];
        suitHash += SUITBIT_BY_ID[g];

        if (SUITS[suitHash] > 0) {
            final int[] suitBinary = new int[4];
            suitBinary[a & 0x3] |= BINARIES_BY_ID[a];
            suitBinary[b & 0x3] |= BINARIES_BY_ID[b];
            suitBinary[c & 0x3] |= BINARIES_BY_ID[c];
            suitBinary[d & 0x3] |= BINARIES_BY_ID[d];
            suitBinary[e & 0x3] |= BINARIES_BY_ID[e];
            suitBinary[f & 0x3] |= BINARIES_BY_ID[f];
            suitBinary[g & 0x3] |= BINARIES_BY_ID[g];

            return FLUSH[suitBinary[SUITS[suitHash] - 1]];
        }

        final byte[] quinary = new byte[13];

        quinary[a >> 2]++;
        quinary[b >> 2]++;
        quinary[c >> 2]++;
        quinary[d >> 2]++;
        quinary[e >> 2]++;
        quinary[f >> 2]++;
        quinary[g >> 2]++;

        final int hash = hashQuinary(quinary, 7);

        return NOFLUSH7[hash];
    }

    /**
     * Computes the hash value for a quinary representation of card ranks.
     * Source: <a href="https://github.com/HenryRLee/PokerHandEvaluator/blob/master/cpp/src/hash.c#L20">hash.c</a>
     *
     * @param q the quinary array representing the card ranks
     * @param k the number of cards in the hand
     * @return the computed hash value
     */
    private static int hashQuinary(final byte[] q, int k) {
        int sum = 0;
        final int len = 13;

        for (int i = 0; i < len; ++i) {
            sum += DP[q[i]][len-i-1][k];

            k -= q[i];

            if (k <= 0) {
                break;
            }
        }
        return sum;
    }

    // Constant fields for lookup tables and bit manipulation
    private static final short[] NOFLUSH7 = Hashtable7.NOFLUSH7;

    private static final int[][][] DP = DPTables.DP;

    private static final short[] FLUSH = HashtableFlush.FLUSH;

    private static final byte[] SUITS = DPTables.SUITS;

    /**
     * Bit representation of the rank for each card ID.
     */
    private static final short[] BINARIES_BY_ID = {
            0x1,    0x1,    0x1,    0x1,
            0x2,    0x2,    0x2,    0x2,
            0x4,    0x4,    0x4,    0x4,
            0x8,    0x8,    0x8,    0x8,
            0x10,   0x10,   0x10,   0x10,
            0x20,   0x20,   0x20,   0x20,
            0x40,   0x40,   0x40,   0x40,
            0x80,   0x80,   0x80,   0x80,
            0x100,  0x100,  0x100,  0x100,
            0x200,  0x200,  0x200,  0x200,
            0x400,  0x400,  0x400,  0x400,
            0x800,  0x800,  0x800,  0x800,
            0x1000, 0x1000, 0x1000, 0x1000,
    };

    /**
     * Bit representation of the suit for each card ID.
     */
    private static final short[] SUITBIT_BY_ID = {
            0x1, 0x8, 0x40, 0x200,
            0x1, 0x8, 0x40, 0x200,
            0x1, 0x8, 0x40, 0x200,
            0x1, 0x8, 0x40, 0x200,
            0x1, 0x8, 0x40, 0x200,
            0x1, 0x8, 0x40, 0x200,
            0x1, 0x8, 0x40, 0x200,
            0x1, 0x8, 0x40, 0x200,
            0x1, 0x8, 0x40, 0x200,
            0x1, 0x8, 0x40, 0x200,
            0x1, 0x8, 0x40, 0x200,
            0x1, 0x8, 0x40, 0x200,
            0x1, 0x8, 0x40, 0x200,
    };
}

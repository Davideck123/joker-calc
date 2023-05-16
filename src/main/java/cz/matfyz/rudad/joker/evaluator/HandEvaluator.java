package cz.matfyz.rudad.joker.evaluator;

import cz.matfyz.rudad.joker.card.Card;

import java.util.List;

public final class HandEvaluator {
    private HandEvaluator() {

    }

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

    private static final short[] NOFLUSH7 = Hashtable7.NOFLUSH7;

    // placeholder for now
    private static final int[][][] DP = DPTables.DP;

    // placeholder for now
    private static final short[] FLUSH = HashtableFlush.FLUSH;

    // placeholder for now
    private static final byte[] SUITS = DPTables.SUITS;

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

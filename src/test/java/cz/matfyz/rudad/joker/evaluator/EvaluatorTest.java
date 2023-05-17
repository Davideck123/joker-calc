package cz.matfyz.rudad.joker.evaluator;

import cz.matfyz.rudad.joker.card.Card;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class EvaluatorTest {

    @Test
    void testEvaluate7Cards() {
        // Community cards
        Card a = Card.getInstance("Ah");
        Card b = Card.getInstance("Kh");
        Card c = Card.getInstance("Th");
        Card d = Card.getInstance("Qh");
        Card e = Card.getInstance("Jh");

        // Player 1
        Card f = Card.getInstance("Ad");
        Card g = Card.getInstance("Ac");

        // Player 2
        Card h = Card.getInstance("9d");
        Card i = Card.getInstance("9h");

        List<Card> hand1 = new ArrayList<>();
        hand1.add(a);
        hand1.add(b);
        hand1.add(c);
        hand1.add(d);
        hand1.add(e);
        hand1.add(f);
        hand1.add(g);

        short rank1 = Evaluator.evaluate7Cards(hand1);
        short rank2 = Evaluator.evaluate7Cards(
                a.toString(),
                b.toString(),
                c.toString(),
                d.toString(),
                e.toString(),
                h.toString(),
                i.toString()
        );

        Assertions.assertEquals(1, rank1);
        Assertions.assertEquals(1, rank2);
        Assertions.assertTrue(Evaluator.isTied(rank1, rank2));
    }

    @Test
    void testEvaluate7Cards2() {
        // Community cards
        int a = 7 * 4; // 9c
        int b = 2 * 4; // 4c
        int c = 2 * 4 + 3; // 4s
        int d = 7 * 4 + 1; // 9d
        int e = 2 * 4 + 2; // 4h

        // Player 1
        int f = 10 * 4; // Qc
        int g = 4 * 4; // 6c

        // Player 2
        int h = 0; // 2c
        int i = 7 * 4 + 2; // 9h

        short rank1 = Evaluator.evaluate7Cards(a, b, c, d, e, f, g);
        short rank2 = Evaluator.evaluate7Cards(
                Card.getInstance(a),
                Card.getInstance(b),
                Card.getInstance(c),
                Card.getInstance(d),
                Card.getInstance(e),
                Card.getInstance(h),
                Card.getInstance(i)
        );

        Assertions.assertEquals(292, rank1);
        Assertions.assertEquals(236, rank2);
        Assertions.assertTrue(Evaluator.isSecondHandWinning(rank1, rank2));
    }
}
package cz.cuni.mff.java;

import cz.cuni.mff.java.evaluator.HandEvaluator;

public class Main {
    public static void main(String[] args) {


        // Community cards
        int a = 7 * 4 + 0; // 9c
        int b = 2 * 4 + 0; // 4c
        int c = 2 * 4 + 3; // 4s
        int d = 7 * 4 + 1; // 9d
        int e = 2 * 4 + 2; // 4h

        // Player 1
        int f = 10 * 4 + 0; // Qc
        int g = 4 * 4 + 0; // 6c

        // Player 2
        int h = 0 * 4 + 0; // 2c
        int i = 7 * 4 + 2; // 9h

        // Evaluating the hand of player 1
        int rank1 = HandEvaluator.evaluate7Cards(a, b, c, d, e, f, g);
        // Evaluating the hand of player 2
        int rank2 = HandEvaluator.evaluate7Cards(a, b, c, d, e, h, i);

        assert(rank1 == 292);
        assert(rank2 == 236);

        System.out.printf("The rank of the hand in player 1 is %d\n", rank1); // expected 292
        System.out.printf("The rank of the hand in player 2 is %d\n", rank2); // expected 236
        System.out.println("Player 2 has a stronger hand");
    }
}
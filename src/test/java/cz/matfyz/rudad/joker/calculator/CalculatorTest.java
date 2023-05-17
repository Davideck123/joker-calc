package cz.matfyz.rudad.joker.calculator;

import cz.matfyz.rudad.joker.card.Card;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

class CalculatorTest {

    double roundTwoDecimals(double number) {
        return Math.round(number * 100.0) / 100.0;
    }

    void checkOdds(Calculator calculator, double[] expectedWinOdds, double[] expectedTieOdds) {
        calculator.calculate();
        var players = calculator.getPlayers();
        for (int i = 0; i < players.length; ++i) {
            Assertions.assertEquals(
                    expectedWinOdds[i],
                    roundTwoDecimals(100 * players[i].getWinProbability())
            );
            Assertions.assertEquals(
                    expectedTieOdds[i],
                    roundTwoDecimals(100 * players[i].getTieProbability())
            );
        }
    }

    Calculator getCalculator(String[] args) {
        CalculatorArgs calculatorArgs = new CalculatorArgs();
        calculatorArgs.parseArgs(args);
        return new Calculator(calculatorArgs);
    }

    @Test
    void testCalculate() {
        List<Player> players = new ArrayList<>();
        players.add(new Player("Ah", "Kh"));
        players.add(new Player("Qc", "Qd"));
        players.add(new Player("Jc", "8s"));
        players.add(new Player("2s", "3c"));
        players.add(new Player("Ad", "8h"));
        players.add(new Player("3h", "6h"));
        players.add(new Player("Kd", "Tc"));
        players.add(new Player("5c", "5h"));
        players.add(new Player("6s", "9h"));
        players.add(new Player("4h", "Js"));
        List<Card> board = new ArrayList<>();
        board.add(Card.getInstance("4s"));
        board.add(Card.getInstance("7d"));
        board.add(Card.getInstance("Td"));
        board.add(Card.getInstance("7h"));
        List<Card> deadCards = new ArrayList<>();
        deadCards.add(Card.getInstance("2c"));
        deadCards.add(Card.getInstance("3d"));

        Calculator calculator = new Calculator(players, board, deadCards);
        double[] expectedWinOdds = {
                7.69, 42.31, 11.54, 0.00, 0.00, 0.00, 15.38, 7.69, 7.69, 7.69
        };
        double[] expectedTieOdds = new double[expectedWinOdds.length];
        checkOdds(calculator, expectedWinOdds, expectedTieOdds);
    }

    @Test
    void testCalculate2() {
        String[] args = "Ah Kh Qc Qd 7c 7d -b 3d 5c Kc".split(" ");
        Calculator calculator = getCalculator(args);
        double[] expectedWinOdds = {76.41, 13.18, 10.41};
        double[] expectedTieOdds = new double[expectedWinOdds.length];
        checkOdds(calculator, expectedWinOdds, expectedTieOdds);
    }

    @Test
    void testCalculate3() {
        String[] args = "4c 6d 2s Qh".split(" ");
        Calculator calculator = getCalculator(args);
        double[] expectedWinOdds = {43.36, 55.65};
        double[] expectedTieOdds = {0.99, 0.99};
        checkOdds(calculator, expectedWinOdds, expectedTieOdds);
    }

    @Test
    void testCalculate4() {
        String[] args = "2c 7s 6h Jd 3h 2d Qh Kc".split(" ");
        Calculator calculator = getCalculator(args);
        double[] expectedWinOdds = {15.20, 22.88, 12.17, 46.95};
        double[] expectedTieOdds = {2.79, 0.43, 2.79, 0.43};
        checkOdds(calculator, expectedWinOdds, expectedTieOdds);
    }

    @Test
    void testCalculate5() {
        String[] args = "Ac 8s 5c 8h 7d Kd --dead Kh 2c".split(" ");
        Calculator calculator = getCalculator(args);
        double[] expectedWinOdds = {45.69, 16.90, 35.20};
        double[] expectedTieOdds = {2.22, 2.22, 0.29};
        checkOdds(calculator, expectedWinOdds, expectedTieOdds);
    }

    @Test
    void testCalculate6() {
        String[] args = "Td 9s 9h Jc 7s 7d -d 2c Ad 5s 9c 8s 9d 2d Jh Ts 7c 8c 3h".split(" ");
        Calculator calculator = getCalculator(args);
        double[] expectedWinOdds = {20.21, 25.71, 52.78};
        double[] expectedTieOdds = {1.29, 1.29, 0.46};
        checkOdds(calculator, expectedWinOdds, expectedTieOdds);
    }

    @Test
    void testCalculate7() {
        String[] args = "Ac Th Jd Ts --dead 5c Jh 6d 9s --board 3h 5h 8c 9d".split(" ");
        Calculator calculator = getCalculator(args);
        double[] expectedWinOdds = {75, 25};
        double[] expectedTieOdds = {0, 0};
        checkOdds(calculator, expectedWinOdds, expectedTieOdds);
    }

    @Test
    void testCalculate8() {
        String[] args = "9d Th 6d Ts 5c 3d -b 8c 4c Kd 7s 3s -d Kc 2d 3h Tc".split(" ");
        Calculator calculator = getCalculator(args);
        double[] expectedWinOdds = {0, 0, 100};
        double[] expectedTieOdds = {0, 0, 0};
        checkOdds(calculator, expectedWinOdds, expectedTieOdds);
    }

    @Test
    void testCalculate9() {
        String[] args = "Ac Kc Tc Th Kh Jh -b Jc Qh 9c -d 4c 8h 9h 3c 8d 3d 2s 9d".split(" ");
        Calculator calculator = getCalculator(args);
        double[] expectedWinOdds = {45.88, 14.29, 38.82};
        double[] expectedTieOdds = {1.01, 0, 1.01};
        checkOdds(calculator, expectedWinOdds, expectedTieOdds);
    }

    @Test
    void testCalculate10() {
        String[] args = "Ah Kh Qc Qd 7c 7d -b 3d 5c 2d".split(" ");
        Calculator calculator = getCalculator(args);
        double[] expectedWinOdds = {33.67, 55.59, 9.52};
        double[] expectedTieOdds = {1.22, 1.22, 1.22};
        checkOdds(calculator, expectedWinOdds, expectedTieOdds);
    }
}
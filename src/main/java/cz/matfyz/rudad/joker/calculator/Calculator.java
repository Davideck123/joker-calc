package cz.matfyz.rudad.joker.calculator;


import com.google.common.math.IntMath;
import cz.matfyz.rudad.joker.card.Card;
import cz.matfyz.rudad.joker.evaluator.HandEvaluator;
import cz.matfyz.rudad.joker.card.Rank;
import cz.matfyz.rudad.joker.card.Suit;
import picocli.CommandLine;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Calculator {

    private final Player[] players;
    private final Card[] board;
    private final Card[] deadCards;
    private final Card[] deck;

    static final int MIN_PLAYERS = 2;
    static final int MAX_PLAYERS = 10;
    static final int MAX_DEAD_CARDS = 19;
    static final int PRE_FLOP = 0;
    static final int FLOP = 3;
    static final int TURN = 4;
    static final int RIVER = 5;

    public Calculator(List<Player> players) throws IllegalArgumentException {
        this(players, new ArrayList<>(), new ArrayList<>());
    }

    public Calculator(List<Player> players, ArrayList<Card> board) throws IllegalArgumentException {
        this(players, board, new ArrayList<>());
    }

    public Calculator(List<Player> players, List<Card> deadCards) throws IllegalArgumentException {
        this(players, new ArrayList<>(), deadCards);
    }

    public Calculator(CalculatorArgs args) throws IllegalArgumentException {
        this(args.getPlayers(), args.getBoard(), args.getDeadCards());
    }

    public Calculator(List<Player> players, List<Card> board, List<Card> deadCards) throws IllegalArgumentException {
        if (players.size() < MIN_PLAYERS || players.size() > MAX_PLAYERS) {
            throw new IllegalArgumentException("Number of players must be between " + MIN_PLAYERS + " and " + MAX_PLAYERS);
        }
        if (IntStream.of(PRE_FLOP, FLOP, TURN, RIVER).noneMatch(s -> s == board.size())) {
            throw new IllegalArgumentException(String.format("Board must have %d, %d, %d or %d cards", PRE_FLOP, FLOP, TURN, RIVER));
        }
        if (deadCards.size() > MAX_DEAD_CARDS) {
            throw new IllegalArgumentException("Maximum number of dead cards is " + MAX_DEAD_CARDS);
        }
        this.players = players.toArray(Player[]::new);
        this.board = board.toArray(Card[]::new);
        this.deadCards = deadCards.toArray(Card[]::new);
        if (!uniqueCards()) {
            throw new IllegalArgumentException("Cards must be unique");
        }
        this.deck = getDeck();
    }

    private boolean uniqueCards() {
        var allPlayerCards = Arrays.stream(players).flatMap(p -> Arrays.stream(p.getCards())).toArray(Card[]::new);
        int totalSize = allPlayerCards.length + board.length + deadCards.length;
        return Stream.of(allPlayerCards, board, deadCards)
                .flatMap(Arrays::stream)
                .distinct()
                .count() == totalSize;
    }

    private Card[] getDeck() {
        List<Card> deck = new ArrayList<>();
        for (var rank : Rank.values()) {
            for (var suit: Suit.values()) {
                var card = Card.getInstance(rank, suit);
                if (Arrays.stream(players).noneMatch(p -> Arrays.asList(p.getCards()).contains(card))
                        && !Arrays.asList(board).contains(card)
                        && !Arrays.asList(deadCards).contains(card)) {
                    deck.add(card);
                }
            }
        }
        return deck.toArray(Card[]::new);
    }

    private void evaluatePlayers(Card a, Card b, Card c, Card d, Card e) {
        short bestHandValue = Short.MAX_VALUE;
        List<Player> handWinners = new ArrayList<>();
        for (var player : players) {
            var handValue = HandEvaluator.evaluate7Cards(
                    a, b, c, d, e, player.getCards()[0], player.getCards()[1]
            );
            if (handValue == bestHandValue) {
                handWinners.add(player);
            }
            else if (handValue < bestHandValue) {
                bestHandValue = handValue;
                handWinners = new ArrayList<>();
                handWinners.add(player);
            }
        }
        if (handWinners.size() == 1) {
            handWinners.get(0).addWin();
        }
        else {
            handWinners.forEach(Player::addSplit);
        }
    }

    private void calculatePreFlop() {
        IntStream.range(0, deck.length)
                .parallel()
                .forEach(i -> {
                    for (int j = i + 1; j < deck.length; ++j) {
                        for (int k = j + 1; k < deck.length; ++k) {
                            for (int l = k + 1; l < deck.length; ++l) {
                                for (int m = l + 1; m < deck.length; ++m) {
                                    evaluatePlayers(deck[i], deck[j], deck[k], deck[l], deck[m]);
                                }
                            }
                        }
                    }
                });
    }

    private void calculateFlop() {
        IntStream.range(0, deck.length)
                .parallel()
                .forEach(i -> {
                    for (int j = i + 1; j < deck.length; ++j) {
                        evaluatePlayers(deck[i], deck[j], board[0], board[1], board[2]);
                    }
                });
    }

    private void calculateTurn() {
        IntStream.range(0, deck.length)
                .parallel()
                .forEach(i -> {
                    evaluatePlayers(deck[i], board[0], board[1], board[2], board[3]);
                });
    }

    private void calculateRiver() {
        evaluatePlayers(board[0], board[1], board[2], board[3], board[4]);
    }

    public Calculator calculate() {
        switch (board.length) {
            case PRE_FLOP -> calculatePreFlop();
            case FLOP -> calculateFlop();
            case TURN -> calculateTurn();
            case RIVER -> calculateRiver();
            default -> throw new IllegalStateException("Invalid number of board cards: " + board.length);
        }

        int total = IntMath.binomial(deck.length, 5 - board.length);
        for (Player player : players) {
            player.setWinProbability(player.getNumberOfWins() / (double) total);
            player.setSplitProbability(player.getNumberOfSplits() / (double) total);
        }
        return this;
    }

    public void printResults() {
        System.out.printf("        %s    %s\n", "Win", "Split");
        for (Player player : players) {
            System.out.printf("%s: %6.2f%% %6.2f%%\n",
                    player, 100 * player.getWinProbability(), 100 * player.getSplitProbability());
        }
    }

    public static void main(String[] args) {
        //List<Player> players = new ArrayList<>();
        //players.add(new Player("Ah", "Kh"));
        //players.add(new Player("Qc", "Qd"));
        //players.add(new Player("Jc", "8s"));
        //players.add(new Player("2s", "3c"));
        //players.add(new Player("Ad", "8h"));
        //players.add(new Player("3h", "6h"));
        //players.add(new Player("Kd", "Tc"));
        //players.add(new Player("5c", "5h"));
        //players.add(new Player("6s", "9h"));
        //players.add(new Player("4h", "Js"));
        //board.add(Card.getInstance("4s"));
        //board.add(Card.getInstance("7d"));
        //board.add(Card.getInstance("Tc"));
        //board.add(Card.getInstance("7h"));
        //board.add(Card.getInstance("9d"));
        try {
            CalculatorArgs calculatorArgs = new CalculatorArgs(args);
            Calculator calculator = new Calculator(calculatorArgs);
            calculator.calculate().printResults();
        } catch (CommandLine.UnmatchedArgumentException | IllegalArgumentException e) {
        //catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
        /*catch (CommandLine.ParameterException e) {
            System.out.println(e.getMessage());
        }*/
    }
}

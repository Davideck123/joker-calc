package cz.matfyz.rudad.joker.calculator;

import com.google.common.math.IntMath;
import cz.matfyz.rudad.joker.card.Card;
import cz.matfyz.rudad.joker.card.Rank;
import cz.matfyz.rudad.joker.card.Suit;
import cz.matfyz.rudad.joker.evaluator.Evaluator;
import picocli.CommandLine;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * The main class that performs the odds calculations for the Joker calculator application.
 */
public class Calculator {

    static final int MIN_PLAYERS = 2;
    static final int MAX_PLAYERS = 10;
    static final int MAX_DEAD_CARDS = 19;
    static final int PRE_FLOP = 0;
    static final int FLOP = 3;
    static final int TURN = 4;
    static final int RIVER = 5;
    private final Player[] players;
    private final Card[] board;
    private final Card[] deadCards;
    private final Card[] deck;

    /**
     * Creates a Calculator object with the specified players.
     *
     * @param players the list of players
     * @throws IllegalArgumentException if the number of players is invalid
     */
    public Calculator(List<Player> players) throws IllegalArgumentException {
        this(players, new ArrayList<>(), new ArrayList<>());
    }
    /**
     * Creates a Calculator object with the specified players and board cards.
     *
     * @param players the list of players
     * @param board   the list of board cards
     * @throws IllegalArgumentException if the number of players or board cards is invalid
     */
    public Calculator(List<Player> players, ArrayList<Card> board) throws IllegalArgumentException {
        this(players, board, new ArrayList<>());
    }
    /**
     * Creates a Calculator object with the specified players and dead cards.
     *
     * @param players   the list of players
     * @param deadCards the list of dead cards
     * @throws IllegalArgumentException if the number of players or dead cards is invalid
     */
    public Calculator(List<Player> players, List<Card> deadCards) throws IllegalArgumentException {
        this(players, new ArrayList<>(), deadCards);
    }

    /**
     * Creates a Calculator object based on the specified CalculatorArgs object.
     *
     * @param args the CalculatorArgs object containing the command-line arguments
     * @throws IllegalArgumentException if the arguments are invalid
     */
    public Calculator(CalculatorArgs args) throws IllegalArgumentException {
        this(args.getPlayers(), args.getBoardCards(), args.getDeadCards());
    }

    /**
     * Creates a Calculator object with the specified players, board cards, and dead cards.
     *
     * @param players   the list of players
     * @param board     the list of board cards
     * @param deadCards the list of dead cards
     * @throws IllegalArgumentException if the number of players, board cards, or dead cards is invalid,
     *                                  or if the cards are not unique
     */
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

    /**
     * The entry point for the Joker Calculator application.
     *
     * @param args the command-line arguments
     */
    public static void main(String[] args) {
        CalculatorArgs calculatorArgs = new CalculatorArgs();
        try {
            calculatorArgs.parseArgs(args);
            if (calculatorArgs.getCommandLine().isUsageHelpRequested()) {
                calculatorArgs.getCommandLine().usage(System.out);
                return;
            }
            if (calculatorArgs.getCommandLine().isVersionHelpRequested()) {
                calculatorArgs.getCommandLine().printVersionHelp(System.out);
                return;
            }
            // Set whether to print emojis for suits or not
            Card.setPrintSuitEmojis(calculatorArgs.shouldPrintEmojis());

            Calculator calculator = new Calculator(calculatorArgs);
            calculator.calculate().printOdds();
        } catch (CommandLine.PicocliException | IllegalArgumentException e) {
            System.out.println(e.getMessage());
            calculatorArgs.getCommandLine().usage(System.out);
        }
    }

    /**
     * Returns the array of players in the calculator.
     *
     * @return an array of players
     */
    public Player[] getPlayers() {
        return players;
    }

    /**
     * Returns the array of board cards in the calculator.
     *
     * @return an array of board cards
     */
    public Card[] getBoard() {
        return board;
    }

    /**
     * Returns the array of dead cards in the calculator.
     *
     * @return an array of dead cards
     */
    public Card[] getDeadCards() {
        return deadCards;
    }

    /**
     * Checks if the cards are unique.
     *
     * @return true if the cards are unique, false otherwise
     */
    private boolean uniqueCards() {
        var allPlayerCards = Arrays.stream(players).flatMap(p -> Arrays.stream(p.getCards())).toArray(Card[]::new);
        int totalSize = allPlayerCards.length + board.length + deadCards.length;
        return Stream.of(allPlayerCards, board, deadCards).flatMap(Arrays::stream).distinct().count() == totalSize;
    }

    /**
     * Retrieves the remaining cards in the deck.
     *
     * @return the remaining cards in the deck
     */
    private Card[] getDeck() {
        List<Card> deck = new ArrayList<>();
        Set<Card> usedCards = Arrays.stream(players)
                .flatMap(player -> Arrays.stream(player.getCards()))
                .collect(Collectors.toCollection(HashSet::new));
        usedCards.addAll(Arrays.asList(board));
        usedCards.addAll(Arrays.asList(deadCards));

        for (var rank : Rank.values()) {
            for (var suit : Suit.values()) {
                var card = Card.getInstance(rank, suit);
                if (!usedCards.contains(card)) {
                    deck.add(card);
                }
            }
        }
        return deck.toArray(Card[]::new);
    }

    /**
     * Evaluates the players' hands for the given set of cards.
     *
     * @param a the first card
     * @param b the second card
     * @param c the third card
     * @param d the fourth card
     * @param e the fifth card
     */
    private void evaluatePlayers(Card a, Card b, Card c, Card d, Card e) {
        short bestHandValue = Short.MAX_VALUE;
        List<Player> handWinners = new ArrayList<>();
        for (var player : players) {
            var handValue = Evaluator.evaluate7Cards(
                    a, b, c, d, e, player.getCards()[0], player.getCards()[1]
            );
            if (Evaluator.isTied(handValue, bestHandValue)) {
                handWinners.add(player);
            } else if (Evaluator.isFirstHandWinning(handValue, bestHandValue)) {
                bestHandValue = handValue;
                handWinners = new ArrayList<>();
                handWinners.add(player);
            }
        }
        if (handWinners.size() == 1) {
            handWinners.get(0).addWin();
        } else {
            handWinners.forEach(Player::addTie);
        }
    }

    /**
     * Calculates pre-flop probabilities.
     */
    private void calculatePreFlop() {
        IntStream.range(0, deck.length).parallel().forEach(i -> {
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

    /**
     * Calculates flop probabilities.
     */
    private void calculateFlop() {
        IntStream.range(0, deck.length).parallel().forEach(i -> {
            for (int j = i + 1; j < deck.length; ++j) {
                evaluatePlayers(deck[i], deck[j], board[0], board[1], board[2]);
            }
        });
    }

    /**
     * Calculates turn probabilities.
     */
    private void calculateTurn() {
        IntStream.range(0, deck.length).parallel().forEach(
                i -> evaluatePlayers(deck[i], board[0], board[1], board[2], board[3])
        );
    }

    /**
     * Calculates river probabilities.
     */
    private void calculateRiver() {
        evaluatePlayers(board[0], board[1], board[2], board[3], board[4]);
    }

    /**
     * Performs the calculation of the probabilities of winning and tying for each player.
     *
     * @return the Calculator object for method chaining
     * @throws IllegalStateException if the number of board cards is invalid
     */
    public Calculator calculate() {
        switch (board.length) {
            case PRE_FLOP -> calculatePreFlop();
            case FLOP -> calculateFlop();
            case TURN -> calculateTurn();
            case RIVER -> calculateRiver();
            default -> throw new IllegalStateException("Invalid number of board cards: " + board.length);
        }

        int totalNumberOfHands = IntMath.binomial(deck.length, 5 - board.length);
        for (Player player : players) {
            player.setWinProbability(totalNumberOfHands);
            player.setTieProbability(totalNumberOfHands);
        }
        return this;
    }

    /**
     * Prints a summary of the odds of winning and tying for each player.
     */
    public void printOdds() {
        System.out.printf("        %s     %s\n", "Win", "Tie");
        for (Player player : players) {
            System.out.printf("%s: %6.2f%% %6.2f%%\n", player, 100 * player.getWinProbability(), 100 * player.getTieProbability());
        }
    }
}

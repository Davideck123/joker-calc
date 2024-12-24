package cz.matfyz.rudad.joker.calculator;

import cz.matfyz.rudad.joker.card.Card;
import picocli.CommandLine;
import picocli.CommandLine.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents the command-line arguments for the Joker calculator application.
 */
@Command(name = "joker", mixinStandardHelpOptions = true, version = "joker-calc 1.0.0")
class CalculatorArgs {
    private final List<Player> players = new ArrayList<>();

    @Parameters(
            converter = CardConverter.class,
            arity = "2",
            description = "Player cards (2 cards per player, "
                    + Calculator.MIN_PLAYERS + " to "
                    + Calculator.MAX_PLAYERS + " players).",
            paramLabel = "<card>"
    )
    private final List<Card> playerCards = new ArrayList<>();

    @Option(
            names = {"-b", "--board"},
            converter = CardConverter.class,
            arity = Calculator.FLOP + ".." + Calculator.RIVER,
            description = "Board cards - flop, turn or river ("
                    + Calculator.FLOP + " to "
                    + Calculator.RIVER + " cards).",
            paramLabel = "<card>",
            hideParamSyntax = true
    )
    private final List<Card> boardCards = new ArrayList<>();

    @Option(
            names = {"-d", "--dead"},
            converter = CardConverter.class,
            arity = "1.." + Calculator.MAX_DEAD_CARDS,
            description = "Cards that are no longer in the deck (1 to "
                    + Calculator.MAX_DEAD_CARDS + " cards).",
            paramLabel = "<card>",
            hideParamSyntax = true
    )
    private final List<Card> deadCards = new ArrayList<>();

    private final CommandLine commandLine = new CommandLine(this);

    /**
     * Creates player objects based on the provided player cards.
     */
    private void createPlayers() {
        // it's guaranteed that playerCards.size() is even due to the arity of the playerCards parameter
        for (int i = 0; i < playerCards.size(); i += 2) {
            players.add(new Player(playerCards.get(i), playerCards.get(i + 1)));
        }
    }

    /**
     * Parses the command-line arguments and fills the players, boardCards and deadCards.
     *
     * @param args the command-line arguments
     * @throws ParameterException        if the command-line arguments are invalid
     * @throws IllegalArgumentException if the player cards cannot be created
     */
    void parseArgs(String[] args) throws ParameterException, IllegalArgumentException {
        commandLine.parseArgs(args);
        createPlayers();
    }

    /**
     * Returns the list of players.
     *
     * @return the list of player objects
     */
    List<Player> getPlayers() {
        return players;
    }

    /**
     * Returns the list of board cards.
     *
     * @return the list of board cards
     */
    List<Card> getBoardCards() {
        return boardCards;
    }

    /**
     * Returns the list of dead cards.
     *
     * @return the list of dead cards
     */
    List<Card> getDeadCards() {
        return deadCards;
    }

    /**
     * Returns the underlying CommandLine object for advanced usage.
     *
     * @return the CommandLine object
     */
    CommandLine getCommandLine() {
        return commandLine;
    }
}

/**
 * A custom converter for converting a String representation of a card to a Card object.
 */
class CardConverter implements ITypeConverter<Card> {

    /**
     * Converts the provided String value to a Card object.
     *
     * @param value the String representation of the card
     * @return the Card object
     * @throws Exception if an error occurs during the conversion
     */
    @Override
    public Card convert(String value) throws Exception {
        return Card.getInstance(value);
    }
}

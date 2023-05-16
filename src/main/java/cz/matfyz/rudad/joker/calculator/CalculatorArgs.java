package cz.matfyz.rudad.joker.calculator;

import cz.matfyz.rudad.joker.card.Card;
import picocli.CommandLine;
import picocli.CommandLine.*;

import java.util.ArrayList;
import java.util.List;

@Command(name = "joker", mixinStandardHelpOptions = true, version = "1.0")
public class CalculatorArgs {

    //@Parameters(paramLabel = "players", converter = PlayerConverter.class, split = ",")
    private final List<Player> players = new ArrayList<>();

    @Parameters(converter = CardConverter.class, arity = "2", split = ",")
    private final List<Card> playerCards = new ArrayList<>();

    @Option(names = { "-b", "--board" }, converter = CardConverter.class, split = ",", description = "Board cards")
    private final List<Card> board = new ArrayList<>();

    @Option(names = { "-d", "--dead" }, converter = CardConverter.class, split = ",")
    private final List<Card> deadCards = new ArrayList<>();

    //@Option(names = { "-h", "--help" }, usageHelp = true, description = "Show this help")
    //private boolean helpRequested;

    CalculatorArgs(String[] args) throws ParameterException, IllegalArgumentException {
        new CommandLine(this).parseArgs(args);
        for (int i = 0; i < playerCards.size(); i += 2) {
            players.add(new Player(playerCards.get(i), playerCards.get(i + 1)));
        }
        CommandLine.usage(this, System.out);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public List<Card> getBoard() {
        return board;
    }

    public List<Card> getDeadCards() {
        return deadCards;
    }
}

class CardConverter implements ITypeConverter<Card> {

    @Override
    public Card convert(String value) throws Exception {
        return Card.getInstance(value);
    }
}

//class PlayerConverter implements ITypeConverter<Player> {
//
//    @Override
//    public Player convert(String value) throws Exception {
//        if (value.length() != 4) {
//            throw new IllegalArgumentException("Player must have exactly 2 cards");
//        }
//        System.out.println(value.substring(0, 2));
//        return new Player(value.substring(0, 2), value.substring(2, 4));
//    }
//}

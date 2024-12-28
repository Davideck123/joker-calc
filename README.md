# 🃏 JOKER - Java pOKER Calculator

[![Javadoc](https://img.shields.io/badge/JavaDoc-Online-green)](https://davideck123.github.io/joker-calc/docs)

**Joker calculator** is a 7-card Texas Hold'em poker odds calculator and evaluator.  
The evaluator is a Java port of [PokerHandEvaluator](https://github.com/HenryRLee/PokerHandEvaluator) by [HenryRLee](https://github.com/HenryRLee), 
which contains [a detailed explanation](https://github.com/HenryRLee/PokerHandEvaluator/blob/master/Documentation/Algorithm.md) of the hand evaluation algorithm.
The calculator then evaluates all combinations of cards that are left in the deck, and calculates the probability of each player winning/splitting.

## Build
To use the joker calculator from the command line, simply run `mvn clean package` to build the jar and then you can use the `./joker` command in the top level directory.

## Usage
Cards are specified as card aliases (two character strings), where the first character is the rank 
`2, 3, 4, 5, 6, 7, 8, 9, T, J, Q, K, A` and the second character is the suit `c, d, h, s` for clubs, diamonds, hearts and spades respectively.  

Specify the players' cards as space seperated card aliases, e.g. `Ah 4d` for A♥️ (Ace of Hearts) and 4♦️ (Four of Diamonds), 
`2c Ts` for 2♣️ (Deuce of Clubs) and 10♠️ (Ten of Spades), etc.

Optionally specify the board cards as space seperated card aliases, e.g. `-b 2c Ts 4d 5h` 
for 2♣️ (Deuce of Clubs), 10♠️ (Ten of Spades), 4♦️ (Four of Diamonds) and 5♥️ (Five of Hearts) as the turn etc.

Optionally specify the dead cards (the cards that are no longer in the deck) as space 
seperated card aliases, e.g. `-d Tc 2c` for 10♣️ (Ten of Clubs) and 2♣️ (Deuce of Clubs).

```
./joker -h
Usage: joker [-ehV] [-b=<card>]... [-d=<card>]... (<card> <card>)...
      (<card> <card>)...   Player cards (2 cards per player, 2 to 10 players).
  -b, --board=<card>       Board cards - flop, turn or river (3 to 5 cards).
  -d, --dead=<card>        Cards that are no longer in the deck (1 to 19 cards).
  -e, --emoji              Display emojis for suits instead of letters.
  -h, --help               Show this help message and exit.
  -V, --version            Print version information and exit.
```

Also, you can use the `-e` flag to display emojis for suits instead of letters.
```
./joker Jh 9s Ac 2d --emoji
        Win     Tie
J♥️9♠️:  43,82%   0,43%
A♣️2♦️:  55,75%   0,43%
```

## Examples

Player 1 has A♥️ (Ace of Hearts) and K️♥️ (King of Hearts),  
player 2 has Q♣️ (Queen of Clubs) and Q♦️ (Queen of Diamonds),  
player 3 has 7♣️ (Seven of Clubs) and 7♦️ (Seven of Diamonds)  
and the board is 3♦️ (Three of Diamonds), 5♣️ (Five of Clubs) and K♣️ (King of Clubs).  
The scenario is represented as follows: `Ah Kh Qc Qd 7c 7d -b 3d 5c Kc`  

```
./joker Ah Kh Qc Qd 7c 7d -b 3d 5c Kc
        Win     Tie
AhKh:  76.41%   0.00%
QcQd:  13.18%   0.00%
7c7d:  10.41%   0.00%
```

Player 1 has A♣️ (Ace of Clubs) and 8♠️ (Eight of Spades),  
player 2 has 5♣️ (Five of Clubs) and 8♥️ (Eight of Hearts),  
player 3 has 7♦️ (Seven of Diamonds) and K♦️ (King of Diamonds)  
and the dead cards are K♥️ (King of Hearts) and 2♣️ (Deuce of Clubs).  
The scenario is represented as follows: `Ac 8s 5c 8h 7d Kd -d Kh 2c`

```
./joker Ac 8s 5c 8h 7d Kd -d Kh 2c
        Win     Tie
Ac8s:  45.69%   2.22%
5c8h:  16.90%   2.22%
7dKd:  35.20%   0.29%
```

## API docs
Javadoc available [HERE](https://davideck123.github.io/joker-calc/docs)

## Resources
Some interesting resources I found while studying the poker hand evaluation algorithms:  
**[PokerHandEvaluator/Algorithm.md](https://github.com/HenryRLee/PokerHandEvaluator/blob/master/Documentation/Algorithm.md) - The hand evaluation algorithm used in this project with super detailed explanation**  
[Enumerating Five-Card Poker Hands](http://suffe.cool/poker/7462.html)  
[Cactus Kev's Poker Hand Evaluator](http://suffe.cool/poker/evaluator.html)  
[The Great Poker Hand Evaluator Roundup](https://www.codingthewheel.com/archives/poker-hand-evaluator-roundup/)  

Some GUI-based evaluators I used to check the odds:  
[Poker Master Tool](https://pokermastertool.bartoszputek.pl/) ([Github](https://github.com/bartoszputek/poker-master-tool))  
[Texas Hold'em Odds Calculator](https://www.cardplayer.com/poker-tools/odds-calculator/texas-holdem)  

Other poker evaluators:  
[PokerHandEvaluator](https://github.com/HenryRLee/PokerHandEvaluator)  
[TwoPlusTwoHandEvaluator](https://github.com/tangentforks/TwoPlusTwoHandEvaluator)  
[SKPokerEval](https://github.com/kennethshackleton/SKPokerEval)  
[OMPEval](https://github.com/zekyll/OMPEval)  
[Java 5-card evaluator](https://github.com/jmp/poker-hand-evaluator)  
[Collection of poker evaluators](https://github.com/tangentforks/XPokerEval)  

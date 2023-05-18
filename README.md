# 🃏 Joker - (Java) Poker Calculator

**Joker calculator** is a 7-card Texas Hold'em poker odds calculator and evaluator.  
The evaluator is a Java port of [PokerHandEvaluator](https://github.com/HenryRLee/PokerHandEvaluator) by [HenryRLee](https://github.com/HenryRLee), 
which contains [a detailed explanation](https://github.com/HenryRLee/PokerHandEvaluator/blob/master/Documentation/Algorithm.md) of the hand evaluation algorithm.
The calculator then evaluates all combinations of cards that are left in the deck, and calculates the probability of each player winning/splitting.

## Usage
Cards are specified as card aliases (two character strings), where the first character is the rank 
`2, 3, 4, 5, 6, 7, 8, 9, T, J, Q, K, A` and the second character is the suit `c, d, h, s` for clubs, diamonds, hearts and spades respectively.  

Specify the players' cards as space seperated card aliases, e.g. `Ah 4d` for A♥️ (Ace of Hearts) and 4♦️ (Four of Diamonds), 
`2c Ts` for 2♣️ (Two of Clubs) and T♠️ (Ten of Spades), etc.

Optionally specify the board cards as space seperated card aliases, e.g. `-b 2c Ts 4d 5h` 
for 2♣️ (Two of Clubs), T♠️ (Ten of Spades), 4♦️ (Four of Diamonds) and 5♥️ (Five of Hearts) as the turn etc.

Optionally specify the dead cards (the cards that are no longer in the deck) as space 
seperated card aliases, e.g. `-d 10c 2c` for 10♣️ (Ten of Clubs) and 2♣️ (Two of Clubs).

```
Usage: joker [-hV] [-b=<card>]... [-d=<card>]... (<card> <card>)...
      (<card> <card>)...   Player cards (2 cards per player, 2 to 10 players).
  -b, --board=<card>       Board cards - flop, turn or river (3 to 5 cards).
  -d, --dead=<card>        Cards that are no longer in the deck (1 to 19 cards).
  -h, --help               Show this help message and exit.
  -V, --version            Print version information and exit.
```

## Examples

Player 1 has A♥️ (Ace of Hearts) and K️♥️ (King of Hearts), player 2 has Q♣️ (Queen of Clubs) 
and Q♦️ (Queen of Diamonds), player 3 has 7♣️ (Seven of Clubs) and 7♦️ (Seven of Diamonds) and 
the board is 3♦️ (Three of Diamonds), 5♣️ (Five of Clubs) and K♣️ (King of Clubs).
The scenario is represented as follows: `Ah Kh Qc Qd 7c 7d -b 3d 5c Kc`  

```
java -cp ... cz.matfyz.rudad.joker.calculator.Calculator Ah Kh Qc Qd 7c 7d -b 3d 5c Kc
        Win     Tie
AhKh:  76,41%   0,00%
QcQd:  13,18%   0,00%
7c7d:  10,41%   0,00%
```

Player 1 has A♣️ (Ace of Clubs) and 8♠️ (Eight of Spades), player 2 has 5♣️ (Five of Clubs) 
and 8♥️ (Eight of Hearts), player 3 has 7♦️ (Seven of Diamonds) and K♦️ (King of Diamonds) and 
the dead cards are K♥️ (King of Hearts) and 2♣️ (Two of Clubs).
The scenario is represented as follows: `Ac 8s 5c 8h 7d Kd -d Kh 2c`

```
java -cp ... cz.matfyz.rudad.joker.calculator.Calculator Ac 8s 5c 8h 7d Kd -d Kh 2c
        Win     Tie
Ac8s:  45,69%   2,22%
5c8h:  16,90%   2,22%
7dKd:  35,20%   0,29%
```



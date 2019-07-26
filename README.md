# Othello AI

An instance of the board game, Othello, in which an artificial intelligence can be tasked to compete with the player or another AI using Minimax or Alpha-Beta Pruning algorithms. The board itself is stored as a string variable with a series of the characters 1, 2, or 0, with 1 and 2 being used to represent each player's game pieces on the board. An example of this variable and one possible board setup is as follows.

```
  "00000000;00000000;00000000;00012000;00021000;00000000;00000000;00000000;1"
  
                              We will interperet this string as such:
                                   
                                   8x8 | A || B || C || D || E || F || G || H |
                                   ---------------------------------------------
                                   | a || 0 || 0 || 0 || 0 || 0 || 0 || 0 || 0 |
                                   ---------------------------------------------
                                   | b || 0 || 0 || 0 || 0 || 0 || 0 || 0 || 0 |
                                   ---------------------------------------------
                                   | c || 0 || 0 || 0 || 0 || 0 || 0 || 0 || 0 |
                                   ---------------------------------------------
                                   | d || 0 || 0 || 0 || 1 || 2 || 0 || 0 || 0 |
                                   ---------------------------------------------
                                   | e || 0 || 0 || 0 || 2 || 1 || 0 || 0 || 0 |
                                   ---------------------------------------------
                                   | f || 0 || 0 || 0 || 0 || 0 || 0 || 0 || 0 |
                                   ---------------------------------------------
                                   | g || 0 || 0 || 0 || 0 || 0 || 0 || 0 || 0 |
                                   ---------------------------------------------
                                   | h || 0 || 0 || 0 || 0 || 0 || 0 || 0 || 0 |  
```

## Concepts Used

* Java
* Minimax Algorithm
* Alpha Beta Pruning

## Tests

All test cases can be found in the provided [link](/Testing)
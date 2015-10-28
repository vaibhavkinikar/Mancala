# Mancala
Developed a program using Adversarial Search (Greedy, MiniMax, Alpha-Beta pruning) algorithms to determine the next move for a player in the Mancala Game. 

The rules of the Mancala game can be found at https://en.wikipedia.org/wiki/Mancala [1] and you can also try playing it online at http://play-mancala.com/ [2] to get a better understanding of the game.

Input:
You are provided with a file input.txt that describes the current state of the game.
<Task#> Greedy=1, MiniMax=2, Alpha-Beta=3, Competition=4
<Your player: 1 or 2>
<Cutting off depth>
<Board state for player-2>
<Board state for player-1>
<#stones in player-2’s mancala>
<#stones in player-1’s mancala>
Example:
Input:
1
1
2
2 2 2 2
2 2 2 2
0
0

Output:
2 2 0 2
0 3 0 3
0
4
Line-1 represents the board state for player-2, i.e. the upper side of the board. Each number is separated by a single white space.
Line-2 represents the board state for player-1, i.e. the upper side of the board. Each number is separated by a single white space.
Line-3 gives you the number of stones in player-2’s mancala.
Line-4 gives you the number of stones in player-1’s mancala.

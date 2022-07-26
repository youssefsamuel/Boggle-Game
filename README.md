# Boggle-Game
Programming Assignment 3/ Algorithms Part 2, Princeton.
Language Used: JAVA.
Data Structures used: R-Way Tries, Queues, 2D-Arrays.

The Boggle Game consists of a board mxn, each cell is dice that has a letter printed on it. Initially all the dices are randomly distributed, and the players compete to get the maximum number of words from this board, according to these rules:
A valid word must be composed by following a sequence of adjacent dice—two dice are adjacent if they are horizontal, vertical, or diagonal neighbors.
A valid word can use each die at most once.
A valid word must contain at least 3 letters.
A valid word must be in the dictionary (which typically does not contain proper nouns, and all words consist of letters (A-Z)).

The goal is to create a boggle solver class that solve this problem and get the maximum score in the game.
To solve this problem, I stored all the words of the dictionary in a trie, for fast search and insert operations.
The value of a key in a trie is the score that the player will get when he finds this word, which depend on its length.
Then, to get the valid words, the board is checked letter by letter, for each letter a depth first search is done to get all the paths to form words starting from this letter.
The word formed at each step is checked if there is a word in the dictionary that start with this prefix. If there is no word that starts with this prefix, that path is no longer useful.
Once a word is found, it is added to another trie that contains the valid words.

Finally, the maximum score is computed by getting the sum of the scores of all the valid words using the following table:
length     score
3–4		     1
5		       2
6		       3
7		       5
8+		     11

It has to be noted that I wrote the full code of BoggleSolver.Java and Trie.Java, but the other 2 classes are already given in the problem.
For more specifications of the problem: https://coursera.cs.princeton.edu/algs4/assignments/boggle/specification.php

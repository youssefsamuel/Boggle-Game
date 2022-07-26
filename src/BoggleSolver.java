import java.util.ArrayList;
import java.util.List;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class BoggleSolver {
    private final Trie trie;

    // Store the dictionary in a R-way trie, for fast search and insert.
    // The value of a key is its score.
    // Each word in the dictionary contains only the uppercase letters A through Z.
    // R = 26.
    public BoggleSolver(String[] dictionary) {
        if (dictionary == null)
            throw new IllegalArgumentException();
        trie = new Trie();
        for (String x : dictionary) {
            if (x == null)
                throw new IllegalArgumentException();
            int point = myScore(x);
            if (point != 0) // no need to store a word whose length is less than 3 because it will not be counted in the game.
                trie.put(x, point);
        }
    }

    private int myScore(String word) {
        int length = word.length();
        if (length == 3 || length == 4)
            return 1;
        if (length == 5)
            return 2;
        if (length == 6)
            return 3;
        if (length == 7)
            return 5;
        if (length >= 8)
            return 11;
        return 0; // if the length is less than 3.
    }

    // Returns the set of all valid words in the given Boggle board, as an Iterable.
    public Iterable<String> getAllValidWords(BoggleBoard board) {
        if (board == null)
            throw new IllegalArgumentException();
        Trie valid = new Trie(); // this trie is used to store the valid words.
        // for every character in the board do a depth first search to check all the valid words that can be formed starting from this letter.
        for (int i = 0; i < board.rows(); i++) {
            for (int j = 0; j < board.cols(); j++) {
                StringBuilder builder = new StringBuilder();
                boolean[][] marked = new boolean[board.rows()][board.cols()];
                dfs(i, j, valid, board, builder, marked);
            }
        }
        return valid.keys();
    }

    private void dfs(int i, int j, Trie valid, BoggleBoard board, StringBuilder prefix, boolean[][] marked) {
        //mark the new letter checked.
        marked[i][j] = true;
        char c = board.getLetter(i, j);
        prefix.append(c); // the prefix currently checked.
        if (c == 'Q') // all the words used in this game that have a letter Q is followed by a letter U.
            prefix.append('U');
        String x = prefix.toString();
        // if the prefix is in the dictionary and its length is greater than 2, put it with the valid words.
        if (trie.contains(x) && prefix.length() > 2) {
            if (!valid.contains(x))
                valid.put(x, 1);
        }
        // only do the DFS if the dictionary contains a words that has the prefix constructed till now.
        if (trie.hasPrefix(x)) {
            // The following are all the cases in the board where we can get the adjacent of the current letter.
            if (i - 1 >= 0) {
                if (!marked[i - 1][j]) {
                    dfs(i - 1, j, valid, board, prefix, marked);
                }
            }
            if (j + 1 < board.cols()) {
                if (!marked[i][j + 1]) {
                    dfs(i, j + 1, valid, board, prefix, marked);
                }
            }
            if (j - 1 >= 0) {
                if (!marked[i][j - 1]) {
                    dfs(i, j - 1, valid, board, prefix, marked);
                }
            }
            if (i + 1 < board.rows()) {
                if (!marked[i + 1][j]) {
                    dfs(i + 1, j, valid, board, prefix, marked);
                }
            }
            if (i - 1 >= 0 && j - 1 >= 0) {
                if (!marked[i - 1][j - 1]) {
                    dfs(i - 1, j - 1, valid, board, prefix, marked);
                }
            }
            if (i - 1 >= 0 && j + 1 < board.cols()) {
                if (!marked[i - 1][j + 1]) {
                    dfs(i - 1, j + 1, valid, board, prefix, marked);
                }
            }
            if (j + 1 < board.cols() && i + 1 < board.rows()) {
                if (!marked[i + 1][j + 1]) {
                    dfs(i + 1, j + 1, valid, board, prefix, marked);
                }
            }
            if (j - 1 >= 0 && i + 1 < board.rows()) {
                if (!marked[i + 1][j - 1]) {
                    dfs(i + 1, j - 1, valid, board, prefix, marked);
                }
            }
        }
        // After finishing the last letter is removed from the prefix.
        // If the deleted letter is a 'U' that we have to delete the letter before it in case it is a 'Q'.
        prefix.deleteCharAt(prefix.length() - 1);
        if (prefix.length() > 0)
            if (prefix.charAt(prefix.length() - 1) == 'Q')
                prefix.deleteCharAt(prefix.length() - 1);
        marked[i][j] = false; 
        return;
    }

    // Returns the score of the given word if it is in the dictionary, zero
    // otherwise.
    // (You can assume the word contains only the uppercase letters A through Z.)
    public int scoreOf(String word) {
        if (word == null)
            throw new IllegalArgumentException();
        if (trie.contains(word))
            return trie.get(word);
        return 0;
    }

    public static void main(String[] args) {
        In in = new In("dictionary-algs4.txt");
        List<String> d = new ArrayList<>();
        int numWords = 0;
        while (in.hasNextLine()) {
            d.add(in.readLine());
            numWords++;
        }
        String[] dict = new String[numWords];
        for (int i = 0; i < numWords; i++) {
            dict[i] = d.get(i);
        }

        BoggleSolver bs = new BoggleSolver(dict);
        String filename = "board-q.txt";

        BoggleBoard board = new BoggleBoard(filename);
        System.out.println(board);
        int score = 0;
        for (String word : bs.getAllValidWords(board)) {
            StdOut.println(word);
            score += bs.scoreOf(word);
        }
        StdOut.println("Score = " + score);
    }
}

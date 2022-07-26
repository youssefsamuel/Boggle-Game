import edu.princeton.cs.algs4.Queue;

public class Trie {
    private static final int R = 26; // A-Z

    private Node root; // root of trie
    private int n; // number of keys in trie

    // R-way trie node
    private class Node {
        private int val;
        private Node[] next = new Node[R];
    }

    /**
     * Initializes an empty string symbol table.
     */
    public Trie() {
    }

    /**
     * Returns the value associated with the given key.
     * 
     * @param key the key
     * @return the value associated with the given key if the key is in the symbol
     *         table and {@code null} if the key is not in the symbol table
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public int get(String key) {
        if (key == null)
            throw new IllegalArgumentException("argument to get() is null");
        Node x = get(root, key, 0);
        if (x == null)
            return 0;
        return x.val;
    }
    private Node get(Node x, String key, int d) {
        if (x == null)
            return null;
        if (d == key.length())
            return x;
        char c = key.charAt(d);
        int c2 = c - 65;
        return get(x.next[c2], key, d + 1);
    }

    public Iterable<String> keys() {
        return keysWithPrefix("");
    }

    public Iterable<String> keysWithPrefix(String prefix) {
        Queue<String> results = new Queue<String>();
        Node x = get(root, prefix, 0);
        collect(x, new StringBuilder(prefix), results);
        return results;
    }

    private void collect(Node x, StringBuilder prefix, Queue<String> results) {
        if (x == null)
            return;
        if (x.val != 0)
            results.enqueue(prefix.toString());
        for (char c = 0; c < R; c++) {
            int c2 = c + 65;
            prefix.append((char) c2);
            collect(x.next[c], prefix, results);
            prefix.deleteCharAt(prefix.length() - 1);
        }
    }

    public boolean hasPrefix(String prefix) {
        Node x = get(root, prefix, 0);
        if (x == null)
            return false;
        return true;
    }


    /**
     * Does this symbol table contain the given key?
     * 
     * @param key the key
     * @return {@code true} if this symbol table contains {@code key} and
     *         {@code false} otherwise
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public boolean contains(String key) {
        if (key == null)
            throw new IllegalArgumentException("argument to contains() is null");
        return get(key) != 0;
    }



    /**
     * Inserts the key-value pair into the symbol table, overwriting the old value
     * with the new value if the key is already in the symbol table. If the value is
     * {@code null}, this effectively deletes the key from the symbol table.
     * 
     * @param key the key
     * @param val the value
     * @throws IllegalArgumentException if {@code key} is {@code null}
     */
    public void put(String key, int val) {
        if (key == null)
            throw new IllegalArgumentException("first argument to put() is null");
        root = put(root, key, val, 0);
    }

    private Node put(Node x, String key, int val, int d) {
        if (x == null)
            x = new Node();
        if (d == key.length()) {
            if (x.val == 0)
                n++;
            x.val = val;
            return x;
        }
        char c = key.charAt(d);
        int c2 = c - 65;
        x.next[c2] = put(x.next[c2], key, val, d + 1);
        return x;
    }

    /**
     * Returns the number of key-value pairs in this symbol table.
     * 
     * @return the number of key-value pairs in this symbol table
     */
    public int size() {
        return n;
    }

    /**
     * Is this symbol table empty?
     * 
     * @return {@code true} if this symbol table is empty and {@code false}
     *         otherwise
     */
    public boolean isEmpty() {
        return size() == 0;
    }
}

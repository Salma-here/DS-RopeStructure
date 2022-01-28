public class TrieNode {
    private boolean isWord;
    private final int SIZE = 26;
    private char character;
    private final TrieNode[] children = new TrieNode[SIZE];
    private int rep = 0;

    public TrieNode() {
        isWord = false;
        for (int i = 0; i < SIZE; i++)
            children[i] = null;
    }

    public boolean isWord() {
        return isWord;
    }

    public void setWord(boolean word) {
        isWord = word;
    }

    public char getCharacter() {
        return character;
    }

    public void setCharacter(char character) {
        this.character = character;
    }

    public TrieNode[] getChildren() {
        return children;
    }

    public int getRep() {
        return rep;
    }

    public void addRep() {
        if (isWord)
            rep++;
    }
}
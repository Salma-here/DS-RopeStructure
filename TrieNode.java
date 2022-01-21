public class TrieNode {
    private boolean isWord;
    private final int SIZE = 26;
    private char character;
    private TrieNode[] children = new TrieNode[SIZE];

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

    public void setChildren(TrieNode[] children) {
        this.children = children;
    }
}

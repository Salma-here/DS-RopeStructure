public class Trie {
    private TrieNode root;

    public Trie(String[] words) {
        root = new TrieNode();
        root.setCharacter('_');
        root.setWord(false);
        for (int i = 0; i < words.length; i++)
            insert(words[i]);
    }


    public void insert(String word) {
        TrieNode temp = root;
        for (int i = 0; i < word.length(); i++) {
            int index = word.charAt(i) - 'a';
            if (temp.getChildren()[index] == null)
                temp.getChildren()[index] = new TrieNode();
            temp = temp.getChildren()[index];
        }
        temp.setWord(true);
    }
    

    public void autocomplete(char c) {
        StringBuffer buffer = new StringBuffer();
        int index = c - 'a';
        //TODO
    }
}

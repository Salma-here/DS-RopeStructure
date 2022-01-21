import java.util.ArrayList;
import java.util.List;

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
            temp.setCharacter(word.charAt(i));
        }
        temp.setWord(true);
    }


    public void autocomplete(char c) {
        ArrayList<String> list=null;
        StringBuffer buffer = new StringBuffer();
        int index = c - 'a';
        TrieNode temp = root.getChildren()[index];
        buffer.append(temp.getCharacter());
//        if (temp.isWord())
//            list.add(buffer.toString());
        for (int i = 0; i < temp.getChildren().length; i++) {
            TrieNode child = temp.getChildren()[i];
            if (child != null) {
                list = helper(child, buffer);
            }
        }
        for (String item: list)
            System.out.println(item);
    }

    public ArrayList<String> helper(TrieNode node, StringBuffer buffer) {
        ArrayList<String> list = new ArrayList<>();
        buffer.append(node.getCharacter());
        if (node.isWord()) {
            list.add(buffer.toString());
            System.out.println(list);
        }
        for (int i = 0; i < node.getChildren().length; i++) {
            TrieNode temp = node.getChildren()[i];
            if (temp != null)
                helper(temp, buffer);
        }
        return list;
    }
}

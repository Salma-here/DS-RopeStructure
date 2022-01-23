import java.util.ArrayList;

public class Trie {
    private TrieNode root;

    public Trie(String[] words) {
        root = new TrieNode();
        root.setCharacter('_');
        root.setWord(false);
        for (String word : words)
            insert(word);
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
        ArrayList<String> list = new ArrayList<>();
        TrieNode temp = root.getChildren()[c - 'a'];
        StringBuffer buffer = new StringBuffer();
        list = scan(temp, buffer, 0, list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i+1 + ". " + list.get(i));
        }
    }

    public ArrayList<String> scan(TrieNode node, StringBuffer buffer, int position, ArrayList<String> list) {
        buffer = new StringBuffer(buffer.substring(0, position));
        buffer.append(node.getCharacter());
        position++;
        if (node.isWord()) {
            list.add(buffer.toString());
        }
        for(TrieNode child: node.getChildren()){
            if(child != null){
                list = scan(child, buffer, position, list);
            }
        }
        return list;
    }
}

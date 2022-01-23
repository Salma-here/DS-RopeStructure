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


    public void autocomplete(String prefix) {
        TrieNode node = root;
        for (int i = 0; i < prefix.length(); i++) {
            int index = prefix.charAt(i) - 'a';
            if (node.getChildren()[index] == null) {
                System.out.println("Not found");
                return;
            } else
                node = node.getChildren()[index];
        }
        char c = prefix.charAt(prefix.length() - 1);
        ArrayList<String> list = new ArrayList<>();
        TrieNode temp = node.getChildren()[c - 'a'];
        StringBuffer buffer = new StringBuffer(prefix);
        list = scan(node, buffer, prefix.length() - 1, list);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(i + 1 + ". " + list.get(i));
        }
    }

    public ArrayList<String> scan(TrieNode node, StringBuffer buffer, int position, ArrayList<String> list) {
        buffer = new StringBuffer(buffer.substring(0, position));
        buffer.append(node.getCharacter());
        position++;
        if (node.isWord()) {
            list.add(buffer.toString());
        }
        for (TrieNode child : node.getChildren()) {
            if (child != null) {
                list = scan(child, buffer, position, list);
            }
        }
        return list;
    }

    public void mostRepeated(Item[] list) {
        PriorityQueue queue = new PriorityQueue(list.length);
        for (Item item : list)
            queue.add(item);
        for (int i = 0; i < 3; i++) {
            System.out.println(String.valueOf(i + 1) + ". " + queue.delete().getData());
        }
    }
}

import java.util.ArrayList;

public class Trie {
    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
        root.setCharacter('_');
        root.setWord(false);
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


    public ArrayList<Item> autocomplete(String prefix) {
        TrieNode node = root;
        for (int i = 0; i < prefix.length(); i++) {
            int index = prefix.charAt(i) - 'a';
            if (node.getChildren()[index] == null) {
                System.out.println("Not found");
                return null;
            } else
                node = node.getChildren()[index];
        }
        char c = prefix.charAt(prefix.length() - 1);
        ArrayList<Item> list = new ArrayList<>();
        StringBuffer buffer = new StringBuffer(prefix);
        list = scan(node, buffer, prefix.length() - 1, list);
//        for (int i = 0; i < list.size(); i++) {
//            System.out.println(i + 1 + ". " + list.get(i));
//        }
//        mostRepeated(list);
        return list;
    }

    public ArrayList<Item> scan(TrieNode node, StringBuffer buffer, int position, ArrayList<Item> list) {
        buffer = new StringBuffer(buffer.substring(0, position));
        buffer.append(node.getCharacter());
        position++;
        if (node.isWord()) {
            Item item = new Item(buffer.toString(), node.getRep());
            list.add(item);
        }
        for (TrieNode child : node.getChildren()) {
            if (child != null) {
                list = scan(child, buffer, position, list);
            }
        }
        return list;
    }

    public Item[] mostRepeated(String prefix) {
        ArrayList<Item> list = autocomplete(prefix);
        PriorityQueue queue = new PriorityQueue(list.size());
        Item[] items = new Item[3];
        for (Item item : list)
            queue.add(item);
        for (int i = 0; i < 3; i++) {
            items[i] = queue.delete();
            System.out.println((i + 1) + ". " + items[i].getData());
        }
        return items;
    }

    public void addPriority(String str){
        TrieNode node=root;
        for (int i = 0; i < str.length(); i++) {
            node=node.getChildren()[str.charAt(i)-'a'];
        }
        node.addRep();
    }
}
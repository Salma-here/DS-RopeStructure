import java.util.ArrayList;

public class Trie {
    private final TrieNode root;

    public Trie() {
        root = new TrieNode();
        root.setCharacter('_');
        root.setWord(false);
    }


    public void insert(String word) {
        // If not present, inserts key into trie
        // If the key is prefix of trie node,just marks leaf node
        word=word.toLowerCase();
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
                return null;
            } else
                node = node.getChildren()[index];
        }
        ArrayList<Item> list = new ArrayList<>();
        StringBuffer buffer = new StringBuffer(prefix);
        list = scan(node, buffer, prefix.length() - 1, list);
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
                list = scan(child, buffer, position, list);//because for branches of trie
            }
        }
        return list;
    }

    public Item[] mostRepeated(String prefix) {//an Option for auto complete
        ArrayList<Item> list = autocomplete(prefix);
        if(list == null)
            return null;
        Item[] items = new Item[3];
        PriorityQueue queue = new PriorityQueue(list.size());
        for (Item item : list)
            queue.add(item);
        for (int i = 0; i < Math.min(3,list.size()); i++) {
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

    public Item[] allWords(String prefix){//an Option for auto complete
        ArrayList<Item> list = autocomplete(prefix);
        if(list == null)
            return null;
        Item[] items = new Item[list.size()];
        for (int i = 0; i < items.length; i++) {
            items[i]= list.get(i);
            System.out.println((i + 1) + ". " + items[i].getData());
        }
        return items;
    }

    public boolean anyWords(String prefix){
        if(autocomplete(prefix) == null)
            return false;
        return true;
    }
}
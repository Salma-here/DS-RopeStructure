import java.util.Stack;

public class Rope {
    private Node root;

    public Rope(String str) { // 'new'
        String[] words = str.split(" ");
        for (int i = 0; i < words.length - 1; i++) {
            words[i] += " ";
        }
        createRope(words, 0, words.length - 1, null, ' ');
    }

    public boolean isEmpty(){
        return root == null;
    }

    private void createRope(String[] words, int first, int last, Node par, char childType) {
        if (first < last) {
            Node p = new Node();
            if (par == null) {
                root = p;
            } else {
                if (childType == 'l') {
                    par.setLeft(p);
                } else {
                    par.setRight(p);
                }
            }
            int count = last - first + 1;
            int leftCount = count - count / 2;
            int len = 0;
            for (int i = first; i < first + leftCount; i++) {
                len += words[i].length();
            }
            p.setLen(len);
            createRope(words, first, (first + leftCount - 1), p, 'l');
            createRope(words, (first + leftCount), last, p, 'r');
        } else { //first == last
            Leaf p = new Leaf(words[first]); //or words[last] doesn't matter
            if (par != null) {
                if (childType == 'l') {
                    par.setLeft(p);
                } else {
                    par.setRight(p);
                }
            } else {
                root = new Node(p.getLen());
                root.setLeft(p);
            }
        }
    }

    public void show() { // 'status'
        System.out.println(getString());
    }

    public String getString(){ //inorder traversal
        Node p = root;
        StringBuffer buffer = new StringBuffer();
        if (p != null) {
            Stack<Node> s = new Stack<>();
            do {
                while (p != null) {
                    s.push(p);
                    p = p.getLeft();
                }
                if (!s.isEmpty()) {
                    p = s.pop();
                    //code for showing string
                    if (p instanceof Leaf) {
                        Leaf tmp = (Leaf) p;
                        buffer.append(tmp.getData());
                    }
                    //end code for showing string
                    p = p.getRight();
                }
            } while (!s.isEmpty() || p != null);
        }
        return buffer.toString();
    }

    public char index(int index){
        return indexAt(root,index);
    }

    public char indexAt(Node node, int index) { //TODO change name to index?
        if (node.getLen() <= index && node.getRight() != null)
            return indexAt(node.getRight(), index - node.getLen());
        if (node.getLeft() != null)
            return indexAt(node.getLeft(), index);
        if (node instanceof Leaf) {
            Leaf leaf = (Leaf) node;
            if (index < leaf.getLen())
                return leaf.getData().charAt(index);
        }
        System.out.println("a problem has occurred.");
        return ' ';//TODO
    }

    public void concat(Rope rope) {
        Node newRoot = new Node();
        newRoot.setLeft(root);
        newRoot.setRight(rope.root);
        int len = root.getLen();
        if (root.getRight() != null)
            len += root.getRight().getLen();
        newRoot.setLen(len);
        root = newRoot;
        rope.root = null;
    }

    public Rope split(int index) {
        Rope rope = new Rope("Hi");
        return rope;
    }//TODO

    public void insert(Rope rope, int index) {
        Rope lastRope = split(index);
        rope.concat(lastRope);
        this.concat(rope);
    }

    public void delete(int i, int j) {
        Rope lastRope = split(i);
        lastRope = lastRope.split(j - 1);
        this.concat(lastRope);
    }
}
import java.io.PrintStream;
import java.util.Stack;

public class Rope {
    private Node root;

    public Rope(String str) { // 'new'
        //String[] words = str.split("(?<=\\G.{5})"); //splits by length of 5.
        String[] words = str.split(" ");
        for (int i = 0; i < words.length - 1; i++)
            words[i] += " ";
        createRope(words, 0, words.length - 1, null, ' ');
    }

    public Rope(Node root) {
        this.root = root;
    }

    public boolean isEmpty() {
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

    public String getString() { //inorder traversal
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

    public char index(int index) {
        return findIndex(root, index);
    }

    public char findIndex(Node node, int index) {
        if (node.getLen() <= index && node.getRight() != null)
            return findIndex(node.getRight(), index - node.getLen());
        if (node.getLeft() != null)
            return findIndex(node.getLeft(), index);
        if (node instanceof Leaf) {
            Leaf leaf = (Leaf) node;
            if (index < leaf.getLen())
                return leaf.getData().charAt(index);
        }
        System.out.println("a problem has occurred.");
        return ' ';//what todo
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
        Rope otherRope;
        Node splitPoint = findSplitPoint(index);
        Node root = splitByNode(splitPoint);
        if (root instanceof Leaf) {
            Leaf l = (Leaf) root;
            Node temp = new Node(l.getLen());
            temp.setLeft(l);
            root = temp;
        }
        if (index < this.root.getLen()) { //returned root is root of other rope
            otherRope = new Rope(root);
        } else { //this.root should be replaced with returned root
            Node temp = this.root;
            this.root = root;
            otherRope = new Rope(temp);
        }
        return otherRope;
    }

    public Node findSplitPoint(int index) { //only works for split point at the end of a string
        Node node = root;
        index++;
        while (true) {
            int len = node.getLen();
            if (index > len) {
                node = node.getRight();
                index -= len;
            } else if (index < len) {
                node = node.getLeft();
            } else
                return node; //send node to "splitByNode" method
        }
    }

    public Node splitByNode(Node node) {
        Node rChild = node.getRight();
        Node rParent = node;
        Node lChild = null;
        Node lParent;
        Node root = rChild;
        while (true) {
            lParent = nearestLeft(this.root, rChild, null);
            rParent.setRight(lChild);
            if (lParent == null)
                break;
            root = lChild = lParent.getLeft();

            rParent = nearestRight(this.root, lChild, null);
            lParent.setLeft(rChild);
            if (rParent == null)
                break;
            root = rChild = rParent.getRight();
        }
        return root;
    }

    public Node nearestRight(Node root, Node node, Node rParent) {
        if (root == null)
            return null;
        if (root.getLeft() == node || root.getRight() == node)
            return rParent;
        Node right = nearestRight(root.getRight(), node, root);
        if (right != null)
            return right;
        return nearestRight(root.getLeft(), node, rParent);
    }

    public Node nearestLeft(Node root, Node node, Node lParent) {
        if (root == null)
            return null;
        if (root.getRight() == node || root.getLeft() == node)
            return lParent;
        Node left = nearestLeft(root.getLeft(), node, root);
        if (left != null)
            return left;
        return nearestLeft(root.getRight(), node, lParent);
    }

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

    public void traversePreOrder(StringBuilder sb, String padding, String pointer, Node node) {
        if (node != null) {
            sb.append(padding);
            sb.append(pointer);
            if (node instanceof Leaf) {
                Leaf l = (Leaf) node;
                sb.append(l.getData());
            } else
                sb.append(node.getLen());
            sb.append("\n");

            StringBuilder paddingBuilder = new StringBuilder(padding);
            paddingBuilder.append("│  ");

            String paddingForBoth = paddingBuilder.toString();
            String pointerForRight = "└──";
            String pointerForLeft = (node.getRight() != null) ? "├──" : "└──";

            traversePreOrder(sb, paddingForBoth, pointerForLeft, node.getLeft());
            traversePreOrder(sb, paddingForBoth, pointerForRight, node.getRight());
        }
    }

    public void print(PrintStream os) {
        StringBuilder sb = new StringBuilder();
        traversePreOrder(sb, "", "", this.root);
        os.print(sb.toString());
    }
}
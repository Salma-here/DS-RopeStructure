import java.util.ArrayList;
import java.util.Stack;

public class Rope {
    private Node root;

    public Rope(String str) { // 'new'
        String[] words = str.split("(?<=\\G.{5})"); //splits by length of 5.
        createRope(words, 0, words.length - 1, null, ' ');
        //first=first index of array and last=last index of array
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
            int count = last - first + 1;//number of all the leaves
            int leftCount = count - count / 2;//number of the leaves on the left side
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

    public static Rope getRope(String str, ArrayList<Rope> ropes) {
        for (Rope rope : ropes) {
            if (rope.getString().equals(str))
                return rope;
        }
        return null;
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
        return ' ';
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
        correctLengths();
    }


    public void insert(Rope rope, int index) {
        if (index > getString().length()) {
            System.out.println("index out of bounds.");
            return;
        }
        if (index == getString().length() - 1) {
            this.concat(rope);
            return;
        }
        Rope lastRope = split(index);
        rope.concat(lastRope);
        this.concat(rope);
        correctLengths();
    }

    public void delete(int i, int j) {
        if (i >= 0 && i < j && j < getString().length()) {
            Rope lastRope = split(i);
            lastRope = lastRope.split(j - i);
            this.concat(lastRope);
            correctLengths();
            return;
        }
        System.out.println("invalid argument.");
    }

    public Rope split(int index) {
        if (root == null)
            return null;
        Rope otherRope;//second part of the rope
        Node root = splitByIndex(index);
        if (root instanceof Leaf) {
            Leaf l = (Leaf) root;
            Node temp = new Node(l.getLen());
            temp.setLeft(l);
            root = temp;
        }
        if (index >= this.root.getLen()) { //returned root is root of other rope
            otherRope = new Rope(root);
        } else { //this.root should be replaced with returned root
            Node temp = this.root;
            this.root = root;
            otherRope = new Rope(temp);
        }
        correctLengths();
        otherRope.correctLengths();
        return otherRope;
    }

    public Node splitByIndex(int index) {
        Node node = root;
        Node parent = null;
        Node rChild = null, lChild = null;
        boolean lChildFirst = false;
        index++;
        while (true) {
            if (node == null)
                break;
            else if (node instanceof Leaf) {
                Leaf l = (Leaf) node;
                Leaf left, right;
                if (parent.getLeft() == node) {
                    left = new Leaf(l.getData().substring(0, index));
                    l.setData(l.getData().substring(index));
                    lChild = left;
                    lChildFirst = true;
                } else {
                    right = new Leaf(l.getData().substring(index));
                    l.setData(l.getData().substring(0, index));
                    rChild = right;
                }
                break;
            } else {
                int len = node.getLen();
                if (index < len) {
                    parent = node;
                    node = node.getLeft();
                } else if (index > len) {
                    index -= len;
                    parent = node;
                    node = node.getRight();
                } else {
                    parent = node;
                    rChild = node.getRight();
                    node.setRight(null);
                    break;
                }
            }
        }//end while

        Node rParent = null, lParent = null;
        if (lChildFirst)
            lParent = parent;
        else
            rParent = parent;
        Node root = (lChildFirst) ? lChild : rChild;

        while (true) {
            if (lChildFirst) {
                lChildFirst = false;
                rParent = nearestRight(this.root, lParent, null);
                if (rParent == null)
                    break;
                root = rChild = rParent.getRight();
                rParent.setRight(lChild);

                lParent = nearestLeft(this.root, rParent, null);
                if (lParent == null)
                    break;
                root = lChild = lParent.getLeft();
                lParent.setLeft(rChild);
            } else {
                lParent = nearestLeft(this.root, rParent, null);
                if (lParent == null)
                    break;
                root = lChild = lParent.getLeft();
                lParent.setLeft(rChild);

                rParent = nearestRight(this.root, lParent, null);
                if (rParent == null)
                    break;
                root = rChild = rParent.getRight();
                rParent.setRight(lChild);
            }
        }
        return root;
    }

    public Node nearestRight(Node root, Node node, Node rParent) {
        if (root == null)
            return null;
        if (root == node)
            return rParent;
        Node right = nearestRight(root.getRight(), node, root);
        if (right != null)
            return right;
        return nearestRight(root.getLeft(), node, rParent);
    }

    public Node nearestLeft(Node root, Node node, Node lParent) {
        if (root == null)
            return null;
        if (root == node)
            return lParent;
        Node left = nearestLeft(root.getLeft(), node, root);
        if (left != null)
            return left;
        return nearestLeft(root.getRight(), node, lParent);
    }


    public void correctLengths() {
        Node p = root;
        if (p != null) {
            Stack<Node> s = new Stack<>();
            do {
                while (p != null) {
                    s.push(p);
                    p = p.getLeft();
                }
                if (!s.isEmpty()) {
                    p = s.pop();
                    //code for correcting numbers
                    if (!(p instanceof Leaf)) {
                        int len = 0;
                        if (p.getLeft() != null) {
                            len += p.getLeft().getLen();
                            if (p.getLeft().getRight() != null)
                                len += p.getLeft().getRight().getLen();
                        }
                        p.setLen(len);
                    }
                    //end code for correcting numbers
                    p = p.getRight();
                }
            } while (!s.isEmpty() || p != null);
        }
    }
}
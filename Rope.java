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

    public Node findSplitPoint(int index){ //only works for split point at the end of a string
        Node node = root;
        index++;
        while(true){
            int len = node.getLen();
            if(index > len){
                node = node.getRight();
                index -= len;
            }
            else if(index < len){
                node = node.getLeft();
            }
            else
                return node; //send node to "splitByNode" method
        }
    }

    public Node splitByNode(Node node){
        //remove right and left link
        Node rChild = node.getRight();
        Node lChild = node.getLeft();
        node.setRight(null);
        //node.setLeft(null);?

        Node rParent, lParent;
        Node root = rChild;
        //node = lChild;?

        while(true){
            lParent = nearestLeft(this.root, rChild);
            if(lParent == null)
                break;
            root = lChild = lParent.getLeft();
            lParent.setLeft(rChild);

            rParent = nearestRight(this.root, lChild);
            if(rParent == null)
                break;
            root = rChild = rParent.getRight();
            rParent.setRight(lChild);
        }

        //todo
        //one root is found and saved in "root"
        //if given index is larger than original root's length,
        //then "root" is root of other rope.
        //if not, this.root should be replaced with "root"
        return root; //temporary
    }

    public Node nearestRight(Node root, Node node){
        //returns parent of the nearest ancestor to given node that is a RIGHT child
        //base case
        if(root == null)
            return null;
        Node rChild = root.getRight();
        if(rChild.getLeft() == node || rChild.getRight() == node){
            return root;
        }
        Node left = nearestRight(root.getLeft(), node);
        if(left == null)
            return nearestRight(root.getRight(), node);
        else
            return left;
    }

    public Node nearestLeft(Node root, Node node){
        //returns parent of the nearest ancestor to given node that is a LEFT child
        //base case
        if(root == null)
            return null;
        Node lChild = root.getLeft();
        if(lChild.getLeft() == node || lChild.getRight() == node){
            return root;
        }
        Node left = nearestLeft(root.getLeft(), node);
        if(left == null)
            return nearestLeft(root.getRight(), node);
        else
            return left;
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
}
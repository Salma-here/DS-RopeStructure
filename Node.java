public class Node {

    private Node left;
    private Node right;
    private int len;

    public Node(int len) {
        this();
        this.len = len;
    }

    public Node() {
        left = right = null;
    }

    public Node getLeft() {
        return left;
    }

    public void setLeft(Node left) {
        this.left = left;
    }

    public Node getRight() {
        return right;
    }

    public void setRight(Node right) {
        this.right = right;
    }

    public int getLen() {
        return len;
    }

    public void setLen(int len) {
        this.len = len;
    }
}

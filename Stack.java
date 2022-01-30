public class Stack {
    private int top;
    private final int size=100;
    private String[] items[];

    public Stack() {
        items = new String[size][];
        top = -1;
    }

    public boolean isEmpty() {
        if (top == -1)
            return true;
        return false;
    }

    public boolean isFull() {
        if (top == size - 1)
            return true;
        return false;
    }

    public void push(String[] x) {
        if (isFull())
            System.out.println("Stack is full!");
        else {
            items[++top] = x;
        }
    }

    public String[] pop() {
        String[] x=null;
        if (isEmpty())
            System.out.println("Stack is empty!");
        else
            x = items[top--];
        return x;
    }
}

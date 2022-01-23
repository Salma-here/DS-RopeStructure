public class PriorityQueue {
    private int front, rear;
    private Item[] items;

    public PriorityQueue(int size) {
        front = 0;
        rear = -1;
        items = new Item[size];
    }

    public void add(Item item) {
        int i = rear;
        if (rear == items.length - 1)
            System.out.println("full");
        for (; i >= front; i--) {
            if (item.getPriority() > items[i].getPriority())
                items[i + 1] = items[i];
            else
                break;
        }
        items[i + 1] = item;
        rear++;
    }

    public Item delete() {
        if (rear < front)
            System.out.println("Empty");
        else
            return items[front++];
        return null;
    }

    public void show() {
        for (int i = front; i <= rear; i++) {
            System.out.println(items[i].getData());
        }
    }
}

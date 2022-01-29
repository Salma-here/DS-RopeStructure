public class Item {
    private String data;
    private int priority;

    public Item(String data, int priority) {
        this.data = data;
        this.priority = priority;
    }

    public String getData() {
        return data;
    }

    public int getPriority() {
        return priority;
    }

}
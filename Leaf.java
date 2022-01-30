public class Leaf extends Node{
    private String data;

    public Leaf(String data) {
        super(data.length());
        this.data = data;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
        this.setLen(data.length());
    }
}

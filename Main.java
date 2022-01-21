public class Main {
    public static void main(String[] args) {

//        Rope r1 = new Rope("I am rope date structure.");
//        r1.show();
//        Rope r2 = new Rope("new string is here.");
//        r2.show();
//        Rope r3 = new Rope("another rope");
//        r3.show();
//
//        System.out.println(r1.indexAt(r1.getRoot(), 10));
//        System.out.println(r2.indexAt(r2.getRoot(), 30));
//
//        r1.concat(r2);
//        r1.show();
//        r2.show();
//        r3.show();

        String[] words = {
                "hello", "hey", "hell", "nice"
        };
        Trie t = new Trie(words);
        t.autocomplete('h');
    }
}

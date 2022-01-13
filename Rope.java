import java.util.Stack;

public class Rope {
    private Node root;
    private static int count;

    public Rope(String str){ // 'new'
        String[] words = str.split(" ");
        for(int i = 0; i < words.length - 1; i++){
            words[i] += " ";
        }
        createRope(words, 0, words.length - 1, null, ' ');
        count++;
    }

    private void createRope(String[] words, int first, int last, Node par, char childType){
        if(first < last){
            Node p = new Node();
            if(par == null){
                root = p;
            }
            else{
                if(childType == 'l'){
                    par.setLeft(p);
                }
                else{
                    par.setRight(p);
                }
            }
            int count = last - first + 1;
            int leftCount = count - count/2;
            int len = 0;
            for(int i = first; i < first + leftCount; i++){
                len += words[i].length();
            }
            p.setLen(len);
            createRope(words, first, (first + leftCount - 1), p, 'l');
            createRope(words, (first + leftCount), last, p, 'r');
        }
        else{ //first == last
            Leaf p = new Leaf(words[first]); //or words[last] doesn't matter
            if(childType == 'l'){
                par.setLeft(p);
            }
            else{
                par.setRight(p);
            }
        }
    }

    public void show(){ // 'status' //inorder traversal
        Node p = root;
        Stack<Node> s = new Stack<Node>();
        System.out.print(count + ". ");
        do{
            while(p != null){
                s.push(p);
                p = p.getLeft();
            }
            if(!s.isEmpty()){
                p = s.pop();
                //code for showing string
                if(p instanceof Leaf){
                    Leaf tmp = (Leaf) p;
                    System.out.print(tmp.getData());
                }
                //end code for showing string
                p = p.getRight();
            }
        } while(!s.isEmpty() || p != null);
        System.out.println();
    }

}

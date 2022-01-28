import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("example.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        Trie words = new Trie();
        while (true) {//Reading from file
            String line = reader.readLine();
            if (line == null)
                break;
            words.insert(line.trim());
        }
        Scanner scanner = new Scanner(System.in);
        ArrayList<Rope> ropes = new ArrayList<>();
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("status")) {
                if (ropes.size() == 0)
                    System.out.println("No Strings!");
                ropes.trimToSize();
                int counter = 1;
                for (int i = 0; i < ropes.size(); i++) {
                    if(ropes.get(i) != null && !ropes.get(i).isEmpty()) {
                        System.out.print(counter + ". ");
                        ropes.get(i).show();
                        counter++;
                    }
                }
            } else if (input.startsWith("new")) {
                String temp = input.substring(input.indexOf('\"') + 1, input.length() - 1);
                ropes.add(new Rope(temp));
            } else if (input.startsWith("index")) {
                if(input.split(" ").length == 3){
                    int stringNumber = Integer.parseInt(input.split(" ")[1]);
                    int index = Integer.parseInt(input.split(" ")[2]);
                    if(stringNumber > ropes.size()){
                        System.out.println("invalid argument.");
                        continue;
                    }
                    Rope rope = ropes.get(stringNumber - 1);
                    System.out.println(rope.index(index));
                }
            } else if (input.startsWith("concat")) {//TODO
                if(input.split(" ").length == 3){
                    int firstStr = Integer.parseInt(input.split(" ")[1]) - 1;
                    int lastStr = Integer.parseInt(input.split(" ")[2]) - 1;
                    Rope rope = ropes.get(firstStr);
                    rope.concat(ropes.get(lastStr));
                    ropes.add(firstStr, rope);
                    ropes.remove(lastStr);
                }
            } else if (input.startsWith("insert")) {
                if(input.split(" ").length == 4){
                    int firstStr = Integer.parseInt(input.split(" ")[1]) - 1;
                    int index = Integer.parseInt(input.split(" ")[2]);
                    int lastStr = Integer.parseInt(input.split(" ")[3]) - 1;
                    Rope rope = ropes.get(firstStr);
                    rope.insert(ropes.get(lastStr), index);
                    ropes.add(firstStr, rope);
                }
            } else if (input.startsWith("split")) {
                if(input.split(" ").length == 3){
                    int stringNum = Integer.parseInt(input.split(" ")[1]) - 1;
                    int index = Integer.parseInt(input.split(" ")[2]);
                    Rope rope = ropes.get(stringNum);
                    ropes.add(rope.split(index));
                    ropes.add(stringNum, rope);
                }
            } else if (input.startsWith("delete")) {
                if(input.split(" ").length == 4){
                    int stringNum = Integer.parseInt(input.split(" ")[1]) - 1;
                    int i = Integer.parseInt(input.split(" ")[2]);
                    int j = Integer.parseInt(input.split(" ")[3]);
                    Rope rope = ropes.get(stringNum);
                    rope.delete(i, j);
                    ropes.add(stringNum, rope);
                }
            } else if (input.startsWith("autocomplete")) {
                if(input.split(" ").length == 2){
                    String prefix = input.split(" ")[1];
                    Item[] items = words.mostRepeated(prefix);
                    System.out.println("Please choose one of them: ");
                    int n = 0;
                    try {
                        n = scanner.nextInt();
                        scanner.skip("[\r\n]+");
                    } catch (InputMismatchException e) {
                        System.out.println(" You should enter number! ");
                    }
                    if(n >= 1 && n <= 3) {
                        String str = items[n - 1].getData();
                        int i = 0;
                        for(; i < ropes.size(); i++){
                            if(ropes.get(i).getString().equals(str)){
                                System.out.println("string already added.");
                                break;
                            }
                        }
                        if(i == ropes.size()) {
                            Rope rope = new Rope(str);
                            if (!ropes.contains(rope))
                                ropes.add(rope);
                            words.addPriority(str);
                        }
                    }
                }
            } else if (input.equalsIgnoreCase("q")) {
                break;
            } else
                System.out.println("Invalid argument.");
        }
    }
}
import java.io.*;
import java.util.*;

public class Main {
    public static void main(String[] args) throws IOException {
        File file = new File("example.txt");
        BufferedReader reader = new BufferedReader(new FileReader(file));
        Trie words = new Trie();
        Stack stack = new Stack();
        while (true) {//Reading from file
            String line = reader.readLine();
            if (line == null)
                break;
            words.insert(line.trim());
        }
        Scanner scanner = new Scanner(System.in);
        ArrayList<Rope> ropes = new ArrayList<>();
        System.out.println("--Rope data structure--");
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("status")) {
                if (ropes.size() == 0)
                    System.out.println("No Strings!");
                ropes.trimToSize();
                int counter = 1;
                for (int i = 0; i < ropes.size(); i++) {
                    if (ropes.get(i) != null && !ropes.get(i).isEmpty()) {
                        System.out.print(counter + ". ");
                        ropes.get(i).show();
                        counter++;
                    }
                }
            } else if (input.startsWith("new")) {
                String temp = input.substring(input.indexOf('\"') + 1, input.length() - 1);
                ropes.add(new Rope(temp));//Creating new Rope and adding to array list
                String[] command = {"new", temp};
                stack.push(command);
            } else if (input.startsWith("index")) {
                if (input.split(" ").length == 3) {
                    int stringNumber = Integer.parseInt(input.split(" ")[1]);
                    int index = Integer.parseInt(input.split(" ")[2]);
                    if (stringNumber > ropes.size()) {//Wrong order
                        System.out.println("invalid argument.");
                        continue;
                    }
                    Rope rope = ropes.get(stringNumber - 1);//Found it from array list
                    System.out.println(rope.index(index));
                }
            } else if (input.startsWith("concat")) {
                if (input.split(" ").length == 3) {
                    int firstStr = Integer.parseInt(input.split(" ")[1]) - 1;
                    int lastStr = Integer.parseInt(input.split(" ")[2]) - 1;
                    String[] command = {"concat", ropes.get(firstStr).getString(), ropes.get(lastStr).getString()};
                    stack.push(command);
                    Rope rope = ropes.get(firstStr);//Found first input from array list
                    rope.concat(ropes.get(lastStr));
                    ropes.remove(lastStr);//remove second input from array list
                    sortList(ropes);
                }
            } else if (input.startsWith("insert")) {
                if (input.split(" ").length == 4) {
                    int firstStr = Integer.parseInt(input.split(" ")[1]) - 1;
                    int index = Integer.parseInt(input.split(" ")[2]);
                    int lastStr = Integer.parseInt(input.split(" ")[3]) - 1;
                    String[] command = {"insert", ropes.get(firstStr).getString(), String.valueOf(index), ropes.get(lastStr).getString(), " "};
                    stack.push(command);
                    Rope rope = ropes.get(firstStr);
                    rope.insert(ropes.get(lastStr), index);
                    command[4] = rope.getString();
                    sortList(ropes);
                }
            } else if (input.startsWith("split")) {
                if (input.split(" ").length == 3) {
                    int stringNum = Integer.parseInt(input.split(" ")[1]) - 1;
                    int index = Integer.parseInt(input.split(" ")[2]);
                    String[] command = {"split", ropes.get(stringNum).getString(), String.valueOf(index)};
                    stack.push(command);
                    Rope rope = ropes.get(stringNum);
                    Rope otherRope = rope.split(index);
                    ropes.add(otherRope);
                    sortList(ropes);
                }
            } else if (input.startsWith("delete")) {
                if (input.split(" ").length == 4) {
                    int stringNum = Integer.parseInt(input.split(" ")[1]) - 1;
                    int i = Integer.parseInt(input.split(" ")[2]);
                    int j = Integer.parseInt(input.split(" ")[3]);
                    String[] command = {"delete", ropes.get(stringNum).getString(), String.valueOf(i), String.valueOf(j), " "};
                    stack.push(command);
                    Rope rope = ropes.get(stringNum);
                    rope.delete(i, j);
                    command[4] = rope.getString();
                    sortList(ropes);
                }
            } else if (input.startsWith("autocomplete")) {
                if (input.split(" ").length == 2) {
                    String prefix = input.split(" ")[1];
                    if (!words.anyWords(prefix)) {
                        System.out.println("Not found");
                        continue;
                    }
                    System.out.println("If you want to have all possible words enter 1");
                    System.out.println("If you want to have 3 most repeated words enter 2");
                    int x = 0;
                    try {
                        x = scanner.nextInt();
                    } catch (InputMismatchException e) {
                        System.out.println(" You should enter number! ");
                    }
                    Item[] items;
                    if (x == 2) {
                        items = words.mostRepeated(prefix);
                    } else if (x == 1) {
                        items = words.allWords(prefix);
                    } else
                        continue;
                    System.out.println("Please choose one of them: ");
                    int n = 0;
                    try {
                        n = scanner.nextInt();
                        scanner.skip("[\r\n]+");
                    } catch (InputMismatchException e) {
                        System.out.println(" You should enter number! ");
                    }
                    if (n >= 1 && n <= items.length) {
                        String str = items[n - 1].getData();
                        if (Rope.getRope(str, ropes) != null) {
                            System.out.println("string already added.");
                        } else {
                            ropes.add(new Rope(str));
                            String[] command = {"new", str};
                            stack.push(command);
                        }
                        words.addPriority(str);
                    }
                }
            } else if (input.equalsIgnoreCase("undo")) {
                if (stack.isEmpty()) {
                    System.out.println("no last commands.");
                    continue;
                }
                String[] command = stack.pop();
                if (command[0].equals("new")) {
                    Rope rope = Rope.getRope(command[1], ropes);
                    ropes.remove(rope);
                } else if (command[0].equals("concat")) {
                    String firstStr = command[1];
                    String secondStr = command[2];
                    Rope rope = Rope.getRope(firstStr + secondStr, ropes);
                    Rope other = rope.split(firstStr.length() - 1);
                    ropes.add(other);
                } else if (command[0].equals("split")) {
                    String str = command[1];
                    int index = Integer.parseInt(command[2]);
                    index++;
                    Rope firstRope = Rope.getRope(str.substring(0, index), ropes);
                    Rope secondRope = Rope.getRope(str.substring(index), ropes);
                    Rope finalRope = new Rope(str);
                    ropes.remove(firstRope);
                    ropes.remove(secondRope);
                    ropes.add(finalRope);
                } else if (command[0].equals("insert")) {
                    String firstStr = command[1];
                    Rope firstRope = new Rope(firstStr);
                    String secondStr = command[3];
                    Rope secondRope = new Rope(secondStr);
                    Rope resultRope = Rope.getRope(command[4], ropes);
                    ropes.remove(resultRope);
                    ropes.add(firstRope);
                    ropes.add(secondRope);
                } else if (command[0].equals("delete")) {
                    String str = command[1];
                    Rope initialRope = new Rope(str);
                    Rope resultRope = Rope.getRope(command[4], ropes);
                    ropes.remove(resultRope);
                    ropes.add(initialRope);
                }
                sortList(ropes);
            } else if (input.equalsIgnoreCase("clr"))
                ropes.clear();
            else if (input.startsWith("q"))
                break;
            else
                System.out.println("Invalid argument.");
        }
    }

    public static void sortList(ArrayList<Rope> ropes) {
        for (int i = 0; i < ropes.size(); i++)
            if (ropes.get(i).isEmpty())
                ropes.remove(i);
    }
}
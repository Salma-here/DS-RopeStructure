import java.util.LinkedList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        LinkedList<Rope> ropes = new LinkedList<>();
        int count = 0;
        while (true) {
            String input = scanner.nextLine();
            if (input.equalsIgnoreCase("status")) {
                if (ropes.size() == 0)
                    System.out.println("No Strings!");
                for (int i = 0; i < ropes.size(); i++) {
                    if (ropes.get(i) == null) {
                        i--;
                        continue;
                    }
                    System.out.print(i + 1 + ". ");
                    ropes.get(i).show();
                }
            } else if (input.startsWith("new")) {
                String temp = input.substring(input.indexOf('\"') + 1, input.length() - 1);
                ropes.add(new Rope(temp));
                count++;
            } else if (input.startsWith("index")) {
                int stringNumber = Integer.parseInt(input.split(" ")[1]);
                int index = Integer.parseInt(input.split(" ")[2]);
                Rope rope = ropes.get(stringNumber - 1);
                System.out.println(rope.index(index));
            } else if (input.startsWith("concat")) {//TODO
                int firstStr = Integer.parseInt(input.split(" ")[1]) - 1;
                int lastStr = Integer.parseInt(input.split(" ")[2]) - 1;
                Rope rope = ropes.get(firstStr);
                rope.concat(ropes.get(lastStr));
                ropes.add(firstStr, rope);
                ropes.remove(lastStr);
                count--;
            } else if (input.startsWith("insert")) {
                int firstStr = Integer.parseInt(input.split(" ")[1]) - 1;
                int index = Integer.parseInt(input.split(" ")[2]);
                int lastStr = Integer.parseInt(input.split(" ")[3]) - 1;
                Rope rope = ropes.get(firstStr);
                rope.insert(ropes.get(lastStr), index);
                ropes.add(firstStr, rope);
            } else if (input.startsWith("split")) {
                int stringNum = Integer.parseInt(input.split(" ")[1]) - 1;
                int index = Integer.parseInt(input.split(" ")[2]);
                Rope rope = ropes.get(stringNum);
                ropes.add(rope.split(index));
                ropes.add(stringNum, rope);
                count++;
            } else if (input.startsWith("delete")) {
                int stringNum = Integer.parseInt(input.split(" ")[1]) - 1;
                int i = Integer.parseInt(input.split(" ")[2]);
                int j = Integer.parseInt(input.split(" ")[3]);
                Rope rope = ropes.get(stringNum);
                rope.delete(i, j);
                ropes.add(stringNum, rope);
            } else if (input.equalsIgnoreCase("Q")) {
                break;
            } else
                System.out.println("Invalid argument");
        }
    }
}

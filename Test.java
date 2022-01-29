public class Test {
    public static void main(String[] args) {
        String str = "Hello my name is Salma and I am here.";
        System.out.println("************* NOTICE *************");
        for(int i = 0; i < str.length(); i++){
            if(str.charAt(i) == ' ')
                System.out.print(i + " ");
        }
        System.out.println();
        System.out.println("************* NOTICE *************");
        Rope r = new Rope(str);
        System.out.println("rope before split: ");
        r.print(System.out);
        Rope secondPart = r.split(16);
        System.out.println("--------------------------------------");
        System.out.println("after split: ");
        System.out.println("first part: ");
        r.print(System.out);
        System.out.println("second part: ");
        secondPart.print(System.out);
    }
}

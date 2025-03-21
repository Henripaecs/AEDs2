package Lab;

import java.util.Scanner;

public class Aquecimento {

    public static int upper(String arr){
        char[] l = arr.toCharArray();
        int cont = 0;
        for (int i = 0; i < arr.length(); i++) {
            if (Character.isUpperCase(l[i])) {
                cont++;
            }
        }
        return cont;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        do {
            String str = scanner.nextLine();
            if (str.equals("FIM")) {
                break;
            }
            System.out.println(upper(str));
        } while (true);
        scanner.close();
    }
}

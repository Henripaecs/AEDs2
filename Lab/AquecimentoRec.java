package Lab;

import java.util.Scanner;

public class AquecimentoRec {
    public static int upper(String arr, int aux){
        if (aux == arr.length()){
            return 0;
        }
        int cont = 0;
       
        if (Character.isUpperCase(arr.charAt(aux))){
            cont = 1;
        }
        return cont + upper(arr, aux + 1);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        do {
            String str = scanner.nextLine();
            if (str.equals("FIM")) {
                break;
            }
            System.out.println(upper(str, 0));
        } while (true);
        scanner.close();
    }
}

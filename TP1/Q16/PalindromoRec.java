package Q16;

import java.util.Scanner;

public class PalindromoRec {
    public static boolean isPalindromo(String str, int left, int right) {
        if (left >= right) {
            return true;
        }
        if (str.charAt(left) != str.charAt(right)) {
            return false;
        }
        return isPalindromo(str, left + 1, right - 1);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while(true){
            String entrada = scanner.nextLine();

            if (entrada.equals("FIM")) {
                break;
            }
            
            if (isPalindromo(entrada, 0, entrada.length() - 1)) {
                System.out.println("SIM");
            } else {
                System.out.println("NAO");
            }
        }
        scanner.close();
    }
}

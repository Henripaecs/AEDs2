package Q01;
import java.util.Scanner;

public class Palindromo {
    public static boolean isPalindromo(String str) {
        int left = 0, right = str.length() - 1;

        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while(true){
            String entrada = scanner.nextLine();

            if (entrada.equals("FIM")) {
                break;
            }
            
            if (isPalindromo(entrada)) {
                System.out.println("SIM");
            } else {
                System.out.println("NAO");
            }
        }
        scanner.close();
    }
}

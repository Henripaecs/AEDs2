import java.util.Scanner;

public class palindromo {
    public static boolean ehpalindromo(String palavra) {
        int i = 0;
        int j = palavra.length() - 1;

        while (i < j) {
            if (palavra.charAt(i) != palavra.charAt(j)) {
                return false; 
            }
            i++;
            j--;
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String palavra;

        do {
            palavra = sc.nextLine();  
            if (!palavra.equals("FIM")) {
                if (ehpalindromo(palavra)) {
                    System.out.println("SIM");
                } else {
                    System.out.println("NAO");
                }
            }
        } while (!palavra.equals("FIM"));
        sc.close();
    }
}

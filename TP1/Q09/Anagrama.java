import java.util.Arrays;
import java.util.Scanner;

public class Anagrama {
    public static boolean Verificar(String str1, String str2) {
        if (str1.length() != str2.length()) {
            return false;
        }
        str1 = str1.replace("[^a-zA-Z]", "0");
        str2 = str2.replace("[^a-zA-Z]", "0");

        char[] array1 = str1.toUpperCase().toCharArray();
        char[] array2 = str2.toUpperCase().toCharArray();
        
        Arrays.sort(array1);
        Arrays.sort(array2);
        
        return Arrays.equals(array1, array2);
    }    

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNextLine()){  
            String entrada1 = scanner.nextLine();
            if (entrada1.equals("FIM")) {
                break;
            }
            String[] partes = entrada1.split("-");
            if (Verificar(partes[0], partes[1])) {
                 System.out.println("SIM");
            } else {
                System.out.println("NAO");
            }
            
        }

        scanner.close();
    }
}

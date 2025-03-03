import java.util.Arrays;
import java.util.Scanner;

public class Anagrama {
    public static boolean Verificar(String str1, String str2){
        if (str1.length() != str2.length()){
            return false;
        }
        char[] array1 = str1.toUpperCase().toCharArray();
        char[] array2 = str2.toUpperCase().toCharArray();
        
        Arrays.sort(array1);
        Arrays.sort(array2);
        
        return Arrays.equals(array1, array2);
    }    

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true){
            String entrada1 = scanner.nextLine();
            if (entrada1.equals("FIM")){
                break;
            }
            
            String entrada2 = scanner.nextLine();

            if (Verificar(entrada1, entrada2)){
                System.out.println("SIM");
            } else {
                System.out.println("NÃO");
            }
        }

        scanner.close();
    }
}

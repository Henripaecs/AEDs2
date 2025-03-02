package Q09;

import java.util.Arrays;
import java.util.Scanner;

public class Anagrama {
    public static boolean Verificar(String str1, String str2){
        if (str1.length() != str2.length()){
            return false;
        }
        char[] array1 = str1.toCharArray();
        char[] array2 = str2.toCharArray();

        for (int i = 0; i < str1.length(); i++){
            array1[i] = Character.toUpperCase(array1[i]);
            array2[i] = Character.toUpperCase(array2[i]);
        }
        
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
            }else{
                System.out.println("NÃO");
            }
        }
        scanner.close();;
    }
}

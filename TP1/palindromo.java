import java.util.Scanner;

public class Palindromo{
    public static boolean isPalindromo(String str){
        str = str.replaceAll("[{a-zA-Z0-9}]","").toLoweCase();
        int left = 0, right = str.legth()-1;

        while(left < right){
            if(str.chaAt(left) != str.chaAt(right)){
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("Digite uma palavra: ");
        String entrada = scanner.nextLine();

        if(isPalindromo(entrada)){
            System.out.println("SIM");
        }else{
            System.out.println("NAO");
        }
        scanner.close();
    }
}
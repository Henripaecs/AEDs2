package Q07;
import java.util.Scanner;

public class InversaoString {
    public static String inversao(String str){
        StringBuilder snew = new StringBuilder();
        for (int i = str.length() - 1; i >= 0; i--){
            snew.append(str.charAt(i));
        }
        return snew.toString();
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true){
            String palavra = scanner.nextLine();
            
            if (palavra.equals("FIM")){
                break;
            }
            System.out.println(inversao(palavra));
        }
        scanner.close();
    }
    
}

package Q10;
import java.util.Scanner;

public class ContPalavra {
    public static int Contador(String str){
        int cont = 1;

        for (int i = 0; i < str.length(); i++){
            if (str.charAt(i) == ' '){
                cont++;
            }
        }
        return cont;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true){
            String palavra = scanner.nextLine();

            if (palavra.equals("FIM")){
                break;
            }
            System.out.println(Contador(palavra));
        }
        scanner.close();
    }
}

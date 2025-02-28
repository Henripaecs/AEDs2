package Q08;
import java.util.Scanner;

public class SomaDigitos {
    public static int somar(String n){
        if(n == 0 || n == 1){
            return 1;
        }
        return n + somar(n-1);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while(true){
            Scanner entrada = scanner.nextLine();
            if(entrada.equals("FIM")){
                break;
            }
            System.out.println(somar(entrada));
        }
    }
}

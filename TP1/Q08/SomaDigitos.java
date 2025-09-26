import java.util.Scanner;

public class SomaDigitos {
    public static int somar(String n, int aux){
        if(aux == n.length()){
            return 0;
        }
        return Character.getNumericValue(n.charAt(aux)) + somar(n, aux + 1);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while(true){            
            String entrada = scanner.nextLine();

            if(entrada.equals("FIM")){
                break;
            }
            int soma = somar(entrada, 0);
            System.out.println(soma);
        }
        scanner.close();
    }
}

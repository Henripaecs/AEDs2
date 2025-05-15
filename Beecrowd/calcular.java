import java.util.Locale;
import java.util.Scanner;

public class calcular {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        sc.useLocale(Locale.US); 
        
        while(sc.hasNextLine()){
            int cod1 = sc.nextInt();
            int qtd1 = sc.nextInt();
            double preco1 = sc.nextDouble();

            int cod2 = sc.nextInt();
            int qtd2 = sc.nextInt();
            double preco2 = sc.nextDouble();

            double total = qtd1 * preco1 + qtd2 * preco2;

            System.out.printf("VALOR A PAGAR: R$ %.2f%n", total);

            sc.close();
        }
        
    }
}

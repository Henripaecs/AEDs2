package Lab;
import java.util.Scanner;

public class espelho {
    public static void espoelhar(int x, int n){
        for (int i = x; i <= n; i++){
            System.out.print(i);
        }
        for (int j = n; j >= x; j--){
            int num = j;
            int invertido = 0;

            while (num != 0){
                int digito = num % 10;
                invertido = invertido * 10 + digito;
                num /= 10;
            }
            if (n >= 10 && invertido < 10) {
                System.out.print("0" + invertido + "");
            } else if(n >=100 && invertido < 100){
                System.out.print("00" + ""+invertido);
            }
            else {
                System.out.print(invertido + "");
            }
        }
        System.out.println();
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (scanner.hasNext()){
            int x = scanner.nextInt();
            int n = scanner.nextInt();
            
            espoelhar(x, n);
        }
        scanner.close();
    }
    
}
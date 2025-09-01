import java.util.*;

public class espelho {
    public static void espelhar(int num1, int num2){
        for (int i = num1; i <= num2; i++){
            System.out.print(i + "");
        }

        for (int j = num2; j >= num1; j--){
            int inverte = 0, aux = j;
            while(aux > 0){
                inverte = inverte * 10 + (aux % 10);
                aux /= 10;
            }
            System.out.print(inverte + "");
        }
        System.out.println();
    }
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        while (sc.hasNextInt()){
            int num1 = sc.nextInt();
            int num2 = sc.nextInt();

            espelhar(num1, num2);
        }
        sc.close();
    }
}

import java.util.Scanner;

public class corrida {
    
    public static int ordenar(int n, int[] arr){
        int cont = 0;
        for (int i = 0; i < n-1; i++){
            int menor = i;
            for (int j = i + 1; j < n; j++){
                if (arr[menor] > arr[j]){
                    menor = j;
                }
            }  
            if (menor != i){          
                int temp = arr[menor];
                arr[menor] = arr[i];
                arr[i] = temp;
                cont++;
            }
        }
        return cont;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while(sc.hasNext()){
            int n1;

            n1 = sc.nextInt();
            int[] n2 = new int[n1];
            int[] arr = new int[n1];

            for (int i = 0; i < n1; i++){
                n2[i] = sc.nextInt();
            }

            for (int j = 0; j < n1; j++){
                arr[j] = sc.nextInt();
            }

            int[] copia1 = n2.clone();
            int[] copia2 = arr.clone();

            int cont = ordenar(n1, copia1);
            int cont2 = ordenar(n1, copia2);
            int result = cont - cont2;
            if (result < 0){
                result *= -1;
            }

            System.out.println(result);
        }
        sc.close();
    }
}

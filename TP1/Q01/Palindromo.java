package Q01;
import java.util.Scanner;

public class Palindromo {
    public static boolean isPalindromo(String str) {
        int left = 0, right = str.length() - 1;

        while (left < right) {
            if (str.charAt(left) != str.charAt(right)) {
                return false;
            }
            left++;
            right--;
        }
        return true;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while(true){
            String entrada = scanner.nextLine();

            if (entrada.equals("FIM")) {
                break;
            }
            
            if (isPalindromo(entrada)) {
                System.out.println("SIM");
            } else {
                System.out.println("NAO");
            }
        }
        scanner.close();
    }
}
/*
public class BubbleSort {
    public static void bubbleSort(int[] num){
        int n = num.length;
        int cont = 0;
        for (int i = 0; i < n - 1; i++){
            for (int j = 0; j < n - i - 1; j++){
                if (num[j] > num[j + 1]){
                    int temp = num[j];
                    num[j] = num[j + 1];
                    num[j + 1] = temp;
                }
                cont++;
            }
        }
        System.out.println(" "+ cont);
    }
    public static void main(String[] args) {
        int[] numeros = {54,21,45,12,1,65,43,5};
        bubbleSort(numeros);
        System.out.println(" "+ java.util.Arrays.toString(numeros));
    }
    
}

------------
public class InsertionSort {
    public static int[] ordenar(int[] arr){
        int n = arr.length;
        for (int i = 1;i < n; i++){
            int menor = arr[i];
            int j = i;

            while (j > 0 && menor < arr[j - 1]){
                arr[j] = arr[j-1];
                j--;
            }
            arr[j] = menor;
        }
        return arr;
    }

    public static void main(String[] args) {
        int[] arr = {16, 11, 06, 03, 30, 05};

        for (int i = 0; i < arr.length; i++){
            System.out.print(" "+ arr[i]);
        }

        System.out.println();
       
        arr = ordenar(arr);

        for (int i = 0; i < arr.length; i++){
            System.out.print(" "+ arr[i]);
        }
        System.out.println();
    }
}
-------------
public class SelectionSort {
    public static void selectionSort(int[] arr){
        int n = arr.length;

        for (int i = 0; i < n - 1; i++){
            int menor = i;
            for (int j = i+1; j < n; j++){
                if(arr[j] < arr[menor]){
                    menor = j;
                }
            }
            int temp = arr[menor];
            arr[menor] = arr[i];
            arr[i] = temp;
        }
    }
    public static void printArray(int[] arr){
        for (int i = 0; i < arr.length; i++){
            System.out.print(" "+arr[i]);
        }
        System.out.println();
    }
    public static void main(String[] args) {
        int[] arr = {43, 312, 2, 32, 65, 87};

        printArray(arr);

        selectionSort(arr);

        printArray(arr);
    }
}

*/

public class OtimizedBubbleSort {
    public static void bubbleSort(int[] arr){
        int n  = arr.length;
        boolean troca;
        int cont = 0;

        for (int i = 0; i < n - 1; i++){
            troca = false;
            for (int j = 0; j < n - i - 1; j++){
                if(arr[j] > arr[j + 1]){
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp;
                    troca = true;
                }
                cont++;
            }
            if (!troca){
                break;
            }
        }
        System.out.println(""+ cont);
    }
    public static void main(String[] args) {
        int[] numeros = {54,21,45,12,1,65,43,5};

        bubbleSort(numeros);

        System.out.println(" " + java.util.Arrays.toString(numeros));
    }
    
}
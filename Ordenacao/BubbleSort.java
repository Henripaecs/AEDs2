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

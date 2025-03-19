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

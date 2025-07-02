public class selececao {
    public static void selectionSort(int[] arr){
        int n = arr.length;
        for (int i = 0; i < n-1; i++){
            int menor = i;
            for(int j = i+1; j < n; j++){
                if(arr[menor] > arr[j]){
                    menor = j;
                }
            }
            int temp = arr[menor];
            arr[menor] = arr[i];
            arr[i] = temp;
        }
    }
}

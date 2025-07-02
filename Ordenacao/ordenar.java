public class ordenar {
    public static void bolha(int[] arr){
        int n = arr.length;    
        for (int i = 0; i < n - 1; i++){
            for (int j = 0; j < n - i - 1; j++){
                if(arr[j] > arr[j+1]){
                    int temp = arr[j];
                    arr[j] = arr[j+1];
                    arr[j+1] = temp;
                }
            }
        }    
    }

    public static void selectionSort(int[] arr){
        int n = arr.length;
        for (int i = 0; i < n - 1; i++){
            int menor = i;
            for (int j = i + i; j < n; j++){
                if (arr[menor] > arr[j]){
                    menor = j;
                }
            }
            int temp = arr[menor];
            arr[menor] = arr[i];
            arr[i] = temp;
        }
    }

    public static int[] insercao(int[] arr){
        int n = arr.length;
        for (int i = 1; i <  n; i++){
            int menor = arr[i];
            int j = i;
            
            while(j > 0 && menor > arr[j - 1]){
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = menor;
        }
        return arr;
    }

    public static void quick(int esq, int dir, int[] arr){
        int i = esq;
        int j = dir;
        int pivo = arr[(dir+esq)/2]; 

        while(i <= j){
            while(arr[i] < pivo) i++;
            while(arr[j] > pivo) j--;

            if(i <= j ){
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }
        if (esq < j) quick(esq, j, arr);
        if (dir > i) quick(i, dir, arr);
    }
}
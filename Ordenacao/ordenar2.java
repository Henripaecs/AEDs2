public class ordenar2 {
    public static void bolha(int[] arr){
        int n = arr.length;
        for (int i = 0; i < n - 1; i++){
            for (int j = 0; j < n -i - 1; j++){
                if (arr[j] > arr[j + 1]){
                    int temp = arr[j];
                    arr[j] = arr[j + 1];
                    arr[j + 1] = temp; 
                }
            }
        }
    }

    public static void select(int[] arr){
        int n = arr.length;
        for (int i = 0; i < n; i++){
            int menor = i;
            for (int j = i+1; j < n; j++){
                if (arr[menor] > arr[j]){
                    menor = j;
                }
            }
            int temp = arr[i];
            arr[i] = arr[menor];
            arr[menor] = temp;
        }
    }

    public static void quick(int[] arr, int esq, int dir){
        int i = esq;
        int j = dir;
        int pivo = arr[(dir+esq)/2];

        while (i <= j){
            while (arr[i] < pivo) i++;
            while (arr[j] > pivo) j--;
            
            if (i <= j){
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;
                i++;
                j--;
            }
        }
        if (esq < j) quick(arr, esq, j);
        if (dir > i) quick(arr, i, dir);
    }

    public static int[] insercao(int[] arr){
        int n = arr.length;
        for (int i = 1 ; i < n; i++){
            int valor = arr[i];
            int j = i;

            while (j > 0 && valor < arr[j - 1]){
                arr[j] = arr[j - 1];
                j--;
            }
            arr[j] = valor;
        }
        return arr;
    }
}

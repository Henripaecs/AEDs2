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

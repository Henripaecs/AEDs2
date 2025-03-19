public class QuickSort{

    public static void Print(int array[]) {
        for (int i = 0; i < array.length; i++) {
            System.out.print(array[i] + " ");
        }
        System.out.println();
    }

    public static void Swap(int array[], int l, int r) {
        int temp = array[r];
        array[r] = array[l];
        array[l] = temp;
    }

    public static void Ordena(int array[], int esq, int dir) {

        int l = esq;
        int r = dir;
        int pivot = array[esq];

        while (l <= r) {

            while (array[l] < pivot) {
                l++;
            }
            while (array[r] > pivot ) {
                r--;
            }

            if (l <= r) {
                Swap(array, l, r);
                l++;
                r--;
            }
            Print(array);
        }
        

        if (esq < r) {
            Ordena(array, esq, r);
        }
        if (dir > l) {
            Ordena(array, l, dir);
        }
       

    }

    public static void main(String[] args) {       
        int[] array= {3,7,1,4,0,9,2,5,11};


        Ordena(array, 0, array.length - 1);
    }
}

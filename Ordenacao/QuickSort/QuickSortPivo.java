package Ordenacao.QuickSort;

import java.util.Arrays;
import java.util.Random;

public class QuickSortPivo {
    void swap(int []array, int i, int j){
        int temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    //pivo igual primeiro elemento do array
    void QuickSortFirstPivot(int [] array , int esq , int dir) {
        int i = esq, j = dir;
        int pivo = array[esq];

        while (i <= j){
            while (array[i] < pivo) i++;
            while (array[j] > pivo) j--;
            if ( i <= j){
                swap(array, i, j);
                i++;
                j--;
            }
        }
        if (esq < j) QuickSortFirstPivot(array, esq, j);
        if (i < dir) QuickSortFirstPivot(array, i, dir);
    }

    //pivo igual ultimo elemento do array
    void QuickSortLastPivot(int [] array , int esq , int dir) {
        int i = esq, j = dir;
        int pivo = array[dir];

        while (i <= j){
            while (array[i] < pivo) i++;
            while (array[j] > pivo) j--;
            if ( i <= j){
                swap(array, i, j);
                i++;
                j--;
            }
        }
        if (esq < j) QuickSortLastPivot(array, esq, j);
        if (i < dir) QuickSortLastPivot(array, i, dir);
    }

    //pivo igual a um elemento aleatorio do array
    void QuickSortRandomPivot(int[] array, int esq, int dir) {
        if (esq >= dir) return; 

        int random = esq + (int)(Math.random() * (dir - esq + 1));
        swap(array, random, dir);
        int pivo = array[dir];

        int i = esq, j = dir;

        while (i <= j) {
            while (array[i] < pivo) i++;
            while (array[j] > pivo) j--;
            if (i <= j) {
                swap(array, i, j);
                i++;
                j--;
            }
        }

        if (esq < j) QuickSortRandomPivot(array, esq, j);
        if (i < dir) QuickSortRandomPivot(array, i, dir);
    }

    //mediana 
    public static int meidana(int []array, int esq , int dir){ 
        int meio = (esq + dir) / 2;

        int a = array[esq];
        int b = array[meio];
        int c = array[dir];

        if ((a > b) != (a > c)) return esq;
        else if ((b > a) != (b > c)) return meio;
        else return dir;


    }
    //pivo igual a mediana de 3(inicio, meio e fim) dos elementos do array
    void QuickSortMedianaOfThreePivot(int [] array , int esq , int dir) {
        int i = esq, j = dir;
        int pivo = array[meidana(array, esq, dir)];

        while (i <= j){
            while (array[i] < pivo) i++;
            while (array[j] > pivo) j--;
            if ( i <= j){
                swap(array, i, j);
                i++;
                j--;
            }
        }
        if (esq < j) QuickSortMedianaOfThreePivot(array, esq, j);
        if (i < dir) QuickSortMedianaOfThreePivot(array, i, dir);
    }

     public static void main(String[] args) {
        QuickSortPivo q = new QuickSortPivo();
        Random rand = new Random();

        int[] tamanhos = {100, 1000, 10000};

        for (int tam : tamanhos) {
            // cria array de numeros randons
            int[] base = new int[tam];
            for (int i = 0; i < tam; i++) {
                base[i] = rand.nextInt(100000);
            }

            System.out.println("\n Testando com " + tam + " elementos ordenados:");

            // primeiro pivo
            int[] arr1 = Arrays.copyOf(base, base.length);
            long ini = System.nanoTime();
            q.QuickSortFirstPivot(arr1, 0, arr1.length - 1);
            long fim = System.nanoTime();
            System.out.println("pivo primeiro: " + (fim - ini) / 1_000_000.0 + " ms");

            // ultimo pivo
            int[] arr2 = Arrays.copyOf(base, base.length);
            ini = System.nanoTime();
            q.QuickSortLastPivot(arr2, 0, arr2.length - 1);
            fim = System.nanoTime();
            System.out.println("pivo ultimo: " + (fim - ini) / 1_000_000.0 + " ms");

            // random
            int[] arr3 = Arrays.copyOf(base, base.length);
            ini = System.nanoTime();
            q.QuickSortRandomPivot(arr3, 0, arr3.length - 1);
            fim = System.nanoTime();
            System.out.println("pivo random: " + (fim - ini) / 1_000_000.0 + " ms");

            // mediana de 3(incio, meio e fim)
            int[] arr4 = Arrays.copyOf(base, base.length);
            ini = System.nanoTime();
            q.QuickSortMedianaOfThreePivot(arr4, 0, arr4.length - 1);
            fim = System.nanoTime();
            System.out.println("pivo mediana de tres: " + (fim - ini) / 1_000_000.0 + " ms");
        }
    }

}   

/*
public class FilaCircular {
    private int[] array;
    private int primeiro;
    private int ultimo;

    public FilaCircular(int capacidade) {
        array = new int[capacidade];
        primeiro = 0;
        ultimo = -1;
    }

    public boolean isFull(){
        return primeiro == ultimo;
    }
    
    public boolean isEmpty(){
        return (ultimo + 1) % array.length == primeiro;
    }
    
    public void enfileirar(int valor){
        if (isFull()){
            throw new RuntimeException("Fila cheia");
        }

        ultimo = (ultimo + 1)  % array.length;
        array[ultimo] = valor;
    } 

    public int desenfileirar(){
        if (isEmpty()){
            throw new RuntimeException("Fila vazia");
        }
        int valor = array[primeiro];
        primeiro = (primeiro + 1) & array.length;
        return valor;
    }

    public int removerMariorQue10(){
        if (isEmpty()) return -1;
        
        int n = (ultimo - primeiro + array.length) % array.length;

        for (int i = 0; i < n; i++){
            int valor = desenfileirar();
            if(valor > 10){
                return valor;
            } else {
                enfileirar(valor);
            }
        }
        return -1;
    }
    

    // apenas para debug
    public void mostrar() {
        System.out.print("Fila: ");
        int i = primeiro;
        while (i != ultimo) {
            System.out.print(array[i] + " ");
            i = (i + 1) % array.length;
        }
        System.out.println();
    }
}
*/


public void sort(){
    for (int i = 1; i < n; i++){
        int temp = array[i];
        int j = i -1;

        if(temp > 0){
            while ((j >= 0) && (array[j] > temp || array[j] < 0)){
                array[j + 1] = array[j];
                j--;
            }
            array[j + 1] = temp;
        } 
    }
}


public void sort(){
    for (int i = (n - 1); i > 0; i--){
        for (int j = 0; j < i; j++){
            if (arr[j] > arr[j + 1]){
                int temp = arr[j];
                arr[j] = arr[j + 1];
                arr[j + 1] = temp;
            }
        }
    }
}
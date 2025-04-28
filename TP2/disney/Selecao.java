package disney;

import java.io.*;
import java.util.*;

public class Selecao {
    public static void main(String[] args) throws IOException {
        ArrayList<Show> lista = new ArrayList<>();
        Map<String, String> dadosCSV = new HashMap<>();
        Scanner sc = new Scanner(System.in);

        //BufferedReader br = new BufferedReader(new FileReader("./disneyplus.csv")); // maquina
        BufferedReader br = new BufferedReader(new FileReader("/tmp/disneyplus.csv")); // verde

        br.readLine(); 
        String linha;
        
        while ((linha = br.readLine()) != null) {
            String id = linha.split(",")[0];
            dadosCSV.put(id, linha);
        }
        br.close();

        String entrada;
        while (!(entrada = sc.nextLine()).equals("FIM")) {
            if (dadosCSV.containsKey(entrada)) {
                Show show = new Show();
                show.ler(dadosCSV.get(entrada));
                lista.add(show);
            }
        }
        sc.close();

        int comparacoes = 0;
        int movimentacoes = 0;

        long inicio = System.nanoTime();

        //Selection Sort
        for (int i = 0; i < lista.size() - 1; i++) {
            int menor = i;
            for (int j = i + 1; j < lista.size(); j++) {
                comparacoes++;
                if (lista.get(j).getTitle().compareTo(lista.get(menor).getTitle()) < 0) {
                    menor = j;
                }
            }
            if (menor != i) {
                Show temp = lista.get(i).clone();
                lista.set(i, lista.get(menor).clone());
                lista.set(menor, temp.clone());
                movimentacoes += 3; //3 movis
            }
        }

        long fim = System.nanoTime();
        double tempoExecucao = (fim - inicio) / 1_000_000.0;

        for (Show s : lista) {
            s.imprimir();
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter("846431_selecao.txt"));
        bw.write("846431\t" + comparacoes + "\t" + movimentacoes + "\t" + String.format("%.2f", tempoExecucao));
        bw.close();
    }
}

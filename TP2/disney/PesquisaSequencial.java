package disney;

import java.io.*;
import java.util.*;
import java.util.Scanner;


public class PesquisaSequencial {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        ArrayList<Show> vetor = new ArrayList<>();
        ArrayList<String> buscar = new ArrayList<>();

        BufferedReader br = new BufferedReader(new FileReader("./disneyplus.csv")); // máquina local
        //BufferedReader br = new BufferedReader(new FileReader("/tmp/disneyplus.csv")); // verde

        br.readLine(); 

        while (true) {
            String linha = sc.nextLine();
            if (linha.equals("FIM")) {
                break;
            }
            Show novo = new Show();
            novo.setTitle(linha);
            vetor.add(novo);
        }

        while (true) {
            String linha = sc.nextLine();
            if (linha.equals("FIM")) {
                break;
            }
            buscar.add(linha);
        }

        long inicio = System.nanoTime();
        int comparacoes = 0;

        //sequencial
        for (String titulo : buscar) {
            boolean encontrado = false;
            for (Show s : vetor) {
                comparacoes++;
                if (s.getTitle().trim().equalsIgnoreCase(titulo.trim())) {
                    encontrado = true;
                    break;
                }
            }
            if (encontrado) {
                System.out.println("SIM");
            } else {
                System.out.println("NAO");
            }
        }

        long fim = System.nanoTime();
        double tempo = (fim - inicio) / 1_000_000.0;

        //log
        String matricula = "846431"; 
        BufferedWriter bw = new BufferedWriter(new FileWriter(matricula + "_sequencial.txt"));
        bw.write(matricula + "\t" + tempo + "\t" + comparacoes);
        bw.close();
    }
}
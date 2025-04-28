package disney;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Scanner;

public class Show implements Cloneable {
    private String show_id;
    private String type;
    private String title;
    private String director;
    private String[] cast;
    private String country;
    private Date date_added;
    private int release_year;
    private String rating;
    private String duration;
    private String[] listed_in;

    public Show() {
        this.show_id = "NaN";
        this.type = "NaN";
        this.title = "NaN";
        this.director = "NaN";
        this.cast = new String[]{"NaN"};
        this.country = "NaN";
        this.date_added = null;
        this.release_year = -1;
        this.rating = "NaN";
        this.duration = "NaN";
        this.listed_in = new String[]{"NaN"};
    }

    public Show(String show_id, String type, String title, String director, String[] cast, String country,
                Date date_added, int release_year, String rating, String duration, String[] listed_in) {
        this.show_id = show_id;
        this.type = type;
        this.title = title;
        this.director = director;
        this.cast = cast;
        this.country = country;
        this.date_added = date_added;
        this.release_year = release_year;
        this.rating = rating;
        this.duration = duration;
        this.listed_in = listed_in;
    }

    public String getShow_id() { return show_id; }
    public void setShow_id(String show_id) { this.show_id = show_id; }

    public String getType() { return type; }
    public void setType(String type) { this.type = type; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDirector() { return director; }
    public void setDirector(String director) { this.director = director; }

    public String[] getCast() { return cast; }
    public void setCast(String[] cast) { this.cast = cast; }

    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }

    public Date getDate_added() { return date_added; }
    public void setDate_added(Date date_added) { this.date_added = date_added; }

    public int getRelease_year() { return release_year; }
    public void setRelease_year(int release_year) { this.release_year = release_year; }

    public String getRating() { return rating; }
    public void setRating(String rating) { this.rating = rating; }

    public String getDuration() { return duration; }
    public void setDuration(String duration) { this.duration = duration; }

    public String[] getListed_in() { return listed_in; }
    public void setListed_in(String[] listed_in) { this.listed_in = listed_in; }

    //  clone
    public Show clone() {
        return new Show(show_id, type, title, director, cast.clone(), country, date_added, release_year, rating, duration, listed_in.clone());
    }

    // imprimir
    public void imprimir() {
        SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
        String dataStr = (date_added != null) ? sdf.format(date_added) : "NaN";
    
        String[] castOrdenado = cast.clone();
        Arrays.sort(castOrdenado, String.CASE_INSENSITIVE_ORDER);
    
        String titleLimpo = title.replaceAll("\"", "");

        for (int i = 0; i < castOrdenado.length; i++){
            castOrdenado[i] = castOrdenado[i].replaceAll("\"", "");
        } 
    
        System.out.println("=> " + show_id + " ## " + titleLimpo + " ## " + type + " ## " + director +
                " ## " + Arrays.toString(castOrdenado) + " ## " + country + " ## " + dataStr + " ## " +
                release_year + " ## " + rating + " ## " + duration + " ## " + Arrays.toString(listed_in) + " ##");
    }
    

    public void ler(String linha) {
        SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, yyyy");
        String[] campos = new String[12];

        String[] partes = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);
        for (int i = 0; i < campos.length; i++) {
            campos[i] = (i < partes.length && !partes[i].trim().isEmpty()) ? partes[i].trim().replaceAll("^\"|\"$", "") : "NaN";
        }

        this.show_id = campos[0];
        this.type = campos[1];
        this.title = campos[2];
        this.director = campos[3];
        this.cast = campos[4].equals("NaN") ? new String[]{"NaN"} : campos[4].split(", ");
        this.country = campos[5];
        try {
            this.date_added = campos[6].equals("NaN") ? null : sdf.parse(campos[6]);
        } catch (ParseException e) {
            this.date_added = null;
        }
        this.release_year = campos[7].equals("NaN") ? -1 : Integer.parseInt(campos[7]);
        this.rating = campos[8];
        this.duration = campos[9];
        this.listed_in = campos[10].equals("NaN") ? new String[]{"NaN"} : campos[10].split(", ");
    }


    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    //SHOW
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    /*public static void main(String[] args) throws IOException {
        Map<String, String> dadosCSV = new HashMap<>();
        //BufferedReader br = new BufferedReader(new FileReader("./disneyplus.csv"));//maquina
        BufferedReader br = new BufferedReader(new FileReader("/tmp/disneyplus.csv"));//verde 

        br.readLine();

        String linha;
        while ((linha = br.readLine()) != null) {
            String id = linha.split(",")[0];
            dadosCSV.put(id, linha);
        }
        br.close();

        Scanner sc = new Scanner(System.in);
        String entrada;

        while (!(entrada = sc.nextLine()).equals("FIM")) {
            if (dadosCSV.containsKey(entrada)) {
                Show show = new Show();
                show.ler(dadosCSV.get(entrada));
                show.imprimir();
            } else {
                System.out.println("=> NaN ## NaN ## NaN ## NaN ## [NaN] ## NaN ## NaN ## -1 ## NaN ## NaN ## [NaN] ##");
            }
        }

        sc.close();
    }*/

    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    //PESQUISA SEQUENCIAL
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||

   /*public static void main(String[] args) throws IOException {
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
*/
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    //ORDENACAO POR SELECAO
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    /*public static void main(String[] args) throws IOException {
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
    }*/
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    //ORDENACAO POR INSERCAO
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    /*public static void main(String[] args) throws IOException {
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

        //Insertion Sort
        for (int i = 1; i < lista.size(); i++) {
            Show chave = lista.get(i).clone();
            int j = i - 1;
        
            comparacoes++;
            while (j >= 0 && (
                    lista.get(j).getType().compareTo(chave.getType()) > 0 ||
                    (lista.get(j).getType().compareTo(chave.getType()) == 0 &&
                     lista.get(j).getTitle().compareTo(chave.getTitle()) > 0)
                  )) 
            {
                comparacoes++;
                lista.set(j + 1, lista.get(j).clone());
                movimentacoes++;
                j--;
            }
            lista.set(j + 1, chave.clone());
            movimentacoes++;
        }

        long fim = System.nanoTime();
        double tempoExecucao = (fim - inicio) / 1_000_000.0;

        for (Show s : lista) {
            s.imprimir();
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter("846431_insercao.txt"));
        bw.write("846431\t" + comparacoes + "\t" + movimentacoes + "\t" + String.format("%.2f", tempoExecucao));
        bw.close();
    }*/ 
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    //ORDENACAO POR HEAPSORT
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    /*
    //HeapSort
    private static void heapSort(ArrayList<Show> lista, int comparacoes[], int movimentacoes[]) {
        int n = lista.size();

        for (int i = n / 2 - 1; i >= 0; i--) {
            heapify(lista, n, i, comparacoes, movimentacoes);
        }

        for (int i = n - 1; i > 0; i--) {
            Show temp = lista.get(0).clone();
            lista.set(0, lista.get(i).clone());
            lista.set(i, temp.clone());
            movimentacoes[0] += 3;

            heapify(lista, i, 0, comparacoes, movimentacoes);
        }
    }

    private static void heapify(ArrayList<Show> lista, int n, int i, int comparacoes[], int movimentacoes[]) {
        int maior = i;
        int esquerda = 2 * i + 1;
        int direita = 2 * i + 2;

        if (esquerda < n) {
            comparacoes[0]++;
            if (compare(lista.get(esquerda), lista.get(maior)) > 0) {
                maior = esquerda;
            }
        }

        if (direita < n) {
            comparacoes[0]++;
            if (compare(lista.get(direita), lista.get(maior)) > 0) {
                maior = direita;
            }
        }

        if (maior != i) {
            Show troca = lista.get(i).clone();
            lista.set(i, lista.get(maior).clone());
            lista.set(maior, troca.clone());
            movimentacoes[0] += 3;

            heapify(lista, n, maior, comparacoes, movimentacoes);
        }
    }

    private static int compare(Show a, Show b) {
        int cmp = a.getDirector().compareTo(b.getDirector());
        if (cmp != 0) {
            return cmp;
        }
        return a.getTitle().compareTo(b.getTitle());
    }

    public static void main(String[] args) throws IOException {
        ArrayList<Show> lista = new ArrayList<>();
        Map<String, String> dadosCSV = new HashMap<>();
        Scanner sc = new Scanner(System.in);

        //BufferedReader br = new BufferedReader(new FileReader("./disneyplus.csv")); //maquina
        BufferedReader br = new BufferedReader(new FileReader("/tmp/disneyplus.csv")); //verde

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

        int[] comparacoes = {0};
        int[] movimentacoes = {0};

        long inicio = System.nanoTime();

        heapSort(lista, comparacoes, movimentacoes);

        long fim = System.nanoTime();
        double tempoExecucao = (fim - inicio) / 1_000_000.0;

        for (Show s : lista) {
            s.imprimir();
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter("846431_heapsort.txt"));
        bw.write("846431\t" + comparacoes[0] + "\t" + movimentacoes[0] + "\t" + String.format("%.2f", tempoExecucao));
        bw.close();
    }*/
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    //ORDENACAO POR COUNTING
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    /*
    private static void countingSort(ArrayList<Show> lista, int comparacoes[], int movimentacoes[]) {
        if (lista.isEmpty()) return;
    
        int minYear = lista.get(0).getRelease_year();
        int maxYear = lista.get(0).getRelease_year();
    
        for (Show s : lista) {
            if (s.getRelease_year() < minYear) minYear = s.getRelease_year();
            if (s.getRelease_year() > maxYear) maxYear = s.getRelease_year();
        }
    
        int range = maxYear - minYear + 1;
    
        ArrayList<ArrayList<Show>> buckets = new ArrayList<>(range);
        for (int i = 0; i < range; i++) {
            buckets.add(new ArrayList<>());
        }
    
        for (Show s : lista) {
            buckets.get(s.getRelease_year() - minYear).add(s.clone());
            movimentacoes[0]++;
        }
    
        lista.clear();
        for (ArrayList<Show> bucket : buckets) {
            if (!bucket.isEmpty()) {
                bucket.sort((a, b) -> {
                    comparacoes[0]++;
                    return a.getTitle().compareTo(b.getTitle());
                });
    
                for (Show s : bucket) {
                    lista.add(s);
                    movimentacoes[0]++;
                }
            }
        }
    }
    
    public static void main(String[] args) throws IOException {
        ArrayList<Show> lista = new ArrayList<>();
        Map<String, String> dadosCSV = new HashMap<>();
        Scanner sc = new Scanner(System.in);

        //BufferedReader br = new BufferedReader(new FileReader("./disneyplus.csv")); //maquina
        BufferedReader br = new BufferedReader(new FileReader("/tmp/disneyplus.csv")); //verde

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

        int[] comparacoes = {0};
        int[] movimentacoes = {0};

        long inicio = System.nanoTime();

        countingSort(lista, comparacoes, movimentacoes);

        long fim = System.nanoTime();
        double tempoExecucao = (fim - inicio) / 1_000_000.0;

        for (Show s : lista) {
            s.imprimir();
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter("846431_countingsort.txt"));
        bw.write("846431\t" + comparacoes[0] + "\t" + movimentacoes[0] + "\t" + String.format("%.2f", tempoExecucao));
        bw.close();
    }
    */
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    //ORDENACAO POR MERGESORT   
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    /*private static void mergeSort(ArrayList<Show> lista, int comparacoes[], int movimentacoes[]) {
        if (lista.size() <= 1) return;
        mergeSort(lista, 0, lista.size() - 1, comparacoes, movimentacoes);
    }
    
    private static void mergeSort(ArrayList<Show> lista, int esq, int dir, int comparacoes[], int movimentacoes[]) {
        if (esq < dir) {
            int meio = (esq + dir) / 2;
            mergeSort(lista, esq, meio, comparacoes, movimentacoes);
            mergeSort(lista, meio + 1, dir, comparacoes, movimentacoes);
            merge(lista, esq, meio, dir, comparacoes, movimentacoes);
        }
    }
    
    private static void merge(ArrayList<Show> lista, int esq, int meio, int dir, int comparacoes[], int movimentacoes[]) {
        ArrayList<Show> esquerda = new ArrayList<>();
        ArrayList<Show> direita = new ArrayList<>();
    
        for (int i = esq; i <= meio; i++) esquerda.add(lista.get(i).clone());
        for (int i = meio + 1; i <= dir; i++) direita.add(lista.get(i).clone());
    
        int i = 0, j = 0, k = esq;
        while (i < esquerda.size() && j < direita.size()) {
            comparacoes[0]++;
            if (compareDurationAndTitle(esquerda.get(i), direita.get(j)) <= 0) {
                lista.set(k++, esquerda.get(i++).clone());
            } else {
                lista.set(k++, direita.get(j++).clone());
            }
            movimentacoes[0]++;
        }
    
        while (i < esquerda.size()) {
            lista.set(k++, esquerda.get(i++).clone());
            movimentacoes[0]++;
        }
    
        while (j < direita.size()) {
            lista.set(k++, direita.get(j++).clone());
            movimentacoes[0]++;
        }
    }
    
    private static int compareDurationAndTitle(Show a, Show b) {
        int durA = parseDuration(a.getDuration());
        int durB = parseDuration(b.getDuration());
        int cmp = Integer.compare(durA, durB);
    
        if (cmp != 0) {
            return cmp;
        }
        return a.getTitle().compareTo(b.getTitle());
    }
    
    private static int parseDuration(String duration) {
        try {
            if (duration.contains("min")) {
                return Integer.parseInt(duration.replaceAll("[^0-9]", ""));
            } else if (duration.contains("Season") || duration.contains("Seasons")) {
                return Integer.parseInt(duration.replaceAll("[^0-9]", "")) * 1000; 
            }
        } catch (Exception e) {
        }
        return 0;
    }
    public static void main(String[] args) throws IOException {
        ArrayList<Show> lista = new ArrayList<>();
        Map<String, String> dadosCSV = new HashMap<>();
        Scanner sc = new Scanner(System.in);

        //BufferedReader br = new BufferedReader(new FileReader("./disneyplus.csv")); //maquina
        BufferedReader br = new BufferedReader(new FileReader("/tmp/disneyplus.csv")); //verde

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

        int[] comparacoes = {0};
        int[] movimentacoes = {0};

        long inicio = System.nanoTime();

        mergeSort(lista, comparacoes, movimentacoes);

        long fim = System.nanoTime();
        double tempoExecucao = (fim - inicio) / 1_000_000.0;

        for (Show s : lista) {
            s.imprimir();
        }

        BufferedWriter bw = new BufferedWriter(new FileWriter("846431_mergesort.txt"));
        bw.write("846431\t" + comparacoes[0] + "\t" + movimentacoes[0] + "\t" + String.format("%.2f", tempoExecucao));
        bw.close();
    }
    */
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    //SELECAO PARCIAL DO SELECAO
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    /*public static void main(String[] args) throws IOException {
        ArrayList<Show> lista = new ArrayList<>();
        Map<String, String> dadosCSV = new HashMap<>();
        Scanner sc = new Scanner(System.in);
    
        //BufferedReader br = new BufferedReader(new FileReader("./disneyplus.csv")); //maquina
        BufferedReader br = new BufferedReader(new FileReader("/tmp/disneyplus.csv")); //verde
    
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
    
        int limite = Math.min(10, lista.size());
        for (int i = 0; i < limite; i++) {
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
                movimentacoes += 3; //3 movi
            }
        }
    
        long fim = System.nanoTime();
        double tempoExecucao = (fim - inicio) / 1_000_000.0;
    
        for (int i = 0; i < limite; i++) {
            lista.get(i).imprimir();
        }
    
        BufferedWriter bw = new BufferedWriter(new FileWriter("846431_selecao.txt"));
        bw.write("846431\t" + comparacoes + "\t" + movimentacoes + "\t" + String.format("%.2f", tempoExecucao));
        bw.close();
    }
    */
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    //QUICKSORT PARCIAL 
    //|||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||
    
    private static void quickSort(ArrayList<Show> lista, int esq, int dir, int comparacoes[], int movimentacoes[]) {
        if (esq < dir) {
            int pivo = partition(lista, esq, dir, comparacoes, movimentacoes);
            quickSort(lista, esq, pivo - 1, comparacoes, movimentacoes);
            quickSort(lista, pivo + 1, dir, comparacoes, movimentacoes);
        }
    }
    
    private static int partition(ArrayList<Show> lista, int esq, int dir, int comparacoes[], int movimentacoes[]) {
        Show pivo = lista.get(dir).clone();
        int i = esq - 1;
        for (int j = esq; j < dir; j++) {
            comparacoes[0]++;
            if (lista.get(j).getTitle().compareTo(pivo.getTitle()) <= 0) {
                i++;
                Show temp = lista.get(i).clone();
                lista.set(i, lista.get(j).clone());
                lista.set(j, temp.clone());
                movimentacoes[0] += 3;
            }
        }
        Show temp = lista.get(i + 1).clone();
        lista.set(i + 1, lista.get(dir).clone());
        lista.set(dir, temp.clone());
        movimentacoes[0] += 3;
        return i + 1;
    }
    public static void main(String[] args) throws IOException {
        ArrayList<Show> lista = new ArrayList<>();
        Map<String, String> dadosCSV = new HashMap<>();
        Scanner sc = new Scanner(System.in);
    
        //BufferedReader br = new BufferedReader(new FileReader("./disneyplus.csv")); //maquina
        BufferedReader br = new BufferedReader(new FileReader("/tmp/disneyplus.csv")); //verde
    
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
    
        quickSort(lista, comparacoes, movimentacoes, null, null);

        long inicio = System.nanoTime();
    
        int limite = Math.min(10, lista.size());
        for (int i = 0; i < limite; i++) {
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
                movimentacoes += 3; //3 movi
            }
        }
    
        long fim = System.nanoTime();
        double tempoExecucao = (fim - inicio) / 1_000_000.0;
    
        for (int i = 0; i < limite; i++) {
            lista.get(i).imprimir();
        }
    
        BufferedWriter bw = new BufferedWriter(new FileWriter("846431_quicksort.txt"));
        bw.write("846431\t" + comparacoes + "\t" + movimentacoes + "\t" + String.format("%.2f", tempoExecucao));
        bw.close();
    }
}

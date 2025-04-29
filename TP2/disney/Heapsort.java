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

class Show {
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
}

public class Heapsort {
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
    }
}

import java.io.*;
import java.util.*;

class Show {

    private String showId;
    private String type;
    private String title;
    private String director;
    private String[] cast;
    private String country;
    private String dateAdded;
    private int releaseYear;
    private String rating;
    private String duration;
    private String[] listedIn;

    Show() {
        this.showId = null;
        this.type = null;
        this.title = null;
        this.director = null;
        this.cast = null;
        this.country = null;
        this.dateAdded = null;
        this.releaseYear = 0;
        this.rating = null;
        this.duration = null;
        this.listedIn = null;
    }

    Show(String showId, String type, String title, String director, String[] cast, String country,
            String dateAdded, int releaseYear, String rating, String duration, String[] listedIn) {
        this.showId = showId;
        this.type = type;
        this.title = title;
        this.director = director;
        this.cast = new String[cast.length];
        this.country = country;
        this.dateAdded = dateAdded;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.duration = duration;
        this.listedIn = new String[listedIn.length];
    }

    public String getShowId() {

        return showId;
    }

    public void setShowId(String x) {

        this.showId = (x.equals("")) ? "NaN" : x;
    }

    public String getType() {

        return type;
    }

    public void setType(String x) {

        this.type = (x.equals("")) ? "NaN" : x;
    }

    public String getTitle() {

        return title;
    }

    public void setTitle(String x) {
        this.title = (x.equals("")) ? "NaN" : x;
    }

    public String getDirector() {

        return director;
    }

    public void setDirector(String x) {

        this.director = (x.equals("")) ? "NaN" : x;
    }

    public String[] getCast() {

        return cast;
    }

    public void setCast(String[] x) {

        if (x.length == 1 && x[0].equals("")) {

            this.cast = new String[] { "NaN" };
        } else {

            this.cast = x;
        }
    }

    public String getCountry() {

        return country;
    }

    public void setCountry(String x) {

        this.country = (x.equals("")) ? "NaN" : x;
    }

    public String getDateAdded() {

        return dateAdded;
    }

    public void setDateAdded(String x) {

        this.dateAdded = (x.equals("")) ? "NaN" : x;
    }

    public int getReleaseYear() {

        return releaseYear;
    }

    public void setReleaseYear(int x) {

        this.releaseYear = x;
    }

    public String getRating() {

        return rating;
    }

    public void setRating(String x) {

        this.rating = (x.equals("")) ? "NaN" : x;
    }

    public String getDuration() {

        return duration;
    }

    public void setDuration(String x) {

        this.duration = (x.equals("")) ? "NaN" : x;
    }

    public String[] getListedIn() {

        return listedIn;
    }

    public void setListedIn(String[] listedIn) {

        if (listedIn.length == 1 && listedIn[0].equals("")) {

            this.listedIn = new String[] { "NaN" };
        } else {

            this.listedIn = listedIn;
        }
    }

    /**
     * Função para clonar um show.
     * 
     * @return novo objeto Show com os mesmos dados
     */
    public Show Clone() {
        return new Show(this.showId, this.type, this.title, this.director, this.cast, this.country, this.dateAdded,
                this.releaseYear, this.rating, this.duration, this.listedIn);
    }

    /**
     * Função para transformar String em inteiro
     * 
     * @param str String a ser transformada
     */
    public int StrToInt(String str) {

        // Definir dados
        int len = str.length();
        int data = 0;

        for (int i = 0; i < len; i++) {

            char c = str.charAt(i);
            data = (10 * data) + (c - '0');
        }

        return data;
    }

    /**
     * Leitura de dados da entrada e atribuições
     * 
     * @param in Entrada de dados
     */
    public void Ler(String in) throws Exception {

        // Definir dados
        String simplify = "";
        boolean flag = true;
        int len = in.length();

        // Varrer String para verificar se está entre aspas
        for (int i = 0; i < len; i++) {

            char c = in.charAt(i);
            if (c == '"')
                flag = !flag;
            else if (c == ',' && flag)
                simplify += '|';
            else
                simplify += c;
        }

        // Array de Strings simplificado
        String[] ShowStr = simplify.split("\\|");
        // Settando valores
        setShowId(ShowStr[0]);
        setType(ShowStr[1]);
        setTitle(ShowStr[2]);
        setDirector(ShowStr[3]);

        // Ajuste na formatação
        String[] CastAux = ShowStr[4].split(",\\s*");
        int castl = CastAux.length;
        quickSort(0, castl - 1, CastAux);
        setCast(CastAux);
        setCountry(ShowStr[5]);
        setDateAdded(ShowStr[6]);

        // Ajuste de variaveis
        setReleaseYear(StrToInt(ShowStr[7]));
        setRating(ShowStr[8]);
        setDuration(ShowStr[9]);

        // Ajuste na formatação
        String[] ListedInAux = ShowStr[10].split(",\\s*");
        int ListedLen = ListedInAux.length;
        quickSort(0, ListedLen - 1, ListedInAux);
        setListedIn(ListedInAux);

    }

    /**
     * Função para criar Show pela String de entrada
     * 
     * @param in String de entrada
     * @return Show criado
     */
    public static Show Create(String in) throws Exception {
        Show created = new Show();
        created.Ler(in);
        return created;
    }

    /**
     * Leitura do arquivo CSV
     * 
     * @param caminho Caminho do CSV
     */
    public static ArrayList<Show> csv(String caminho) throws Exception {
        ArrayList<Show> shows = new ArrayList<>();
        File file = new File(caminho);
        if (!file.exists()) {
            throw new FileNotFoundException("Arquivo não encontrado: " + caminho);
        }
        try (Scanner sc = new Scanner(file)) {
            while (sc.hasNext()) {
                String data = sc.nextLine();
                shows.add(Create(data));
            }
        }
        return shows;
    }

    /**
     * Função para trocar dois elementos do array
     * 
     * @param i     Índice do primeiro elemento
     * @param j     Índice do segundo elemento
     * @param array Array de Strings
     */
    public static void swap(int i, int j, String[] array) {

        String temp = array[i];
        array[i] = array[j];
        array[j] = temp;
    }

    /**
     * Método QuickSort de ordenação para Strings
     * 
     * @param esq   Índice inicial
     * @param dir   Índice final
     * @param array Array de Strings a ser ordenado
     */
    public static void quickSort(int esq, int dir, String[] array) {
        if (array == null || array.length == 0 || esq >= dir)
            return;
        int i = esq, j = dir;
        String pivo = array[esq + (dir - esq) / 2];
        while (i <= j) {
            while (array[i].compareTo(pivo) < 0)
                i++;
            while (array[j].compareTo(pivo) > 0)
                j--;
            if (i <= j) {
                swap(i, j, array);
                i++;
                j--;
            }
        }
        // Chamada recursiva
        if (esq < j)
            quickSort(esq, j, array);
        if (i < dir)
            quickSort(i, dir, array);
    }

    public void Imprimir() {

        System.out.print("=> ");
        System.out.print(showId + " ## ");
        System.out.print(title + " ## ");
        System.out.print(type + " ## ");
        System.out.print(director + " ## ");
        System.out.print("[");
        if (cast != null && cast.length > 0)
            System.out.print(String.join(", ", cast));
        System.out.print("]");
        System.out.print(" ## ");
        System.out.print(country + " ## ");
        System.out.print(dateAdded + " ## ");
        System.out.print(releaseYear + " ## ");
        System.out.print(rating + " ## ");
        System.out.print(duration + " ## ");
        System.out.print("[");
        // Categorias
        if (listedIn != null && listedIn.length > 0)
            System.out.print(String.join(", ", listedIn));
        System.out.print("] ## ");
        System.out.println();
    }
}

class HashReserva {
    private Show[] tabela;
    private int tamHash = 21;
    private int tamReserva = 9;
    private int posReserva = 21; // início da área de reserva
    private int reservaUsada = 0;

    public HashReserva() {
        tabela = new Show[tamHash + tamReserva];
    }

    private int hash(String title) {
        int soma = 0;
        for (int i = 0; i < title.length(); i++) {
            soma += (int) title.charAt(i);
        }
        return soma % tamHash;
    }

    public void inserir(Show show) {
        int pos = hash(show.getTitle());
        if (tabela[pos] == null) {
            tabela[pos] = show;
        } else {
            // Não insere duplicata
            if (tabela[pos].getTitle().equals(show.getTitle()))
                return;
            for (int i = 0; i < reservaUsada; i++) {
                if (tabela[posReserva + i] != null && tabela[posReserva + i].getTitle().equals(show.getTitle()))
                    return; // já existe
            }
            if (reservaUsada < tamReserva) {
                tabela[posReserva + reservaUsada] = show;
                reservaUsada++;
            }
            // Se reserva cheia, ignora inserção
        }
    }

    public int[] pesquisarDetalhado(String title) {
        int pos = hash(title);
        if (tabela[pos] != null && tabela[pos].getTitle().equals(title)) {
            return new int[] { pos, 1 }; // achou na hash
        }
        for (int i = 0; i < reservaUsada; i++) {
            if (tabela[posReserva + i] != null && tabela[posReserva + i].getTitle().equals(title)) {
                return new int[] { posReserva + i, 1 };
            }
        }
        return new int[] { pos, 0 };
    }
}

public class TabelaHash {
    public static void main(String[] args) throws Exception {
        long inicio = System.currentTimeMillis();
        Show showManager = new Show();
        ArrayList<Show> shows = showManager.csv("/tmp/disneyplus.csv");

        HashReserva tabela = new HashReserva();

        // Inserção dos registros (entrada padrão até "FIM")
        Scanner sc = new Scanner(System.in);
        String linha = sc.nextLine();
        while (!linha.equals("FIM")) {
            for (Show s : shows) {
                if (s != null && s.getShowId().equals(linha)) {
                    tabela.inserir(s);
                    break;
                }
            }
            linha = sc.nextLine();
        }

        // Pesquisa (entrada padrão até "FIM")
        linha = sc.nextLine();
        while (!linha.equals("FIM")) {
            int[] resp = tabela.pesquisarDetalhado(linha);
            System.out.print(" (Posicao: " + resp[0] + ") ");
            System.out.println(resp[1] == 1 ? "SIM" : "NAO");
            linha = sc.nextLine();
        }

        long fim = System.currentTimeMillis();
        double tempo = (fim - inicio) / 1000.0;

        // Log
        try (PrintWriter log = new PrintWriter("846431_hashReserva.txt")) {
            log.printf("846431\t%.3f\n", tempo);
        }
    }
}
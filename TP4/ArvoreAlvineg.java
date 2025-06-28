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
            String dateAdded, int releaseYear, String rating, String duration, String[] listedIn){
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

    public String getShowId(){

        return showId;
    }

    public void setShowId(String x){

        this.showId = (x.equals("")) ? "NaN" : x;
    }

    public String getType(){

        return type;
    }

    public void setType(String x){
        
        this.type = (x.equals("")) ? "NaN" : x;
    }

    public String getTitle(){
        
        return title;
    }

    public void setTitle(String x){
        this.title = (x.equals("")) ? "NaN" : x;
    }

    public String getDirector(){
        
        return director;
    }

    public void setDirector(String x){

        this.director = (x.equals("")) ? "NaN" : x;
    }

    public String[] getCast(){
        
        return cast;
    }

    public void setCast(String[] x){

        if(x.length == 1 && x[0].equals("")){

            this.cast = new String[] { "NaN" };
        } else {

            this.cast = x;
        }
    }

    public String getCountry(){

        return country;
    }

    
    public void setCountry(String x){

        this.country = (x.equals("")) ? "NaN" : x;
    }

    public String getDateAdded(){
        
        return dateAdded;
    }

    public void setDateAdded(String x){

        this.dateAdded = (x.equals("")) ? "NaN" : x;
    }

    public int getReleaseYear(){

        return releaseYear;
    }

    public void setReleaseYear(int x){

        this.releaseYear = x;
    }

    public String getRating(){
        
        return rating;
    }

    public void setRating(String x){

        this.rating = (x.equals("")) ? "NaN" : x;
    }

    public String getDuration(){

        return duration;
    }

    public void setDuration(String x){

        this.duration = (x.equals("")) ? "NaN" : x;
    }

    public String[] getListedIn(){
        
        return listedIn;
    }

    public void setListedIn(String[] listedIn){

        if(listedIn.length == 1 && listedIn[0].equals("")){

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
    public Show Clone(){
        return new Show(this.showId,this.type,this.title,this.director,this.cast,this.country,this.dateAdded,this.releaseYear,this.rating,this.duration,this.listedIn);
    }

    /**
     * Função para transformar String em inteiro
     * 
     * @param str String a ser transformada
     */
    public int StrToInt(String str){

        // Definir dados
        int len = str.length();
        int data = 0;

        for(int i = 0; i < len; i++){

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
    public void Ler(String in) throws Exception{

        // Definir dados
        String simplify = "";
        boolean flag = true;
        int len = in.length();

        // Varrer String para verificar se está entre aspas
        for(int i = 0; i < len; i++){

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
    public static Show Create(String in) throws Exception{
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
    public static void swap(int i, int j, String[] array){

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
            while (array[i].compareTo(pivo) < 0) i++;
            while (array[j].compareTo(pivo) > 0) j--;
            if (i <= j) {
                swap(i, j, array);
                i++;
                j--;
            }
        }
        // Chamada recursiva
        if (esq < j) quickSort(esq, j, array);
        if (i < dir) quickSort(i, dir, array);
    }
    

    public void Imprimir() {

        System.out.print("=> ");
        System.out.print(showId + " ## ");
        System.out.print(title + " ## ");
        System.out.print(type + " ## ");
        System.out.print(director + " ## ");
        System.out.print("[");
        if(cast != null && cast.length > 0)
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
        if(listedIn != null && listedIn.length > 0)
            System.out.print(String.join(", ", listedIn));
        System.out.print("] ## ");
        System.out.println(); 
    }
}


class NoRN {
    Show show;
    NoRN esq, dir, pai;
    boolean cor; // true = vermelho, false = preto

    public NoRN(Show show) {
        this.show = show;
        this.esq = this.dir = this.pai = null;
        this.cor = true; // novo nó sempre começa vermelho
    }
}

class ArvoreAlvinegra {
    private NoRN raiz;
    public int comparacoes = 0;

    public void inserir(Show show) {
        NoRN novo = new NoRN(show);
        raiz = inserir(raiz, novo, null);
        balancearInsercao(novo);
        raiz.cor = false; // raiz sempre preta
    }

    private NoRN inserir(NoRN no, NoRN novo, NoRN pai) {
        if (no == null) {
            novo.pai = pai;
            return novo;
        }
        comparacoes++;
        int cmp = novo.show.getTitle().compareTo(no.show.getTitle());
        if (cmp < 0) {
            no.esq = inserir(no.esq, novo, no);
        } else if (cmp > 0) {
            no.dir = inserir(no.dir, novo, no);
        }
        // se igual, não insere
        return no;
    }

    private void balancearInsercao(NoRN no) {
        while (no != null && no != raiz && cor(no.pai)) {
            NoRN avo = no.pai.pai;
            if (no.pai == avo.esq) {
                NoRN tio = avo.dir;
                if (cor(tio)) {
                    no.pai.cor = false;
                    tio.cor = false;
                    avo.cor = true;
                    no = avo;
                } else {
                    if (no == no.pai.dir) {
                        no = no.pai;
                        rotacaoEsq(no);
                    }
                    no.pai.cor = false;
                    avo.cor = true;
                    rotacaoDir(avo);
                }
            } else {
                NoRN tio = avo.esq;
                if (cor(tio)) {
                    no.pai.cor = false;
                    tio.cor = false;
                    avo.cor = true;
                    no = avo;
                } else {
                    if (no == no.pai.esq) {
                        no = no.pai;
                        rotacaoDir(no);
                    }
                    no.pai.cor = false;
                    avo.cor = true;
                    rotacaoEsq(avo);
                }
            }
        }
        raiz.cor = false;
    }

    private boolean cor(NoRN no) {
        return no != null && no.cor;
    }

    private void rotacaoEsq(NoRN no) {
        NoRN dir = no.dir;
        no.dir = dir.esq;
        if (dir.esq != null) dir.esq.pai = no;
        dir.pai = no.pai;
        if (no.pai == null) raiz = dir;
        else if (no == no.pai.esq) no.pai.esq = dir;
        else no.pai.dir = dir;
        dir.esq = no;
        no.pai = dir;
    }

    private void rotacaoDir(NoRN no) {
        NoRN esq = no.esq;
        no.esq = esq.dir;
        if (esq.dir != null) esq.dir.pai = no;
        esq.pai = no.pai;
        if (no.pai == null) raiz = esq;
        else if (no == no.pai.dir) no.pai.dir = esq;
        else no.pai.esq = esq;
        esq.dir = no;
        no.pai = esq;
    }

    public boolean pesquisar(String nome) {
        System.out.print("=>raiz  ");
        return pesquisar(raiz, nome);
    }

    private boolean pesquisar(NoRN no, String nome) {
        if (no == null) {
            System.out.println("NAO");
            return false;
        }
        comparacoes++;
        int cmp = nome.compareTo(no.show.getTitle());
        if (cmp == 0) {
            System.out.println("SIM");
            return true;
        } else if (cmp < 0) {
            if (no.esq == null) {
                System.out.println("NAO");
                return false;
            }
            System.out.print("esq ");
            return pesquisar(no.esq, nome);
        } else {
            if (no.dir == null) {
                System.out.println("NAO");
                return false;
            }
            System.out.print("dir ");
            return pesquisar(no.dir, nome);
        }
    }

}

public class ArvoreAlvineg {
    public static void main(String[] args) throws Exception {
        long inicio = System.currentTimeMillis();
        Show showManager = new Show();
        ArrayList<Show> shows = showManager.csv("/tmp/disneyplus.csv");

        ArvoreAlvinegra arvore = new ArvoreAlvinegra();

        // Inserção dos registros (entrada padrão até "FIM")
        Scanner sc = new Scanner(System.in);
        String linha = sc.nextLine();
        while (!linha.equals("FIM")) {
            for (Show s : shows) {
                if (s != null && s.getShowId().equals(linha)) {
                    arvore.inserir(s);
                    break;
                }
            }
            linha = sc.nextLine();
        }

        // Pesquisa (entrada padrão até "FIM")
        linha = sc.nextLine();
        while (!linha.equals("FIM")) {
            arvore.pesquisar(linha);
            linha = sc.nextLine();
        }

        long fim = System.currentTimeMillis();
        double tempo = (fim - inicio) / 1000.0;

        // Log
        try (PrintWriter log = new PrintWriter("846431_avinegra.txt")) {
            log.printf("846431\t%.3f\t%d\n", tempo, arvore.comparacoes); 
        }
    }
}
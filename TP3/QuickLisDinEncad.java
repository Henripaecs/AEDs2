import java.io.*;
import java.util.*;

class Show {
    String show_id, type, title, director, cast, country, date_added, rating, duration, listed_in;
    int release_year;

    Show(String[] campos) {
        show_id = campo(campos[0]);
        type = campo(campos[1]);
        title = campo(campos[2]);
        director = campo(campos[3]);
        cast = ordenarTokens(campo(campos[4]));
        country = campo(campos[5]);
        date_added = campo(campos[6]);
        release_year = campos[7].isEmpty() ? -1 : Integer.parseInt(campos[7]);
        rating = campo(campos[8]);
        duration = campo(campos[9]);
        listed_in = ordenarTokens(campo(campos[10]));
    }

    private String campo(String valor) {
        return valor == null || valor.isEmpty() ? "NaN" : valor;
    }

    private String ordenarTokens(String campo) {
        if (campo.equals("NaN")) return campo;
        String[] tokens = campo.split(",");
        List<String> lista = new ArrayList<>();
        for (String token : tokens) lista.add(token.strip());
        Collections.sort(lista);
        return String.join(", ", lista);
    }

    void imprimir() {
        String ano = (release_year == -1) ? "NaN" : String.valueOf(release_year);
        System.out.printf("=> %s ## %s ## %s ## %s ## [%s] ## %s ## %s ## %s ## %s ## %s ## [%s] ##\n",
            show_id, title, type, director, cast, country, date_added, ano, rating, duration, listed_in);
    }
}

class Node {
    Show show;
    Node ant, prox;

    Node(Show show) {
        this.show = show;
    }
}

public class QuickLisDinEncad {
    static final int MAX_SHOWS = 1500;
    static List<Show> allShows = new ArrayList<>();
    static Node inicio = null, fim = null;

    public static void main(String[] args) throws Exception {
        lerCSV("/tmp/disneyplus.csv");

        Scanner sc = new Scanner(System.in);
        String entrada;

        while (!(entrada = sc.nextLine().trim()).equals("FIM")) {
            for (Show s : allShows) {
                if (s.show_id.equals(entrada)) {
                    inserirFim(s);
                    break;
                }
            }
        }

        Show[] lista = converterParaArray();
        int comparacoes = 0, movimentacoes = 0;

        long inicioTempo = System.currentTimeMillis();
        quickSort(lista, 0, lista.length - 1, new int[]{comparacoes}, new int[]{movimentacoes});
        long fimTempo = System.currentTimeMillis();

        for (Show s : lista) s.imprimir();

        double tempoExecucao = fimTempo - inicioTempo;
        salvarLog("846431_quicksort3.txt", comparacoesGlobal, movimentacoesGlobal, tempoExecucao);

        if (sc.hasNextLine()) {
            String tituloBusca = sc.nextLine().trim();
            int compBin = 0;
            buscaBinaria(lista, tituloBusca, new int[]{compBin});
        }

        sc.close();
    }

    static void lerCSV(String caminho) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(caminho));
        String linha;
        br.readLine();
        while ((linha = br.readLine()) != null && allShows.size() < MAX_SHOWS) {
            String[] campos = parseCSV(linha);
            if (campos.length >= 11)
                allShows.add(new Show(campos));
        }
        br.close();
    }

    static String[] parseCSV(String linha) {
        List<String> campos = new ArrayList<>();
        StringBuilder sb = new StringBuilder();
        boolean entreAspas = false;

        for (char c : linha.toCharArray()) {
            if (c == '"') entreAspas = !entreAspas;
            else if (c == ',' && !entreAspas) {
                campos.add(sb.toString());
                sb.setLength(0);
            } else sb.append(c);
        }
        campos.add(sb.toString());
        return campos.toArray(new String[0]);
    }

    static void inserirFim(Show show) {
        Node novo = new Node(show);
        if (fim == null) inicio = fim = novo;
        else {
            fim.prox = novo;
            novo.ant = fim;
            fim = novo;
        }
    }

    static Show[] converterParaArray() {
        List<Show> lista = new ArrayList<>();
        for (Node p = inicio; p != null; p = p.prox)
            lista.add(p.show);
        return lista.toArray(new Show[0]);
    }

    static int comparacoesGlobal = 0;
    static int movimentacoesGlobal = 0;

    static void quickSort(Show[] arr, int esq, int dir, int[] comparacoes, int[] movimentacoes) {
        if (esq < dir) {
            Show pivo = arr[dir];
            int i = esq - 1;

            for (int j = esq; j < dir; j++) {
                comparacoes[0]++;
                comparacoesGlobal++;
                if (comparar(arr[j], pivo) <= 0) {
                    i++;
                    trocar(arr, i, j);
                    movimentacoes[0] += 3;
                    movimentacoesGlobal += 3;
                }
            }

            trocar(arr, i + 1, dir);
            movimentacoes[0] += 3;
            movimentacoesGlobal += 3;

            quickSort(arr, esq, i, comparacoes, movimentacoes);
            quickSort(arr, i + 2, dir, comparacoes, movimentacoes);
        }
    }

    static void trocar(Show[] arr, int i, int j) {
        Show tmp = arr[i];
        arr[i] = arr[j];
        arr[j] = tmp;
    }

    static int comparar(Show a, Show b) {
        long da = parseDate(a.date_added);
        long db = parseDate(b.date_added);
        if (da != db) return Long.compare(da, db);
        return a.title.compareToIgnoreCase(b.title);
    }

    static long parseDate(String data) {
        if (data.equals("NaN")) return 0;
        try {
            return new java.text.SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH).parse(data).getTime();
        } catch (Exception e) {
            return 0;
        }
    }

    static int buscaBinaria(Show[] arr, String titulo, int[] comparacoes) {
        int esq = 0, dir = arr.length - 1;
        while (esq <= dir) {
            int meio = (esq + dir) / 2;
            comparacoes[0]++;
            int cmp = arr[meio].title.compareTo(titulo);
            if (cmp == 0) return meio;
            else if (cmp < 0) esq = meio + 1;
            else dir = meio - 1;
        }
        return -1;
    }

    static void salvarLog(String nome, int comparacoes, int movimentacoes, double tempo) {
        try (PrintWriter pw = new PrintWriter(new FileWriter(nome))) {
            pw.printf("846431\t%d\t%d\t%.2f\n", comparacoes, movimentacoes, tempo);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}

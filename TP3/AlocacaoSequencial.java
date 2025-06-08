//package TP3;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class AlocacaoSequencial {

    static class Show implements Cloneable {
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

        public String getTitle() { return title; }

        public Show clone() {
            return new Show(show_id, type, title, director, cast.clone(), country, date_added, release_year, rating, duration, listed_in.clone());
        }

        public void imprimir() {
            SimpleDateFormat sdf = new SimpleDateFormat("MMMM d, yyyy", Locale.ENGLISH);
            String dataStr = (date_added != null) ? sdf.format(date_added) : "NaN";

            String[] castOrdenado = cast.clone();
            Arrays.sort(castOrdenado, String.CASE_INSENSITIVE_ORDER);

            String titleLimpo = title.replaceAll("\"", "");
            for (int i = 0; i < castOrdenado.length; i++) {
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

    static class ListaShow {
        private Show[] array;
        private int n;

        public ListaShow(int capacidade) {
            this.array = new Show[capacidade];
            this.n = 0;
        }

        public void inserirInicio(Show show) throws Exception {
            if (n >= array.length) throw new Exception("Erro: Lista cheia.");
            for (int i = n; i > 0; i--) array[i] = array[i - 1];
            array[0] = show.clone();
            n++;
        }

        public void inserirFim(Show show) throws Exception {
            if (n >= array.length) throw new Exception("Erro: Lista cheia.");
            array[n++] = show.clone();
        }

        public void inserir(Show show, int pos) throws Exception {
            if (n >= array.length || pos < 0 || pos > n) throw new Exception("Erro: Posição inválida.");
            for (int i = n; i > pos; i--) array[i] = array[i - 1];
            array[pos] = show.clone();
            n++;
        }

        public Show removerInicio() throws Exception {
            if (n == 0) throw new Exception("Erro: Lista vazia.");
            Show resp = array[0];
            for (int i = 0; i < n - 1; i++) array[i] = array[i + 1];
            n--;
            return resp;
        }

        public Show removerFim() throws Exception {
            if (n == 0) throw new Exception("Erro: Lista vazia.");
            return array[--n];
        }

        public Show remover(int pos) throws Exception {
            if (n == 0 || pos < 0 || pos >= n) throw new Exception("Erro: Posição inválida.");
            Show resp = array[pos];
            for (int i = pos; i < n - 1; i++) array[i] = array[i + 1];
            n--;
            return resp;
        }

        public void mostrar() {
            for (int i = 0; i < n; i++) {
                array[i].imprimir();
            }
        }
    }

    public static void main(String[] args) throws IOException {
        Map<String, String> dadosCSV = new HashMap<>();
        BufferedReader br = new BufferedReader(new FileReader("/tmp/disneyplus.csv"));
        br.readLine();
        String linha;
        while ((linha = br.readLine()) != null) {
            String id = linha.split(",")[0];
            dadosCSV.put(id, linha);
        }
        br.close();

        Scanner sc = new Scanner(System.in);
        ListaShow lista = new ListaShow(1000);

        String entrada;
        while (!(entrada = sc.nextLine()).equals("FIM")) {
            if (dadosCSV.containsKey(entrada)) {
                Show show = new Show();
                show.ler(dadosCSV.get(entrada));
                try {
                    lista.inserirFim(show);
                } catch (Exception e) {
                    System.out.println("Erro ao inserir: " + e.getMessage());
                }
            } else {
                System.out.println("=> NaN ## NaN ## NaN ## NaN ## [NaN] ## NaN ## NaN ## -1 ## NaN ## NaN ## [NaN] ##");
            }
        }

        int n = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < n; i++) {
            String comando = sc.nextLine();
            String[] partes = comando.split(" ");

            try {
                switch (partes[0]) {
                    case "II": {
                        Show s = new Show();
                        s.ler(dadosCSV.get(partes[1]));
                        lista.inserirInicio(s);
                        break;
                    }
                    case "IF": {
                        Show s = new Show();
                        s.ler(dadosCSV.get(partes[1]));
                        lista.inserirFim(s);
                        break;
                    }
                    case "I*": {
                        int pos = Integer.parseInt(partes[1]);
                        Show s = new Show();
                        s.ler(dadosCSV.get(partes[2]));
                        lista.inserir(s, pos);
                        break;
                    }
                    case "RI": {
                        Show removido = lista.removerInicio();
                        System.out.println("(R) " + removido.getTitle());
                        break;
                    }
                    case "RF": {
                        Show removido = lista.removerFim();
                        System.out.println("(R) " + removido.getTitle());
                        break;
                    }
                    case "R*": {
                        int pos = Integer.parseInt(partes[1]);
                        Show removido = lista.remover(pos);
                        System.out.println("(R) " + removido.getTitle());
                        break;
                    }
                }
            } catch (Exception e) {
                System.out.println("Erro: " + e.getMessage());
            }
        }

        lista.mostrar();
        sc.close();
    }
}

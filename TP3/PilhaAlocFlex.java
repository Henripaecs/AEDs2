import java.io.*;
import java.util.*;

class Show {
    String show_id;
    String type;
    String title;
    String director;
    String cast;
    String country;
    String date_added;
    int release_year;
    String rating;
    String duration;
    String listed_in;

    public Show(String[] campos) {
        show_id = campoValido(campos[0]);
        type = campoValido(campos[1]);
        title = campoValido(campos[2]);
        director = campoValido(campos[3]);
        cast = ordenarTokens(campoValido(campos[4]));
        country = campoValido(campos[5]);
        date_added = campoValido(campos[6]);
        release_year = campos[7].isEmpty() ? 0 : Integer.parseInt(campos[7]);
        rating = campoValido(campos[8]);
        duration = campoValido(campos[9]);
        listed_in = ordenarTokens(campoValido(campos[10]));
    }

    private String campoValido(String s) {
        return s == null || s.isEmpty() ? "NaN" : s;
    }

    private String ordenarTokens(String campo) {
        if (campo.equals("NaN")) return campo;

        String[] tokens = campo.split(",");
        List<String> lista = new ArrayList<>();
        for (String t : tokens) {
            lista.add(t.strip());
        }
        Collections.sort(lista);
        return String.join(", ", lista);
    }

    public void imprimir(int pos) {
        String ano = (release_year == 0) ? "NaN" : String.valueOf(release_year);
        System.out.printf("[%d] => %s ## %s ## %s ## %s ## [%s] ## %s ## %s ## %s ## %s ## %s ## [%s] ##\n",
                pos, show_id, title, type, director, cast, country,
                date_added, ano, rating, duration, listed_in);
    }
}

public class PilhaAlocFlex {
    static final int MAX_SHOWS = 1500;
    static List<Show> todosShows = new ArrayList<>();
    static List<Show> pilha = new ArrayList<>();

    public static void main(String[] args) throws Exception {
        lerCSV("/tmp/disneyplus.csv");

        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()) {
            String linha = sc.nextLine().trim();
            if (linha.equals("FIM")) break;

            for (Show s : todosShows) {
                if (s.show_id.equals(linha)) {
                    empilhar(s);
                    break;
                }
            }
        }

        int comandos = Integer.parseInt(sc.nextLine());
        for (int i = 0; i < comandos; i++) {
            String comando = sc.nextLine();
            if (comando.startsWith("I ")) {
                String id = comando.substring(2).trim();
                for (Show s : todosShows) {
                    if (s.show_id.equals(id)) {
                        empilhar(s);
                        break;
                    }
                }
            } else if (comando.equals("R")) {
                Show removido = desempilhar();
                if (removido != null) {
                    System.out.println("(R) " + removido.title);
                }
            }
        }

        for (int i = pilha.size() - 1; i >= 0; i--) {
            pilha.get(i).imprimir(i);
        }

        sc.close();
    }

    static void empilhar(Show s) {
        if (pilha.size() < MAX_SHOWS) {
            pilha.add(s);
        }
    }

    static Show desempilhar() {
        if (!pilha.isEmpty()) {
            return pilha.remove(pilha.size() - 1);
        }
        return null;
    }

    static void lerCSV(String caminho) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(caminho));
        String linha;

        br.readLine(); 
        while ((linha = br.readLine()) != null && todosShows.size() < MAX_SHOWS) {
            String[] campos = dividirCSV(linha);
            if (campos.length >= 11) {
                todosShows.add(new Show(campos));
            }
        }
        br.close();
    }

    static String[] dividirCSV(String linha) {
        List<String> campos = new ArrayList<>();
        StringBuilder campo = new StringBuilder();
        boolean entreAspas = false;

        for (int i = 0; i < linha.length(); i++) {
            char c = linha.charAt(i);
            if (c == '"') {
                entreAspas = !entreAspas;
            } else if (c == ',' && !entreAspas) {
                campos.add(campo.toString());
                campo.setLength(0);
            } else {
                campo.append(c);
            }
        }
        campos.add(campo.toString());
        return campos.toArray(new String[0]);
    }
}

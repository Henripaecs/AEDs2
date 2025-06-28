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

    public Show() {}

    public Show(String showId, String type, String title, String director, String[] cast, String country,
                String dateAdded, int releaseYear, String rating, String duration, String[] listedIn) {
        this.showId = showId;
        this.type = type;
        this.title = title;
        this.director = director;
        this.cast = cast;
        this.country = country;
        this.dateAdded = dateAdded;
        this.releaseYear = releaseYear;
        this.rating = rating;
        this.duration = duration;
        this.listedIn = listedIn;
    }

    public String getShowId() { return showId; }
    public String getTitle() { return title; }
    public int getReleaseYear() { return releaseYear; }

    public static Show parse(String line) {
        try {
            String[] parts = line.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)");
            if (parts.length < 11) return null;

            String showId = parts[0].replace("\"", "").trim();
            String type = parts[1].replace("\"", "").trim();
            String title = parts[2].replace("\"", "").trim();
            String director = parts[3].replace("\"", "").trim();
            String[] cast = parts[4].replace("\"", "").split(", ?");
            String country = parts[5].replace("\"", "").trim();
            String dateAdded = parts[6].replace("\"", "").trim();
            int releaseYear = Integer.parseInt(parts[7].replace("\"", "").trim());
            String rating = parts[8].replace("\"", "").trim();
            String duration = parts[9].replace("\"", "").trim();
            String[] listedIn = parts[10].replace("\"", "").split(", ?");

            return new Show(showId, type, title, director, cast, country, dateAdded, releaseYear, rating, duration, listedIn);
        } catch (Exception e) {
            return null;
        }
    }

    public static ArrayList<Show> readCSV(String path) throws IOException {
        ArrayList<Show> list = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(path))) {
            br.readLine(); // Skip header
            String line;
            while ((line = br.readLine()) != null) {
                Show show = parse(line);
                if (show != null) list.add(show);
            }
        }
        return list;
    }
}

class NoTitulo {
    String title;
    NoTitulo esq, dir;

    public NoTitulo(String title) {
        this.title = title;
    }
}

class ArvoreTitulo {
    NoTitulo raiz;

    public void inserir(String title) {
        raiz = inserir(raiz, title);
    }

    private NoTitulo inserir(NoTitulo no, String title) {
        if (no == null) return new NoTitulo(title);
        int cmp = title.compareTo(no.title);
        if (cmp < 0) no.esq = inserir(no.esq, title);
        else if (cmp > 0) no.dir = inserir(no.dir, title);
        return no;
    }

    public boolean pesquisar(String title, StringBuilder caminho) {
        return pesquisar(raiz, title, caminho);
    }

    private boolean pesquisar(NoTitulo no, String title, StringBuilder caminho) {
        if (no == null) return false;
        int cmp = title.compareTo(no.title);
        if (cmp == 0) return true;
        else if (cmp < 0) {
            caminho.append("ESQ ");
            return pesquisar(no.esq, title, caminho);
        } else {
            caminho.append("DIR ");
            return pesquisar(no.dir, title, caminho);
        }
    }
}

class NoAno {
    int chave;
    NoAno esq, dir;
    ArvoreTitulo titulos = new ArvoreTitulo();

    public NoAno(int chave) {
        this.chave = chave;
    }
}

class ArvoreAno {
    NoAno raiz;

    public void inserir(Show s) {
        int chave = s.getReleaseYear() % 15;
        raiz = inserir(raiz, chave, s.getTitle());
    }

    private NoAno inserir(NoAno no, int chave, String title) {
        if (no == null) {
            NoAno novo = new NoAno(chave);
            novo.titulos.inserir(title);
            return novo;
        }
        if (chave < no.chave) no.esq = inserir(no.esq, chave, title);
        else if (chave > no.chave) no.dir = inserir(no.dir, chave, title);
        else no.titulos.inserir(title);
        return no;
    }

    public boolean pesquisar(String title, int chave, StringBuilder caminho) {
        return pesquisar(raiz, title, chave, caminho);
    }

    private boolean pesquisar(NoAno no, String title, int chave, StringBuilder caminho) {
        if (no == null) return false;
        if (chave == no.chave) {
            return no.titulos.pesquisar(title, caminho);
        } else if (chave < no.chave) {
            caminho.append("ESQ ");
            return pesquisar(no.esq, title, chave, caminho);
        } else {
            caminho.append("DIR ");
            return pesquisar(no.dir, title, chave, caminho);
        }
    }
}

public class ArvoreDeArvore {
    public static void main(String[] args) throws Exception {
        long inicio = System.currentTimeMillis();
        ArrayList<Show> shows = Show.readCSV("/tmp/disneyplus.csv");
        ArvoreAno arvore = new ArvoreAno();

        Scanner sc = new Scanner(System.in);
        String linha;

        // Insercao
        while (!(linha = sc.nextLine()).equals("FIM")) {
            for (Show s : shows) {
                if (s.getShowId().equals(linha)) {
                    arvore.inserir(s);
                    break;
                }
            }
        }

        // Pesquisa
        while (!(linha = sc.nextLine()).equals("FIM")) {
            Show buscado = null;
            for (Show s : shows) {
                if (s.getTitle().equals(linha)) {
                    buscado = s;
                    break;
                }
            }
            StringBuilder caminho = new StringBuilder("=>raiz ");
            boolean achou = buscado != null && arvore.pesquisar(buscado.getTitle(), buscado.getReleaseYear() % 15, caminho);
            System.out.println(caminho.toString() + (achou ? "SIM" : "NAO"));
        }

        double tempo = (System.currentTimeMillis() - inicio) / 1000.0;
        try (PrintWriter pw = new PrintWriter("846431_arvoreArvore.txt")) {
            pw.printf("846431\t%.3f\t0\n", tempo);
        }
    }
}
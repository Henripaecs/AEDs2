import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class Game implements Cloneable {
    private String appID;
    private String name;
    private Date releaseDate;
    private String estimatedOwners;
    private double price;
    private String[] supportedLanguages;
    private int metacriticScore;
    private double userScore;
    private int achievements;
    private String publisher;
    private String developer;
    private String[] categories;
    private String[] genres;
    private String[] tags;

    public Game() {
        this.appID = "NaN";
        this.name = "NaN";
        this.releaseDate = null;
        this.estimatedOwners = "NaN";
        this.price = -1;
        this.supportedLanguages = new String[0]; // Alterado para vazio
        this.metacriticScore = -1;
        this.userScore = -1;
        this.achievements = -1;
        this.publisher = "NaN";
        this.developer = "NaN";
        this.categories = new String[0]; // Alterado para vazio
        this.genres = new String[0];     // Alterado para vazio
        this.tags = new String[0];       // Alterado para vazio
    }

    // Clone
    public Game clone() {
        return new Game(
            appID, name, releaseDate, estimatedOwners, price,
            supportedLanguages.clone(), metacriticScore, userScore, achievements,
            publisher, developer, categories.clone(), genres.clone(), tags.clone()
        );
    }

    public Game(String appID, String name, Date releaseDate, String estimatedOwners, double price,
                String[] supportedLanguages, int metacriticScore, double userScore, int achievements,
                String publisher, String developer, String[] categories, String[] genres, String[] tags) {
        this.appID = appID;
        this.name = name;
        this.releaseDate = releaseDate;
        this.estimatedOwners = estimatedOwners;
        this.price = price;
        this.supportedLanguages = supportedLanguages;
        this.metacriticScore = metacriticScore;
        this.userScore = userScore;
        this.achievements = achievements;
        this.publisher = publisher;
        this.developer = developer;
        this.categories = categories;
        this.genres = genres;
        this.tags = tags;
    }

    // Ler linha CSV
    public void ler(String linha) {
        String[] campos = new String[14];
        String[] partes = linha.split(",(?=(?:[^\"]*\"[^\"]*\")*[^\"]*$)", -1);

        for (int i = 0; i < campos.length; i++) {
            campos[i] = (i < partes.length && !partes[i].trim().isEmpty()) ? partes[i].trim().replaceAll("^\"|\"$", "") : "NaN";
        }

        SimpleDateFormat sdfOriginal = new SimpleDateFormat("MMM dd, yyyy", Locale.ENGLISH);

        this.appID = campos[0];
        this.name = campos[1];

        try {
            this.releaseDate = campos[2].equals("NaN") ? null : sdfOriginal.parse(campos[2]);
        } catch (ParseException e) {
            this.releaseDate = null;
        }

        this.estimatedOwners = campos[3];
        this.price = campos[4].equals("NaN") ? -1 : Double.parseDouble(campos[4]);
        this.supportedLanguages = campos[5].equals("NaN") ? new String[0] : campos[5] // Alterado
                .replaceAll("\\[|\\]|'", "")
                .split(",\\s*");
        this.metacriticScore = campos[6].equals("NaN") ? -1 : Integer.parseInt(campos[6]);
        this.userScore = campos[7].equals("NaN") ? -1 : Double.parseDouble(campos[7]);
        this.achievements = campos[8].equals("NaN") ? -1 : Integer.parseInt(campos[8]);
        this.publisher = campos[9].replaceAll(",(?![\\s])", ", ");
        this.developer = campos[10].replaceAll(",(?![\\s])", ", ");
        
        // CORREÇÃO APLICADA AQUI
        this.categories = campos[11].equals("NaN") ? new String[0] : Arrays.stream(campos[11].split(",")).map(String::trim).toArray(String[]::new);
        this.genres = campos[12].equals("NaN") ? new String[0] : Arrays.stream(campos[12].split(",")).map(String::trim).toArray(String[]::new);
        this.tags = campos[13].equals("NaN") ? new String[0] : Arrays.stream(campos[13].split(",")).map(String::trim).toArray(String[]::new);
    }

    // Imprimir
    public void imprimir() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String dataStr = (releaseDate != null) ? sdf.format(releaseDate) : "NaN";

        // CORREÇÃO APLICADA AQUI
        String supportedLanguagesStr = "[" + String.join(", ", supportedLanguages) + "]";
        String categoriesStr = "[" + String.join(", ", categories) + "]";
        String genresStr = "[" + String.join(", ", genres) + "]";
        String tagsStr = "[" + String.join(", ", tags) + "]";

        System.out.println(
            "=> " + appID + " ## " + name + " ## " + dataStr + " ## " +
            estimatedOwners + " ## " + price + " ## " + supportedLanguagesStr + " ## " +
            metacriticScore + " ## " + userScore + " ## " + achievements + " ## [" +
            publisher + "] ## [" + developer + "] ## " + categoriesStr + " ## " +
            genresStr + " ## " + tagsStr + " ##"
        );
    }

    public static void main(String[] args) throws IOException {
        Map<String, String> dadosCSV = new HashMap<>();

        BufferedReader br = new BufferedReader(new FileReader("/tmp/games.csv")); //verde
        //BufferedReader br = new BufferedReader(new FileReader("D://Documentos/GitHub/AEDs2/TP2/games.csv")); // local

        br.readLine();//pula o cabecalho
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
                Game game = new Game();
                game.ler(dadosCSV.get(entrada));
                game.imprimir();
            } else {
                System.out.println("=> NaN ## NaN ## NaN ## NaN ## -1 ## [] ## -1 ## -1 ## -1 ## NaN ## NaN ## [] ## [] ## [] ##");
            }
        }

        sc.close();
    }
}
//package Q04;
import java.util.Random;
import java.util.Scanner;

public class AlteracaoRandom {    
    public static String substituirLetraAleatoria(String input, Random gerador) {
        char letra1 = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));
        char letra2 = (char) ('a' + (Math.abs(gerador.nextInt()) % 26));
        
        return input.replace(letra1, letra2);
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Random gerador = new Random();
        gerador.setSeed(4);
        
        while (scanner.hasNextLine()) {
            String linha = scanner.nextLine();
            if (linha.equals("FIM")) {
                break;
            }
            System.out.println(substituirLetraAleatoria(linha, gerador));
        }
        scanner.close();
    }
}

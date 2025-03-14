import java.util.Scanner;
import java.util.HashSet;

public class SubstringLonga{
    public static int maiorSubstring(String texto) {
        int maiorTamanho = 0;
        int esquerda = 0;
        HashSet<Character> conjunto = new HashSet<>();

        for (int direita = 0; direita < texto.length(); direita++) {
            while (conjunto.contains(texto.charAt(direita))) {
                conjunto.remove(texto.charAt(esquerda));
                esquerda++;
            }
            conjunto.add(texto.charAt(direita));
            maiorTamanho = Math.max(maiorTamanho, direita - esquerda + 1);
        }

        return maiorTamanho;
    }

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        do { 
            String palavra = entrada.nextLine();
            if (palavra.equals("FIM")) {
                break;
            }
            System.out.println(maiorSubstring(palavra));
        } while (true);
        entrada.close();
    }
}
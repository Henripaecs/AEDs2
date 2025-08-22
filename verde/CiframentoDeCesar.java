import java.util.Scanner;

public class CiframentoDeCesar {
    public String cifra(String palavra) {        
        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < palavra.length(); i++) {
            char c = palavra.charAt(i);

            c = (char) (c + 3);
            resultado.append(c); 
        }
        return resultado.toString(); 
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        CiframentoDeCesar cifrador = new CiframentoDeCesar(); 

        while (sc.hasNextLine()) {
            String linha = sc.nextLine();
            if (linha.equals("FIM")) break; 
            System.out.println(cifrador.cifra(linha)); 
        }
        sc.close(); 
    }
}

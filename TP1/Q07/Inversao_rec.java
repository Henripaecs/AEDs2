import java.util.Scanner;

public class Inversao_rec {

    public static String inversao(String str){
        if (str.length() <= 1){
            return str;
        }
        return str.charAt(str.length() - 1) + inversao(str.substring(0, str.length() - 1));
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true){
            String palavra = scanner.nextLine();

            if (palavra.equals("FIM")){
                break;
            }
            System.out.println(inversao(palavra));
        }
        scanner.close();
    }
}

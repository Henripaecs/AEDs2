package Q12;
import java.util.Scanner;

public class Senhas {
    public static boolean Senha(String str){
        if (str.length() < 8){
            return false;
        }

        boolean Maiscula = false;
        boolean Minuscula = false;
        boolean Digito = false;
        boolean Numero = false;

        for(char c : str.toCharArray()){
            if (Character.isUpperCase(c)){
                Maiscula = true;
            } else if (Character.isLowerCase(c)){
                Minuscula = true;
            } else if(Character.isDigit(c)){
                Numero = true;
            } else{
                Digito = true;
            }
        }
        return Maiscula && Minuscula && Digito && Numero;
    }
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while(true){
            String senha = scanner.nextLine();

            if(senha.equals("FIM")){
                break;
            }
            if (Senha(senha)) {
                System.out.println("SIM");
            } else {
                System.out.println("NAO");
            }

        }
        scanner.close();
    }
}

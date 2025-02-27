package Q06;
import java.util.Scanner;

public class Ls {

    public static boolean First(String  str){
        for(int i = 0; i < str.length(); i++){
            char c = str.charAt(i);
            if(!(c == 'a' || c == 'A' || c == 'e' || c == 'E' || c == 'i' || c == 'I' || c == 'o' || c == 'O' || c == 'u' || c == 'U')){
                return false;
            }
        }
        return true;
    }
    public static boolean Second(String  str){
        for(int i = 0; i < str.length(); i++){
            char c = str.charAt(i);
            if(c == 'a' || c == 'A' || c == 'e' || c == 'E' || c == 'i' || c == 'I' || c == 'o' || c == 'O' || c == 'u' || c == 'U'){
                return false;
            }
        }
        return true;
    }
    public static boolean Third(String str){
        for(int i = 0; i < str.length(); i++){
            char c = str.charAt(i);
            if(c >= 0 && c <= 9){
                return true;
            }
        }
        return false;
    }
    public static boolean Fourth(String str){
        for(int i = 0; i < str.length(); i++){
            char c = str.charAt(i);
            if(c >= 0.0 && c <= 9.9){
                return true;
            }
        }
        return false;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while(true){
            String entrada = scanner.nextLine();

            if(entrada.equals("FIM")){
                break;
            }
            if(First(entrada)){
                System.out.print("SIM");
            }else{
                System.out.print("NAO");
            }
            if(Second(entrada)){
                System.out.print("SIM");
            }else{
                System.out.print("NAO");
            }
            if(Third(entrada)){
                System.out.print("SIM");
            }else{
                System.out.print("NAO");
            }
            if(Fourth(entrada)){
                System.out.println("SIM");
            }else{
                System.out.println("NAO");
            }

        }
        scanner.close();
    }
}

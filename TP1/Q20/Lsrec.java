package Q20;
import java.util.Scanner;

public class Lsrec {
    public static boolean First(String  str, int index){
        if (index == str.length()){
            return true;
        }

        char c = str.charAt(index);
            
        if(!(c == 'a' || c == 'A' || c == 'e' || c == 'E' || c == 'i' || c == 'I' || c == 'o' || c == 'O' || c == 'u' || c == 'U')){
            return false;
        }
        return First(str, index + 1);
    }
    public static boolean Second(String  str, int index){
        if (index == str.length()){
            return true;
        }

        char c = str.charAt(index);

        if(c == 'a' || c == 'A' || c == 'e' || c == 'E' || c == 'i' || c == 'I' || c == 'o' || c == 'O' || c == 'u' || c == 'U' || Character.isDigit(c)){
            return false;
        }
       
        return Second(str, index + 1);
    }
    public static boolean Third(String str, int index){
        if (index == str.length()){
            return true;
        }

        char c = str.charAt(index);

        if(!Character.isDigit(c)){
            return false;
        }
        return Third(str, index + 1);
    }
    public static boolean Fourth(String str, int index, boolean hasDecimal){
        if(index == str.length()){
            return true;
        }

        char c = str.charAt(index);

        if(c == '.' || c == ','){
            if(hasDecimal){
                return false;
             }
            return Fourth(str, index + 1, true);
        } else if(!Character.isDigit(c)){
            return false;
        }    
        
        return Fourth(str, index + 1, hasDecimal);
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while(true){
            String entrada = scanner.nextLine();

            if(entrada.equals("FIM")){
                break;
            }
            if(First(entrada, 0)){
                System.out.print("SIM ");
            }else{
                System.out.print("NAO ");
            }
            if(Second(entrada, 0)){
                System.out.print("SIM ");
            }else{
                System.out.print("NAO ");
            }
            if(Third(entrada, 0)){
                System.out.print("SIM ");
            }else{
                System.out.print("NAO ");
            }
            if(Fourth(entrada, 0, false)){
                System.out.println("SIM");
            }else{
                System.out.println("NAO");
            }

        }
        scanner.close();
    }
}

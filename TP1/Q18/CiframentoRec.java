package Q18;
import java.util.Scanner;

public class CiframentoRec {
    public static String CiframentoCesar(String str, int aux){
        if (aux == str.length()){
            return "";
        }
        return (char)(str.charAt(aux) + 3) + CiframentoCesar(str, aux + 1);
    }

    public static void main(String[] args) {
        Scanner scannear = new Scanner(System.in);

        while(true){
            String cifra = scannear.nextLine();

            if(cifra.equals("FIM")){
                break;
            }
            System.out.println(CiframentoCesar(cifra, 0));
        }
        scannear.close();
    }
    
}

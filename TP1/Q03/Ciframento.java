package Q03;
import java.util.Scanner;

public class Ciframento {
    public static String CiframentoCesar(String str){
        StringBuilder ciframento = new StringBuilder();
        for(int i = 0; i < str.length(); i++){
            ciframento.append((char)(str.charAt(i) + 3));
        }
        return ciframento.toString();
    }

    public static void main(String[] args) {
        Scanner scannear = new Scanner(System.in);

        while(true){
            String cifra = scannear.nextLine();

            if(cifra.equals("FIM")){
                break;
            }
            System.out.println(CiframentoCesar(cifra));
        }
        scannear.close();
    }
    
}

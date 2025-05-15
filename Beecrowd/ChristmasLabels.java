import java.util.Scanner;

public class ChristmasLabels {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        int n = sc.nextInt();
        sc.nextLine();
        
        String[] lingua = new String[n];
        String[] traducao = new String[n];
        
        for (int i = 0; i < n; i++) {
            lingua[i] = sc.nextLine();
            traducao[i] = sc.nextLine();
        }
        
        int m = sc.nextInt();
        sc.nextLine(); 
        
        for (int i = 0; i < m; i++) {
            String name = sc.nextLine(); 
            String linguacianca = sc.nextLine(); 

            String aux = "";
            for (int j = 0; j < n; j++) {
                if (lingua[j].equals(linguacianca)) {
                    aux = traducao[j];
                    break; 
                }
            }
            
            System.out.println(name);
            System.out.println(aux);
            System.out.println(); 
        }

        sc.close();
    }
}

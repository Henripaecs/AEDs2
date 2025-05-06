import java.util.Scanner;

public class vo {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        final int MAX = 10001;
        int[] pontuacoes = new int[MAX];

        while (true) {
            int N = sc.nextInt();
            int M = sc.nextInt();
            if (N == 0 && M == 0) break;

            for (int i = 0; i < MAX; i++) {
                pontuacoes[i] = 0;
            }

            for (int i = 0; i < N; i++) {
                for (int j = 0; j < M; j++) {
                    int jogador = sc.nextInt();
                    pontuacoes[jogador]++;
                }
            }

            int primeiro = 0;
            for (int i = 1; i < MAX; i++) {
                if (pontuacoes[i] > primeiro) {
                    primeiro = pontuacoes[i];
                }
            }

            int segundo = 0;
            for (int i = 1; i < MAX; i++) {
                if (pontuacoes[i] > segundo && pontuacoes[i] < primeiro) {
                    segundo = pontuacoes[i];
                }
            }

            for (int i = 1; i < MAX; i++) {
                if (pontuacoes[i] == segundo) {
                    System.out.print(i + " ");
                }
            }
            System.out.println();
        }

        sc.close();
    }
}

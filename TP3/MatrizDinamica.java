import java.util.Scanner;

class MatrizDina {
    int[][] elementos;
    int linhas, colunas;

    public MatrizDina(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.elementos = new int[linhas][colunas];
    }

    public void ler(Scanner sc) {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                elementos[i][j] = sc.nextInt();
            }
        }
    }

    public void mostrarDiagonalPrincipal() {
        int limite = Math.min(linhas, colunas);
        for (int i = 0; i < limite; i++) {
            System.out.print(elementos[i][i] + " ");
        }
        System.out.println();
    }

    public void mostrarDiagonalSecundaria() {
        int limite = Math.min(linhas, colunas);
        for (int i = 0; i < limite; i++) {
            System.out.print(elementos[i][colunas - i - 1] + " ");
        }
        System.out.println();
    }

    public MatrizDina soma(MatrizDina m) {
        if (this.linhas != m.linhas || this.colunas != m.colunas) {
            throw new IllegalArgumentException("Dimensões incompatíveis para soma.");
        }
        MatrizDina resultado = new MatrizDina(linhas, colunas);
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                resultado.elementos[i][j] = this.elementos[i][j] + m.elementos[i][j];
            }
        }
        return resultado;
    }

    public MatrizDina multiplicacao(MatrizDina m) {
        if (this.colunas != m.linhas) {
            throw new IllegalArgumentException("Dimensões incompatíveis para multiplicação.");
        }
        MatrizDina resultado = new MatrizDina(this.linhas, m.colunas);
        for (int i = 0; i < this.linhas; i++) {
            for (int j = 0; j < m.colunas; j++) {
                for (int k = 0; k < this.colunas; k++) {
                    resultado.elementos[i][j] += this.elementos[i][k] * m.elementos[k][j];
                }
            }
        }
        return resultado;
    }

    public void mostrar() {
        for (int i = 0; i < linhas; i++) {
            for (int j = 0; j < colunas; j++) {
                System.out.print(elementos[i][j] + " ");
            }
            System.out.println();
        }
    }
}

public class MatrizDinamica {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int casos = sc.nextInt();

        for (int t = 0; t < casos; t++) {
            int l1 = sc.nextInt();
            int c1 = sc.nextInt();
            MatrizDina m1 = new MatrizDina(l1, c1);
            m1.ler(sc);

            int l2 = sc.nextInt();
            int c2 = sc.nextInt();
            MatrizDina m2 = new MatrizDina(l2, c2);
            m2.ler(sc);

            m1.mostrarDiagonalPrincipal();
            m1.mostrarDiagonalSecundaria();

            try {
                MatrizDina soma = m1.soma(m2);
                soma.mostrar();
            } catch (IllegalArgumentException e) {
                System.out.println("Erro na soma: " + e.getMessage());
            }

            try {
                MatrizDina mult = m1.multiplicacao(m2);
                mult.mostrar();
            } catch (IllegalArgumentException e) {
                System.out.println("Erro na multiplicação: " + e.getMessage());
            }
        }

        sc.close();
    }
}

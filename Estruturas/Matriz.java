class Celula{
    public int elemento;
    public Celula inf, sup, esq, dir;
    public int linha, coluna;

    public Celula(){
        this(0, 0, 0);
    }

    public Celula(int elemento){
        this(0, 0, elemento);
    }

    public Celula(int linha, int coluna, int elemento) {
        this.linha = linha;
        this.coluna = coluna;
        this.elemento = elemento;
        sup = inf = esq = dir = null;
    }
}
public class Matriz{
    private Celula inicio;
    private int linha, coluna;

    public Matriz(){
        this(3, 3);
    }

    public Matriz(int linha, int coluna) {
        this.linha = linha;
        this.coluna = coluna;
    
        Celula anterior = null;
        Celula linhaAnterior = null;
    
        for (int i = 0; i < linha; i++) {
            Celula atual = null;
            anterior = null;
            Celula temp = linhaAnterior;
    
            for (int j = 0; j < coluna; j++) {
                Celula nova = new Celula(i, j, 0);
    
                if (j == 0) {
                    atual = nova;
                    if (i == 0) {
                        this.inicio = nova;
                    }
                } else {
                    anterior.dir = nova;
                    nova.esq = anterior;
                }
    
                if (temp != null) {
                    temp.inf = nova;
                    nova.sup = temp;
                    temp = temp.dir;
                }
    
                anterior = nova;
            }
    
            linhaAnterior = atual;
        }
    }
    

    public Matriz somar( Matriz x){
        if (this.linha != x.linha || this.coluna != x.coluna){
            return null;
        }
        Matriz result = new Matriz(linha, coluna);

        Celula linhaA = this.inicio;
        Celula linhaB = x.inicio;
        Celula linhaR = result.inicio;        

        for (int i = 0; i < linha; i++){
            Celula colunaA = linhaA;
            Celula colunaB = linhaB;
            Celula colunaR = linhaR;

            for (int j = 0; j < coluna; j++){
                colunaR.elemento = colunaA.elemento + colunaB.elemento;

                colunaA = colunaA.dir;
                colunaB = colunaB.dir;
                colunaR = colunaR.dir;
            }
            linhaA = linhaA.inf;
            linhaB = linhaB.inf;
            linhaR = linhaR.inf;
        }
        return result;
    }

    public void removermatrizmeio(){
        int colunameio = coluna / 2;
        
        Celula atual = inicio;

        for (int j = 0; j < colunameio; j++){//ir até a  coluna que desejo retirar;
            atual = atual.dir;
        }

        for (int i = 0; i < linha; i++){
            Celula temp = atual;
            if(temp.esq != null) temp.esq.dir = temp.dir;
            if(temp.dir != null) temp.dir.esq = temp.esq;
            
            atual = temp.inf;

            temp.esq = null;
            temp.dir = null;
            temp.sup = null;
            temp.inf = null;
        }
        coluna--;
    }

    public void removermatrizlinha(){
        int linhameio = linha/2;
        Celula atual = inicio;

        for (int i = 0; i < linhameio; i++){
            atual = atual.inf;
        }

        for (int j = 0; j < coluna; j++){
            Celula temp = atual;

            if(temp.sup != null) temp.sup.inf = temp.inf;
            if(temp.inf != null) temp.inf.sup = temp.sup;

            atual = temp.dir;

            temp.esq = null;
            temp.dir = null;
            temp.sup = null;
            temp.inf = null;
        }
        linha--;
    }

    public void diagonalPrinciapl(){
        if (linha != coluna){
            return;
        }
        System.out.println("Diagonal Princiapal ");
        Celula atual = inicio;

        for (int i = 0; i < linha; i++){
            System.out.println("[" + atual.linha + "," + atual.coluna + "] = " + atual.elemento);
            System.out.print(atual.elemento + " ");
            if (atual.dir != null && atual.inf != null)    
                atual = atual.dir.inf;
        }
        System.out.println();
    }

    public void diagonalSeucndaria(){
        if (linha != coluna){
            return;
        }
        System.out.println("Diagonal Secundaria ");
        Celula atual = inicio;

        for (int i = 0; i < coluna - 1; i++){
            atual = atual.dir;
        }
        for (int i = 0; i < linha; i++){
            System.out.println("[" + atual.linha + "," + atual.coluna + "] = " + atual.elemento);
            System.out.print(atual.elemento + " ");
            if (atual.esq != null && atual.inf != null)
                atual = atual.esq.inf;
        }
        System.out.println();
    }

    public void preencherSequencial() {
        Celula linhaAtual = inicio;
        int valor = 1;

        for (int i = 0; i < linha; i++) {
            Celula colunaAtual = linhaAtual;

            for (int j = 0; j < coluna; j++) {
                colunaAtual.elemento = valor++;
                colunaAtual = colunaAtual.dir;
            }

            linhaAtual = linhaAtual.inf;
        }
    }

    public void mostrar() {
        Celula linhaAtual = inicio;

        for (int i = 0; i < linha; i++) {
            Celula colunaAtual = linhaAtual;

            for (int j = 0; j < coluna; j++) {
                System.out.printf("%4d", colunaAtual.elemento);
                colunaAtual = colunaAtual.dir;
            }

            System.out.println();
            linhaAtual = linhaAtual.inf;
        }
    }
    public static void main(String[] args) {
        Matriz m = new Matriz(3, 3);
        Matriz x = new Matriz(3, 3);        
        Matriz r = new Matriz(3,3);
        m.preencherSequencial();
        x.preencherSequencial();
        r.preencherSequencial();
        m.mostrar();
        x.mostrar();
        Matriz soma = m.somar(x);
        System.out.println();
        soma.mostrar();
        m.diagonalPrinciapl();
        m.diagonalSeucndaria();
        System.out.println("Sem a coluna do meio");
        r.removermatrizmeio();;
        r.mostrar();
        System.out.println("Sem a linha do meio");
        r.removermatrizlinha();
        r.mostrar();

    }
}



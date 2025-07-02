public class arvoreTrie {
    void imprimir(String prefixo){
        No atual = raiz;

        for (int i = 0; i < prefixo.length; i++){
            atual = atual.pesquisar(prefixo.charAt(i));
            if(atual = null){
                return false;
            }
        }

        imprimirPalavras(atual, prefixo);
    }

    void imprimirPalavras(No no, String prefixo){
        if(no.fimPalavra){
            System.out.println(prefixo);
        }
        for (Celula i = no.primeiro.prefixo; i != null; i = i.prox){
            imprimirPalavras(i.no, prefixo + i.elemto);
        }
    }
}
    

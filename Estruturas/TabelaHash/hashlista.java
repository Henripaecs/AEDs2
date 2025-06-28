package TabelaHash;

public class hashlista {
    void inserir(int x)throws Exception{
        if(pesquisar(x) == true){
            throw new Exception("Erro ao inserior");
        }else{
            tabela[hash(x)].inserir(x);
        }
        ultimo.prox = new celula;
        ultimo = ultimo.prox;
    }
}

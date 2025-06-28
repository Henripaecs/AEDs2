package TabelaHash;

public class TabelaHash {

    void inserir(int x)throws Exception{
        int i = hash(x);
        if (x == NULO){
            throw new Exception("Erro!");
        } else if (tabela[i] == NULO){
            tabela[i] = x;
        } else if (numReserva < tamReserva){
            tabela[tamTabela + numReserva] = x;
            numReserva++;//o valor incial de numReserva é zero
        }else{
            throw Exception("Erro!");
        }
    }

    int pesquisar(int x){
        int i = hash(x), resp = NULO;

        if(tabela[i] == x){
            resp = i;
        }else if(tabela[i] != NULO){
            for (int i = 0; i < tamReserva; i++){
                if (tabela[tamTabela + i] == x){
                    resp = tamTabela + i;
                    i = tamReserva;
                }
            }
        }
    return resp;
    }
}
Doidona(){//construtor
    Tabela T1[], T3[];
    Lista listaT1;
    AB ABT1, ABT3;
}

public void inserir(int chave){

    int posT1 = hashT1(chave);

    if (T1[posT1] == NULO){
        T1[posT1] = chave;        
    } else {
        int posT2 = hashT2(chave);

        if(posT2 == 0){            
            int posT3 = hashT3(chave);

            if(T3[posT3] == NULO){
                T3[posT3] = chave;
            } else {
                ABT3.inserir(chave);
            }
        } else if (posT2 == 1){
            listaT1.inserir(chave);
        } else if (posT2 == 2){
            ABT1.inserir(chave);
        }
    } 
}

//---------------------------------------------------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------------------------------------------------
//---------------------------------------------------------------------------------------------------------------------------------------------------------------

Doidona(){//construtor
    Tabela T1[], T3[];
    Celula primeiroT1, ultimoT1;
    No raizT1, raizT3;
}

public void inserir(int chave){

    int posT1 = hashT1(chave);

    if (T1[posT1] == NULO){
        T1[posT1] = chave;        
    } else {
        int posT2 = hashT2(chave);

        if(posT2 == 0){            
            int posT3 = hashT3(chave);

            if(T3[posT3] == NULO){
                T3[posT3] = chave;
            } else {
                raizT3.inserirArvore(raizT3, chave);
            }
        } else if (posT2 == 1){
            ultimoT1.prox = new Celula(chave);
            ultimoT1 = ultimoT1.prox;
        } else if (posT2 == 2){
            raizT1.inserirArvore(raizT1, chave);
        }
    } 
}

public No inserirArvore(No i, int chave){
    if (i == null){
        i = new no(chave);
    } else if (chave < i.elemento){
        i.esq = inserirArvore(i.esq, chave);
    } else if (chave > i.elemento){
        i.dir = inserirArvore(i.dir, chave);
    } else {
        throw new Exception("Elemento já inserido!!");
    }
    return i;
}
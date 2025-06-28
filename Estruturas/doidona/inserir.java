public void inserir(int x) throw Exception {
    int pos = h1(x);

    if(t1[pos] == NULO){
        t1[pos] = x;
    } else {
        pos = h2(x);
        if (t2[pos] == NULO){
            t2[pos] = x;
        } else {
            pos = rehash2(x);
            if (t2[pos] == NULO){
                t2[pos] = x;
            } else {
                pos = h3(x);
                if(h3[pos] == 0){
                    t3[pos].inserirArvore(x);
                } else if (h3[pos] == 1){
                    t3[pos].inserirLista(x);
                } else {
                    throw new Exception("Erro!!");
                }
            }
        }
    }
}

public void inserirArvore(int elemento, No i){
    if (i == null){
        i = new No(elemento);
    } else if (elemento < i.elemento){
        i.esq = inserirArvore(elemento, i.esq);
    } else if (elemento > i.elemento){
        i.dir = inserirArvore(elemento, i.dir);
    } else { 
        throw new Exception("Elemento ja existe!!");
    }
    return i;
}

public void inserirLista(int elemento){
    ultimo.prox = new Celula(elemento);
    ultimo = ultimo.prox;
}


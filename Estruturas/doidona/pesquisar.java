    public boolean pesquisar(int x){
        boolean resp = false;
        int pos = h1(x);

        if (t1[pos] == x){
            resp = true;
        } else {
            pos = h2(x);
            if (t2[pos] == x){
                resp = true;
            } else if (t2[pos] != NULO){
                pos = rehash2(x);
                if(t2[pos] == NULO){
                    resp = true;
                } else {
                    pos = h3(x);
                    if (pos == 0){
                        if(t3[pos].arvorePesquisa(x)){
                            resp = true;
                        } 
                    }else if (pos == 1){
                        if(t3[pos].listaPesquisa(x)){
                            resp = true;
                        }
                    }
                }
            }
        }
        return resp;
    }
    public boolean arvorePesquisa(No i, int elemento){
        boolean resp = false;

        if (i == null){
            resp = false;
        } else if (i.elemento == elemento){
            resp = true;
        } else if (i.elemento < elemento){
            resp = arvorePesquisa(i.esq, elemento);
        } else if (i.elemento > elemento){
            resp = arvorePesquisa(i.dir, elemento);
        }
        return resp;
    }

    public boolean listaPesquisa(int elemento){
        boolean resp = false;

        for (Celula i = primeiro; i != null; i=i.prox){
            if (i.elemento == elemento){
                resp = true;
                break;
            }
        }
        return resp;
    }
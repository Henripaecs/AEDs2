public class hash {
    public boolean pesquisar(int x){
        int pos1= t1.hashT1(x);
        CelulaT1 cel = t1.tabela[pos1];

        if (cel == null)return false;
        if (cel.item == x) return true;
        
        T2 t2 = cel.t2;

        if (t2 == null)return false;

        int pos2 = t2.hashT2(x);
        if(t2.tabela[pos2] == x)return true;

        pos2 = t2.rehashT2(x);
        if (t2.tabela[pos2] == x)return true;

        
        T3 t3 = t2.t3;
        if (t3 == null)return false;
        int pos3 = t3.hashT3(x);        

        if (pos3 == 0){
            return buscarArvore(t3.raiz, x);
        }else{
            for (Celula i = t3.primeiro; i != null; i = i.prox){
                if(i.elemento == x)return true;
            }
        }
        return false;
    }

    boolean buscarArvore(No no, int x){
        if (no == null)return false;

        if (no.x == x){
            return true;
        }else if(x < no.x){
            return buscarArvore(no.esq, x);
        }else{
            return buscarArvore(no.dir, x);
        }
    }

    //caso for string a busca

    boolean pesquisar(String placa) {
        int pos1 = hashT1(placa);
        CelulaT1 cel = t1.tabela[pos1];

        if (cel == null) return false;
        if (placa.equals(cel.item)) return true;

        T2 t2 = cel.t2;
        if (t2 == null) return false;

        int pos2 = hashT2(placa);
        if (placa.equals(t2.tabela[pos2])) return true;

        pos2 = rehashT2(placa);
        if (placa.equals(t2.tabela[pos2])) return true;

        int pos3 = hashT3(placa);
        T3 t3 = t2.t3[pos3];
        if (t3 == null) return false;

        if (pos3 == 0) {
            for (Celula i = t3.primeiro; i != null; i = i.prox)
                if (placa.equals(i.elemento)) return true;
        } else {
            return buscarArvore(t3.raiz, placa);
        }

        return false;
    }

    boolean buscarArvore(No raiz, String placa) {
        if (raiz == null) return false;
        int cmp = placa.compareTo(raiz.elemento);
        if (cmp == 0) return true;
        else if (cmp < 0) return buscarArvore(raiz.esq, placa);
        else return buscarArvore(raiz.dir, placa);
    }
}

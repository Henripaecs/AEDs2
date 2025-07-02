public class contarNoTipo4 {
    int cont = 0;
    
    if(i == null){
        return 0;
    }else{
        if(i.dir == cor && i.esq == cor){
            cont++;
        }else if (i.dir == null){
            return contarNoTipo4(i.dir);
        }else{
            return contarNoTipo4(i.esq);
        }
    }
    return cont;
}

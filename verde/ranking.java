public class ranking {
    public class Jogador{
        private String nome;
        private int gols;
        private int partidas;

        public Jogador(String nome, int gols, int partidas){
            this.nome = nome;
            this.gols = gols;
            this.partidas = partidas;
        }

        public double mediaGolsPorPartida(){
            if (partidas == 0) {
                return 0;
            }
            return (double) gols / partidas;
        }

        @Override
        public String toString(){
            return nome + ": " + gols + " gols em " + partidas + " partidas, média: " + String.format("%.2f", mediaGolsPorPartida());
        }
    }

}

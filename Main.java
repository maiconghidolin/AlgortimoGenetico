import java.util.List;
class Main{

    public static void main(String args[]){
        try{

            // Procedimento:
            //  Cria População;
            //  Para cada iteração:
            //      Seleciona dois genes pais;
            //      CrossOver;
            //      Mutação;
            //      Fitness;
            //      Seleção dos melhores/Corte;

            LerArquivo lerArquivo = new LerArquivo();
            Populacao populacao = new Populacao(lerArquivo);
			
			Evolution evolution = new Evolution();
			double fitness[] = new double[populacao.populacaoInicial.size()];
			
			for (int i = 0; i < populacao.populacaoInicial.size(); i++) {
			//	System.out.println("Calculando fitness do indiv. " + i + "...");
				fitness[i] = evolution.fitness(populacao.populacaoInicial.get(i));
				System.out.println("Indiv. " + i + " - fitness: " + fitness[i]);
			}
			
        } catch(Exception ex){
            System.out.println(ex.getCause().getMessage());
        }
    }
}
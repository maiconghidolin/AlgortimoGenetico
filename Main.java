import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

class Main {

    private static final int quantidadeIteracoes = 20;

    public static void main(String args[]){
		
		double valorFitness[];
		
        try {
			
			if (args != null) {
				
				List<String> listArgs = Arrays.asList(args);
				Diretivas.printDebug = listArgs.indexOf("-d") >= 0;
				
			}

            // Procedimento:
            //  Cria População;
            //  Para cada iteração:
            //      Seleciona dois genes pais;
            //      CrossOver;
            //      Mutação;
            //      Fitness;
            //      Seleção dos melhores/Corte;

            LerArquivo lerArquivo = new LerArquivo();
            // cria populacao
            Populacao populacao = new Populacao(lerArquivo);
            ArrayList<Disciplina[]> populacaoAtual, populacaoDescendente;
			
			// processo de evolução da população
            
			Fitness fitness = new Fitness();
			
			// Mutacao mutacao = new Mutacao(populacao.populacaoInicial);
			CrossOver crossOver = new CrossOver();
			
			populacaoAtual = new ArrayList<Disciplina[]>(populacao.populacaoInicial);
			
			for(int i = 0; i < quantidadeIteracoes; i++){
            
				// crossover
			   populacaoDescendente = new ArrayList<Disciplina[]>(populacaoAtual);
			   
			   crossOver.efetuarCruzamento(populacaoDescendente, lerArquivo.semestres);
			   populacaoAtual.addAll(populacaoDescendente);
			   
               //Mutação
               
               //Fitness
			   
			   valorFitness = new double[populacaoAtual.size()];
			   
				for (int j = 0; j < populacaoDescendente.size(); j++) {
					if (Diretivas.printDebug)
						System.out.print("Calculando fitness do individuo " + j + "...");
					valorFitness[j] = fitness.fitness(populacaoDescendente.get(j));
					if (Diretivas.printDebug)
						System.out.println(" valor: " + valorFitness[j]);
                
				}
				
				// ordenar pela fitness
				
				
			   
               //Corte
            }

			
            if (Diretivas.printDebug)
				populacao.imprimeIndividuos();

        }
		catch (Exception ex){
            System.out.println(ex.getCause().getMessage());
        }
    }
}
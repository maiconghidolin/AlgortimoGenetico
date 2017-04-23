import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Collections;

class Main {

    private static final int quantidadeIteracoes = 1;

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
			ArrayList<Individuo> individuosPopulacao;
			Individuo indiv;
			
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
			   
			   individuosPopulacao = new ArrayList<Individuo>();
			   
			   for (int j = 0; j < populacaoAtual.size(); j++) {
					if (Diretivas.printDebug)
						System.out.print("Calculando fitness do individuo " + j + "...");
					
					indiv = new Individuo();
					indiv.disciplinas = populacaoAtual.get(i);
					indiv.fitness = fitness.fitness(indiv.disciplinas);
					if (Diretivas.printDebug)
						System.out.println(" valor: " + indiv.fitness);
					
					individuosPopulacao.add(indiv);
					
				}
				
				// ordenar pela fitness
				
				Collections.sort(individuosPopulacao, new Comparator<Individuo>() {
					@Override
					public int compare(Individuo i1, Individuo i2)
					{
						return i1.fitness >= i2.fitness ? 1 : -1;
					}
				});
				
			    //Corte
				populacaoAtual.clear();
				for (int k = 0; k < 750; k++)
					populacaoAtual.add(individuosPopulacao.get(k).disciplinas);
				
            }
			
//            if (Diretivas.printDebug)
//				populacao.imprimeIndividuos();

        }
		catch (Exception ex){
            System.out.println(ex.getCause().getMessage());
        }
    }
}
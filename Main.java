import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Collections;
import java.net.*;
import java.io.*;

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
			ArrayList<Individuo> individuosPopulacao;
			Individuo indiv;
			
			CrossOver crossOver = new CrossOver();
			Mutacao mutacao = new Mutacao();

			populacaoAtual = new ArrayList<Disciplina[]>(populacao.populacaoInicial);
			
			for(int i = 0; i < quantidadeIteracoes; i++){
				// crossover
				populacaoDescendente = new ArrayList<Disciplina[]>(populacaoAtual);
			   
			   	crossOver.efetuarCruzamento(populacaoDescendente, lerArquivo.semestres);
			   	populacaoAtual.addAll(populacaoDescendente);
			   
               			//Mutação
               			mutacao.Mutar(populacaoAtual);

               			//Fitness
			   	individuosPopulacao = new ArrayList<Individuo>();
			   	for (int j = 0; j < populacaoAtual.size(); j++) {
					if (Diretivas.printDebug)
						System.out.print("Calculando fitness do individuo " + j + "...");
					
					indiv = new Individuo();
					indiv.disciplinas = populacaoAtual.get(i);
					indiv.fitness = fitness.fitness(indiv.disciplinas, false);
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
			
            		//if (Diretivas.printDebug)
				//populacao.imprimeIndividuos();
			
			double valfitness = fitness.fitness(populacaoAtual.get(0), true);
				System.out.println("Fitness " + valfitness);
				
			for(int i = 1; i <= 5; i++){
				int k;
                		if(i == 1) {
                    			k = 0;
					System.out.println("\nSala 101");
					
					
                		} else if(i == 2) {
                    			k = 30;
					System.out.println("\nSala 102");
				
                		} else if(i == 3) {
                    			k = 60;
					System.out.println("\nSala 103");
					
                		} else if(i == 4) {
                    			k = 90;
					System.out.println("\nSala 104");
					
                		} else{
                    			k = 120;
					System.out.println("\nSala 105");
					
                		}

				for(int j = k; j < k + 30; j++){
					if(populacaoAtual.get(0)[j] != null){						
						
						System.out.println("Horario " + (j - k) + ": " + populacaoAtual.get(0)[j].codigo + " - " + populacaoAtual.get(0)[j].professor.nome);	
						
					}else{
						System.out.println("Horario " + (j - k) + ": Nada");
						
					}
							
				}
								
				
			}
        }
		catch (Exception ex){
            ex.printStackTrace(System.out);
        }
    }
}

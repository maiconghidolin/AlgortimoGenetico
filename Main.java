import java.util.List;
class Main{

    public static void main(String args[]){
        try{
            LerArquivo lerArquivo = new LerArquivo();
            Populacao populacao = new Populacao(lerArquivo);
			
			Evolution evolution = new Evolution();
			double fitness[] = new double[populacao.populacaoInicial.size()];
			
			for (int i = 0; i < populacao.populacaoInicial.size(); i++) {
			//	System.out.println("Calculando fitness do indiv. " + i + "...");
				fitness[i] = evolution.fitness(populacao.populacaoInicial.get(i));
				System.out.println("Indiv. " + i + " - fitness: " + fitness[i]);
			}
			
            for(PeriodosDisciplina periodo: lerArquivo.periodosDisciplina){
                System.out.println(periodo.codigoDisciplina + " " + periodo.quantidadePeriodos);
            }
        } catch(Exception ex){
            System.out.println(ex.getCause().getMessage());
        }
    }
}
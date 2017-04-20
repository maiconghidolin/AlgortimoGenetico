import java.util.List;

class Evolution {

	// a fitness é a avaliação da satisfação das preferências dos professores
	
		public double fitness(Disciplina individuo[]) {
	
			double fitness = 1.0;
			// double penalidadeHorariosConsecutivos = 0.2 / (individuo.length / 2); // 
			// double penalidadeHorarioIndesejado = 0.4 / individuo.length;
			// double penalidadeManhaNoite = 0.4 / (individuo.length * 0.6666667);
			
			double penalidadeHorariosConsecutivos = 0.2 / 30; // 
			double penalidadeHorarioIndesejado = 0.4 / 30;
			double penalidadeManhaNoite = 0.4 / 30;
			
			int horarioAula, i, j, k, stepK;
		
			for (i = 0; i < individuo.length-1; i+=2) {
				if (individuo[i] == null || individuo[i+1] == null)
					continue;
				if (individuo[i].professor.nome == individuo[i+1].professor.nome)
					fitness -= penalidadeHorariosConsecutivos;
			}
			
			for (i = horarioAula = 0; i < individuo.length; i++, horarioAula++) {
				if (individuo[i] == null)
					continue;
				if (horarioAula == 30)
					horarioAula = 0;
				if (individuo[i].professor.horariosIndisponiveis.indexOf(horarioAula) >= 0)
					fitness -= penalidadeHorarioIndesejado;
			}
			
			stepK = 1;
			for (i = 0, j = i + 20; j < 150; i++, j++) {
				if (individuo[i] != null) {
					for (k = 0;  k < 2 && k > -2; k += stepK) {
						if (individuo[j+k] == null)
							continue;
						if (individuo[i].professor.nome == individuo[j+k].professor.nome)
							fitness -= penalidadeManhaNoite;
					}
				}
				stepK = -stepK;
			}
			
			//System.out.println(fitness);
			return fitness;
			
		}
		
		public void crossOver(List<Disciplina[]> populacao) {
			
		}
		
		public void mutacao(Disciplina[] individuo) {
			
		}
	
}
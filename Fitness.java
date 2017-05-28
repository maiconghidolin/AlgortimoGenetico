import java.util.List;
import java.util.ArrayList;
import java.util.Random;

class Fitness {

	// a fitness é a avaliação da satisfação das preferências dos professores
	
	public double fitness(Disciplina individuo[], boolean log) {

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
			if (individuo[i].professor.nome == individuo[i+1].professor.nome){
				fitness -= penalidadeHorariosConsecutivos;
				if(log){
					System.out.println("Preferencia quebrada: periodos consecutivos (" + (i % 30) + ", " + ((i + 1) % 30) + ") no mesmo turno para o professor " + individuo[i].professor.nome + "");
				}
			}
		}
		
		for (i = horarioAula = 0; i < individuo.length; i++, horarioAula++) {
			if (individuo[i] == null)
				continue;
			if (horarioAula == 30)
				horarioAula = 0;
			if (individuo[i].professor.horariosIndisponiveis.indexOf(horarioAula) >= 0){
				fitness -= penalidadeHorarioIndesejado;
				if(log){
					System.out.println("Preferencia quebrada: horario " + horarioAula + " indesejado para o professor " + individuo[i].professor.nome + "");
				}
			}
		}
		
		for (k = 0; k < 5; k++) {
			int offSetSala = (k * 30);
			for (i = offSetSala; i < offSetSala + 10; i++) {
				if (individuo[i] != null) {
					int horarioOutrasSalas = i % 30;
					while(horarioOutrasSalas < 149){

						int offSetSalaNoturno = horarioOutrasSalas + 20 - (i % 2);
						for (j = offSetSalaNoturno; j < offSetSalaNoturno + 2; j++) {
							if (individuo[j] == null)
							continue;
							if (individuo[i].professor.nome == individuo[j].professor.nome){
								fitness -= penalidadeManhaNoite;
								if(log){
									System.out.println("Preferencia quebrada: aulas ministradas no periodo matinal e noturno no mesmo dia (" + i + ", " + (j) + ") para o professor " + individuo[i].professor.nome + "");
								}
							}
						}

						horarioOutrasSalas += 30;
					}
				}
			}
		}

		//System.out.println(fitness);
		return fitness;
		
	}

}
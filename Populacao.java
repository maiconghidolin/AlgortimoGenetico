import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

class Populacao{
    private static final int numeroIndividuos = 750;
    private static final int numeroSalas = 5;
    private static final int numeroHorarios = 30;

    //0 ao 29 - sala 101
    //30 ao 59 - sala 102
    //60 ao 89 - sala 103
    //90 ao 119 - sala 104
    //120 ao 149 - sala 105
    private static final int numeroGenes = numeroSalas * numeroHorarios;
    
    // uma lista de vetores
    // a lista vai ter o tamanho do número de indivíduos
    // cada item da lista é um vetor de tamanho do número de genes
    public List<Disciplina[]> populacaoInicial;

    public Populacao(LerArquivo arquivo){
        populacaoInicial = new ArrayList<Disciplina[]>();
        int i, j, k;
        // passa cada indivíduo criando os genes
        for(i = 0; i < numeroIndividuos; i++){
            Disciplina[] genes = new Disciplina[numeroGenes];
            List<Disciplina> disciplinasAux = new ArrayList<>(arquivo.disciplinas);
            Random rand = new Random();
            while(disciplinasAux.size() > 0){
                Disciplina disciplina;
                int index = rand.nextInt(disciplinasAux.size());
                // pega uma disciplina aleatoria para colocar em um gene
                disciplina = disciplinasAux.get(index);
                // pega o horário correspondente a cada sala
                if(disciplina.semestre.sala == 101) {
                    j = k = 0;
                } else if(disciplina.semestre.sala == 102) {
                    j = k = 30;
                } else if(disciplina.semestre.sala == 103) {
                    j = k = 60;
                } else if(disciplina.semestre.sala == 104) {
                    j = k = 90;
                } else{
                    j = k = 120;
                }
                while(j < (k + 30)){
                    // se já tem um valor no gene não sobrescreve
                    if(genes[j] == null){
                        boolean semestreValido = true, paresHorariosValidos = true, aulaMesmoHorarioOutraSala = false;
                        // verifica se o semestre da disciplina pode ter aula nesse horario
                        if(!disciplina.semestre.horariosDisponiveis.contains(j - k)){
                            semestreValido = false;
                        }
                        // verifica os pares de horarios 
                        // (21,02), (23,04), (25,06), (27,08)
                        // (51,32), (53,34), (55,36), (57,38)
                        // (81,62), (83,64), (85,66), (87,68)
                        // (111,92), (113,94), (115,96), (117,98)
                        // (141,122), (143,124), (145,126), (147,128)
                        List<Integer> pares = Arrays.asList(2, 21, 32, 51, 62, 81, 92, 111, 122, 141);
                        List<Integer> pares2 = Arrays.asList(4, 23, 34, 53, 64, 83, 94, 113, 124, 143);
                        List<Integer> pares3 = Arrays.asList(6, 25, 36, 55, 66, 85, 96, 115, 126, 145);
                        List<Integer> pares4 = Arrays.asList(8, 27, 38, 57, 68, 87, 98, 117, 128, 147);
						
                        if(pares.indexOf(j) > -1){
                            for(int v: pares){
                                if(genes[v] != null){
                                    if(genes[v].professor.nome == disciplina.professor.nome){
                                        paresHorariosValidos = false;
                                    }
                                }
                            }
                        }
                        if(pares2.indexOf(j) > -1){
                            for(int v: pares2){
                                if(genes[v] != null){
                                    if(genes[v].professor.nome == disciplina.professor.nome){
                                        paresHorariosValidos = false;
                                    }
                                }
                            }
                        }
                        if(pares3.indexOf(j) > -1){
                            for(int v: pares3){
                                if(genes[v] != null){
                                    if(genes[v].professor.nome == disciplina.professor.nome){
                                        paresHorariosValidos = false;
                                    }
                                }
                            }
                        }
                        if(pares4.indexOf(j) > -1){
                            for(int v: pares4){
                                if(genes[v] != null){
                                    if(genes[v].professor.nome == disciplina.professor.nome){
                                        paresHorariosValidos = false;
                                    }
                                }
                            }
                        }

                        // valida se o professor ja não está dando aula em outra sala nesse horario
                        int horarioOutrasSalas = j % 30;  
                        while(horarioOutrasSalas < 149){
                            if(genes[horarioOutrasSalas] != null){
                                if(genes[horarioOutrasSalas].professor.nome == disciplina.professor.nome){
                                    aulaMesmoHorarioOutraSala = true;
                                    break;
                                }
                            }
                            horarioOutrasSalas += 30;
                        }                      
						
                        // verifica se pode colocar nesse gene 
                        if(semestreValido && paresHorariosValidos && !aulaMesmoHorarioOutraSala){
                            genes[j] = disciplina;
                            break;
                        }
                        j++;
                    }
					else {
                        j++;
                    }
                }
                // remove da lista auxiliar para não selecionar o mesmo gene duas vezes
                disciplinasAux.remove(index);
            }
            populacaoInicial.add(genes);
        }
    }
	
	public void imprimeIndividuos() {
        Disciplina individuo[];
        for (int i = 0; i < this.populacaoInicial.size(); i++) {
            individuo = this.populacaoInicial.get(i);
            for (int j = 0; j < individuo.length; j++) {
                System.out.print(j + " ");
                System.out.print(individuo[j] == null ? "SEM AULA" : (individuo[j].codigo + " " + individuo[j].professor.nome));
                if (j == 29 || j == 59 || j == 89 || j == 119 || j == 149)
                    System.out.print("\n");
                else 
                    System.out.print(" ");
            }
            System.out.println("\n");
        }
    }
 
    public static int indexOf(Disciplina disciplinas[], Disciplina disciplina) {
        if (disciplinas == null)
            return -1;
        for (int i = 0; i < disciplinas.length; i++)
            if (disciplina.codigo == disciplinas[i].codigo && disciplina.professor.nome == disciplinas[i].professor.nome)
                return i;
        return -1;
    }
    
    public static boolean compararIgualdadeDisciplinas(Disciplina discip1, Disciplina discip2) {
        if ((discip1 == null || discip2 == null) && (discip1 != null || discip2 != null))
            return false;
        return discip1.codigo == discip2.codigo && discip1.professor.nome == discip2.professor.nome;
    }
	
	public static boolean validarIndividuo(Disciplina[] individuo) {
		
		Disciplina disciplina1, disciplina2;
		Utils utils;
		
		utils = new Utils();
		// verifica se o semestre da disciplina pode ter aula nesse horario
		for (int i = 0; i < utils.paresHorariosNaoPermitidos.length - 1; i++) {
			
			disciplina1 = individuo[i];
			disciplina2 = individuo[i+1];
			
			if (disciplina1 != null && disciplina2 != null)
				if (disciplina1.professor.nome == disciplina2.professor.nome)
					return false;
		}

		// valida se o professor não está em duas aulas no mesmo horário
		for (int i = 0; i < 30; i++) {
			disciplina1 = individuo[i];
			for (int j = i + 30; j < 150; j+=30) {
				disciplina2 = individuo[j];
				if (disciplina1 != null && disciplina2 != null)
					if (disciplina1.professor.nome == disciplina2.professor.nome)
						return false;
			}
		}
		
		return true;     

	}
	
	public static boolean validarParesHorarios(Disciplina individuo[], int indices[]) {
		
		Disciplina disciplina;
		int ixHorario, ixHorarioParNaoPermitido;
		Utils utils;
		
		if (indices == null)
			return false;
		
		utils = new Utils();
		
		for (int i = 0; i < indices.length - 1; i++) {
			
			int j;
			// se o horário está nos pares e é impar, verifica com o posterior
			// caso contrário, verifica com o anterior
			ixHorario = indices[i];
			disciplina = individuo[ixHorario];
			
			if (disciplina != null) {
				
				ixHorarioParNaoPermitido = utils.indexOfHorarioParNaoPermitido(ixHorario);
				
				if (ixHorarioParNaoPermitido >= 0) {
					j = ixHorario % 2 == 0 ? ixHorarioParNaoPermitido-1 : ixHorarioParNaoPermitido+1;
					if (individuo[j] == null)
						continue;
					if (disciplina.professor.nome == individuo[j].professor.nome)
						return false;
				}
			}

		}
		
		return true;
		
	}
	
	public static boolean validarAulasProfessor(Disciplina individuo[], int indices[]) {
		
		Disciplina disciplina;
		int ixHorario;
		
		if (indices == null)
			return false;

		for (int i = 0; i < indices.length-1; i++) {
			
			ixHorario = indices[i];
			disciplina = individuo[ixHorario];
			
			for (int j = ixHorario  % 30; j < 150; j+=30) {
			
				if (j != ixHorario && disciplina != null && individuo[j] != null)
					if (disciplina.professor.nome == individuo[j].professor.nome)
						return false;

			}
					
		}
		return true;
		
	}
	
}
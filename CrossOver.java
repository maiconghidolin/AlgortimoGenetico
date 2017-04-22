import java.util.List;
import java.util.ArrayList;
import java.util.Random;

class CrossOver {
		
	public void efetuarCruzamento(List<Disciplina[]> populacao, List<Semestre> semestres) {
		
		// o cross-over deve ocorrer em cada conjunto de genes correspondente ao semestre/sala
		// cruzar quantidade de horários do semestre / 4 genes aleatórios e encontrar uma troca simétrica para manter o número de período das disciplinas/professor
		// iterando nos semestres e fazendo as trocas em cada par de indivíduos
		// validar se os genes trocados não são os mesmos?
		
		Random rand = new Random();
		
		int ixGeneTroca1Indiv1, ixGeneTroca1Indiv2, ixGeneTroca2Indiv1, ixGeneTroca2Indiv2, tentativas, offSetIndividuo, ixGeneOffSet, trocas, ixHorTroca1Indiv1, ixHorTroca2Indiv1, ixHorTroca1Indiv2, ixHorTroca2Indiv2, trocasDesejadas;
		Disciplina geneTroca1Indiv1, geneTroca1Indiv2, geneTroca2Indiv1, geneTroca2Indiv2, individuo1[], individuo2[];
		ArrayList<Integer> horariosTrocaIndiv1, horariosTrocaIndiv2, horariosNulos; // lista dos horários ainda não cruzados (trocados) do indivíduo

		try {
		
			for (int i = 0; i < populacao.size()-1; i++) {
				
				individuo1 = populacao.get(i);
				individuo2 = populacao.get(i+1);
				
				for (Semestre semestre : semestres) {
				
					offSetIndividuo = (semestre.sala - 101) * 30; // deslocamento dentro do cojunto de genes, de acordo com a sala do semestre		
					tentativas = trocas = 0;
					horariosTrocaIndiv1 = new ArrayList<Integer>(semestre.horariosDisponiveis);
					horariosTrocaIndiv2 = new ArrayList<Integer>(semestre.horariosDisponiveis);
					
					horariosNulos = new ArrayList<Integer>();
					for (int h : horariosTrocaIndiv1)
						if (individuo1[offSetIndividuo + h] == null)
							horariosNulos.add(h);
					for (int hn : horariosNulos)
						horariosTrocaIndiv1.remove(horariosTrocaIndiv1.indexOf(hn));
					
					horariosNulos = new ArrayList<Integer>();
					for (int h : horariosTrocaIndiv2)
						if (individuo2[offSetIndividuo + h] == null)
							horariosNulos.add(h);
					for (int hn : horariosNulos)
						horariosTrocaIndiv2.remove(horariosTrocaIndiv2.indexOf(hn));
					
					trocasDesejadas = (horariosTrocaIndiv1.size() < horariosTrocaIndiv2.size() ? horariosTrocaIndiv1.size() : horariosTrocaIndiv2.size()) / 2;
					
					while (tentativas++ < 100 && horariosTrocaIndiv1.size() > trocasDesejadas && horariosTrocaIndiv2.size() > trocasDesejadas) {
						
						// só inicialização pro Java n ratiar
						ixGeneTroca1Indiv1 = ixGeneTroca1Indiv2 = ixGeneTroca2Indiv1 = ixGeneTroca2Indiv2 = -1;
						ixHorTroca1Indiv1 = ixHorTroca1Indiv2 = ixHorTroca2Indiv1 = ixHorTroca2Indiv2 = 0;
						geneTroca1Indiv1 = geneTroca1Indiv2 = geneTroca2Indiv1 = geneTroca2Indiv2 = null;
						
						// índice do gene candidato a cruzamento no indivíduo
						ixHorTroca1Indiv1 = rand.nextInt(horariosTrocaIndiv1.size());
						ixGeneTroca1Indiv1 = horariosTrocaIndiv1.get(ixHorTroca1Indiv1) + offSetIndividuo;
						geneTroca1Indiv1 = individuo1[ixGeneTroca1Indiv1]; // disciplina do horário candidato no indivíduo 1
						
						while (true) {
							ixHorTroca1Indiv2 = rand.nextInt(horariosTrocaIndiv2.size());
							ixGeneTroca1Indiv2 = horariosTrocaIndiv2.get(ixHorTroca1Indiv2) + offSetIndividuo;
							geneTroca1Indiv2 = individuo2[ixGeneTroca1Indiv2]; // disciplina do horário candidato no indivíduo 2
							if (Populacao.compararIgualdadeDisciplinas(geneTroca1Indiv2, geneTroca1Indiv1) == false)
								break;
						}
						
						if (Diretivas.printDebug)
							System.out.println(geneTroca1Indiv1.codigoNomeProfessor() + " " + geneTroca1Indiv2.codigoNomeProfessor());
						
						for (int hor : horariosTrocaIndiv1) {
							ixGeneOffSet = hor + offSetIndividuo;
							if (Populacao.compararIgualdadeDisciplinas(individuo1[ixGeneOffSet], geneTroca1Indiv2)) {
								ixGeneTroca2Indiv1 = ixGeneOffSet;
								geneTroca2Indiv1 = individuo1[ixGeneTroca2Indiv1];
								ixHorTroca2Indiv1 = horariosTrocaIndiv1.indexOf(hor);
								break;
							}
						}

						for (int hor : horariosTrocaIndiv2) {
							ixGeneOffSet = hor + offSetIndividuo;
							if (Populacao.compararIgualdadeDisciplinas(individuo2[ixGeneOffSet], geneTroca1Indiv1)) {
								ixGeneTroca2Indiv2 = ixGeneOffSet;
								geneTroca2Indiv2 = individuo2[ixGeneTroca2Indiv2];
								ixHorTroca2Indiv2 = horariosTrocaIndiv2.indexOf(hor);
								break;
							}
						}
						
						if (ixGeneTroca1Indiv1 >=0 && ixGeneTroca1Indiv2 >= 0 && ixGeneTroca2Indiv1 >= 0 && ixGeneTroca2Indiv2 >= 0) {
							
							individuo1[ixGeneTroca1Indiv1] = geneTroca1Indiv2;
							individuo2[ixGeneTroca1Indiv2] = geneTroca1Indiv1;
							if (Diretivas.printDebug) {
								System.out.println("Troca 1: " + geneTroca1Indiv1.codigoNomeProfessor() + " / " + geneTroca1Indiv2.codigoNomeProfessor());
								System.out.println(horariosTrocaIndiv1.get(ixHorTroca1Indiv1) + " / " + horariosTrocaIndiv2.get(ixHorTroca1Indiv2)); 
							}
							individuo1[ixGeneTroca2Indiv1] = geneTroca2Indiv2;
							individuo2[ixGeneTroca2Indiv2] = geneTroca2Indiv1;
							if (Diretivas.printDebug) {							
								System.out.println("Troca 2: " + geneTroca2Indiv1.codigoNomeProfessor() + " / " + geneTroca2Indiv2.codigoNomeProfessor());
								System.out.println(horariosTrocaIndiv1.get(ixHorTroca2Indiv1) + " / " + horariosTrocaIndiv2.get(ixHorTroca2Indiv2));
							}
							try {
							
								if (Diretivas.printDebug) {
													
									System.out.println("Horários disponíveis: " + Utils.serializarArrayList(horariosTrocaIndiv1) + " / " + Utils.serializarArrayList(horariosTrocaIndiv2));
									
									System.out.println("Horários para remover: " + ixHorTroca1Indiv1 + " " + ixHorTroca2Indiv1 + " / " + ixHorTroca1Indiv2 + " " + ixHorTroca2Indiv2);
									
									System.out.println("Removendo horários " + horariosTrocaIndiv1.get(ixHorTroca1Indiv1) + " ix - " + ixHorTroca1Indiv1 +
									" e " + horariosTrocaIndiv1.get(ixHorTroca2Indiv1) + " ix - " + ixHorTroca2Indiv1);
									horariosTrocaIndiv1.remove(ixHorTroca1Indiv1);
								}
								
								if (ixHorTroca2Indiv1 > ixHorTroca1Indiv1)
									horariosTrocaIndiv1.remove(ixHorTroca2Indiv1-1);
								else if (ixHorTroca2Indiv1 < ixHorTroca1Indiv1)
									horariosTrocaIndiv1.remove(ixHorTroca2Indiv1);
								
								if (Diretivas.printDebug)	
								System.out.println("Horários removidos\nRemovendo horários " + horariosTrocaIndiv2.get(ixHorTroca1Indiv2) + " ix - " + ixHorTroca1Indiv2 + " e "  + horariosTrocaIndiv2.get(ixHorTroca2Indiv2) + " ix - " + ixHorTroca2Indiv2);
								
								horariosTrocaIndiv2.remove(ixHorTroca1Indiv2);
								if (ixHorTroca2Indiv2 >= ixHorTroca1Indiv2)
									horariosTrocaIndiv2.remove(ixHorTroca2Indiv2-1);
								else if (ixHorTroca2Indiv2 < ixHorTroca1Indiv2)
									horariosTrocaIndiv2.remove(ixHorTroca2Indiv2);
								
								if (Diretivas.printDebug)
									System.out.println("Horários removidos");
								
							}
							catch (Exception ex) {
								ex.printStackTrace(System.out);
							}
							if (Diretivas.printDebug) {
								System.out.println("Horários ainda não trocados: " + Utils.serializarArrayList(horariosTrocaIndiv1));
								System.out.println(Utils.serializarArrayList(horariosTrocaIndiv2));
							}
						}
						
					}
				
				}
				
			}
		}
		catch (Exception ex) {
			ex.printStackTrace(System.out);
		}
	}
			
}
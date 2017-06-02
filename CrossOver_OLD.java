import java.util.List;
import java.util.ArrayList;
import java.util.Random;

class CrossOver_OLD {

	private int ixTr1Ind1 = 0, ixTr1Ind2 = 1, ixTr2Ind1 = 2, ixTr2Ind2 = 3;
		
	public void efetuarCruzamento(List<Disciplina[]> populacao, List<Semestre> semestres) {
		
		// o cross-over ocorre em cada conjunto de genes correspondente ao semestre/sala
		// cruzar quantidade de horários do semestre / 4 genes aleatórios e encontrar uma troca simétrica para manter o número de período das disciplinas/professor
		// iterando nos semestres e fazendo as trocas em cada par de indivíduos
		// validar se os genes trocados não são os mesmos
		
		Random rand = new Random();
		Utils utils = new Utils();
		
		int ixGeneTroca1Indiv1, ixGeneTroca1Indiv2, ixGeneTroca2Indiv1, ixGeneTroca2Indiv2, tentativas, offSetIndividuo, ixGeneOffSet, trocas, ixHorTroca1Indiv1, ixHorTroca2Indiv1, ixHorTroca1Indiv2, ixHorTroca2Indiv2, trocasDesejadas;
		int ixTrocas[];
		Disciplina geneTroca1Indiv1, geneTroca1Indiv2, geneTroca2Indiv1, geneTroca2Indiv2, individuo1[], individuo2[], descendente1[], descendente2[], genesTroca[];
		ArrayList<Integer> horariosTrocaIndiv1, horariosTrocaIndiv2, horariosNulos, individuosParadosNoTempo;
		
		try {
		
			ixTrocas = new int[4];
			genesTroca = new Disciplina[4];
			individuosParadosNoTempo = new ArrayList<Integer>();
		
			for (int i = 0; i < populacao.size()-1; i+=2) {
				
				if (Diretivas.printDebug)
					System.out.println("Cruzamento dos indivíduos " + i + " e " + (i+1) + " de " + populacao.size());
				
				individuo1 = populacao.get(i);
				individuo2 = populacao.get(i+1);
				trocas = 0;
				
				for (Semestre semestre : semestres) {
				
					
					offSetIndividuo = (semestre.sala - 101) * 30; // deslocamento dentro do cojunto de genes, de acordo com a sala do semestre
					horariosTrocaIndiv1 = new ArrayList<Integer>(semestre.horariosDisponiveis);
					horariosTrocaIndiv2 = new ArrayList<Integer>(semestre.horariosDisponiveis);
					
					if (Diretivas.printDebug)
						System.out.println("Semestre " + semestre.codigo + " offset: " + offSetIndividuo);
					
					trocasDesejadas = (horariosTrocaIndiv1.size() < horariosTrocaIndiv2.size() ? horariosTrocaIndiv1.size() : horariosTrocaIndiv2.size()) / 2;
					
					while (horariosTrocaIndiv1.size() >= 2 && horariosTrocaIndiv2.size() >= 2 && trocas < trocasDesejadas) {

						if (Diretivas.printDebug)
							System.out.println("Horários antes da remoção: " + utils.serializarArrayList(horariosTrocaIndiv1) + " / " + utils.serializarArrayList(horariosTrocaIndiv2
						));
							
						if (obterGenesTroca(individuo1, individuo2, ixTrocas, genesTroca, horariosTrocaIndiv1, horariosTrocaIndiv2, offSetIndividuo) == false) {
							if (Diretivas.printDebug)
								System.out.println("Nenhuma troca possível...");
							break;
						}
		
						if (Diretivas.printDebug) {
							System.out.println("Horários após da remoção: " + utils.serializarArrayList(horariosTrocaIndiv1) + " / " + utils.serializarArrayList(horariosTrocaIndiv2));
							System.out.print("Trocas: " + ixTrocas[ixTr1Ind1] + " por " + ixTrocas[ixTr1Ind2] + " e " + ixTrocas[ixTr2Ind1] + " por " + ixTrocas[ixTr2Ind2]);
							System.out.println(" " + genesTroca[ixTr1Ind1].codigoNomeProfessor() + " por " + genesTroca[ixTr1Ind2].codigoNomeProfessor() + " e " + genesTroca[ixTr2Ind1].codigoNomeProfessor() + " por " + genesTroca[ixTr2Ind2].codigoNomeProfessor());
						}

						if (Populacao.validarParesHorarios(individuo1, ixTrocas) == false || Populacao.validarParesHorarios(individuo2, ixTrocas) == false || (Populacao.validarAulasProfessor(individuo1, ixTrocas) == false || Populacao.validarAulasProfessor(individuo1, ixTrocas)))
							break;
							
						individuo1[ixTrocas[ixTr1Ind1]] = genesTroca[ixTr1Ind1];
						individuo2[ixTrocas[ixTr1Ind2]] = genesTroca[ixTr1Ind2];
						individuo1[ixTrocas[ixTr2Ind1]] = genesTroca[ixTr2Ind1];
						individuo2[ixTrocas[ixTr2Ind2]] = genesTroca[ixTr2Ind2];
					
						trocas++;

					}
				}
				// se não houve troca de genes os indivíduos não são válidos, pois serão iguais aos pais
				if (trocas == 0) {
					individuosParadosNoTempo.add(i);
					individuosParadosNoTempo.add(i+1);
				}
					
			}
			
			for (int k = individuosParadosNoTempo.size()-1; k >= 0; k--)
				populacao.remove(individuosParadosNoTempo.get(k));
			
		}
		catch (Exception ex) {
			ex.printStackTrace(System.out);
		}
	}
	
	private boolean obterGenesTroca(Disciplina individuo1[], Disciplina individuo2[], int ixTrocas[], Disciplina[] genesTroca, ArrayList<Integer> horarios1, ArrayList<Integer> horarios2, int offSetIndividuo) {
		
		// os itens dos arrays correspondem a:
		// 0 - troca 1, indivíduo 1
		// 1 - troca 1, indivíduo 2
		// 2 - troca 2, indivíduo 1
		// 3 - troca 2, indivíduo 2
		
		if (horarios1 == null || horarios2 == null)
			return false;
		
		if (horarios1.size() < 2 || horarios2.size() < 2)
			return false;

		int ixHorario;
		
		Utils utils = new Utils();
		
		ixHorario = 0;
		genesTroca[ixTr1Ind1] = null;
		
		// seleciona o primeiro gene não nulo como candidato do indivíduo 1 para a troca 1
		while (genesTroca[ixTr1Ind1] == null && ixHorario < horarios1.size()) {
			ixTrocas[ixTr1Ind1] = horarios1.get(ixHorario) + offSetIndividuo;
			genesTroca[ixTr1Ind1] = individuo1[ixTrocas[ixTr1Ind1]];
			ixHorario++;
		}
				
		if (ixHorario == horarios1.size())
			return false;
		
		if(Diretivas.printDebug)
			System.out.println("G11 " + ixTrocas[ixTr1Ind1] + " " + genesTroca[ixTr1Ind1].codigoNomeProfessor());
		
		// procura, no indivíduo 2, um gene diferente do selecionado no indivíduo 1
		for (ixHorario = 0; ixHorario < horarios2.size(); ixHorario++) {
			ixTrocas[ixTr1Ind2] = horarios2.get(ixHorario) + offSetIndividuo;
			genesTroca[ixTr1Ind2] = individuo2[ixTrocas[ixTr1Ind2]];
			if (genesTroca[ixTr1Ind2] != null && compararDisciplinas(genesTroca[ixTr1Ind2], genesTroca[ixTr1Ind1]) == false)
				break;
		}
		
		if (ixHorario == horarios2.size())
			return false;				
		
		if(Diretivas.printDebug)
			System.out.println("G12 " + ixTrocas[ixTr1Ind2] + " " + genesTroca[ixTr1Ind2].codigoNomeProfessor());
		
		for (ixHorario = 0; ixHorario < horarios1.size(); ixHorario++) {
			ixTrocas[ixTr2Ind1] = horarios1.get(ixHorario) + offSetIndividuo;
			if (ixTrocas[ixTr2Ind1] == ixTrocas[ixTr1Ind1])
				continue;
			// procura, no indivíduo 1, um gene igual ao selecionado para troca 1 no indivíduo 2
			genesTroca[ixTr2Ind1] = individuo1[ixTrocas[ixTr2Ind1]];
			if (genesTroca[ixTr2Ind1] != null && compararDisciplinas(genesTroca[ixTr2Ind1], genesTroca[ixTr1Ind2]))
				break;
		}	
		

		if (ixHorario == horarios1.size())
			return false;
		
		if (Diretivas.printDebug)
			System.out.println("G21 " + ixTrocas[ixTr2Ind1] + " " + genesTroca[ixTr2Ind1].codigoNomeProfessor());
		
		for (ixHorario = 0; ixHorario < horarios2.size(); ixHorario++) {
			ixTrocas[ixTr2Ind2] = horarios2.get(ixHorario) + offSetIndividuo;
			if (ixTrocas[ixTr2Ind2] == ixTrocas[ixTr1Ind2])
				continue;
			// procura, no indivíduo 2, um gene igual ao selecionado para troca 1 no indivíduo 1
			genesTroca[ixTr2Ind2] = individuo2[ixTrocas[ixTr2Ind2]];
			if (genesTroca[ixTr2Ind2] != null && compararDisciplinas(genesTroca[ixTr2Ind2], genesTroca[ixTr1Ind1]))
				break;
		}
		
		if (ixHorario == horarios2.size())
			return false;
		
		if(Diretivas.printDebug)
			System.out.println("G22 " + ixTrocas[ixTr2Ind2] + " " + genesTroca[ixTr2Ind2].codigoNomeProfessor());
		
		horarios1.remove(horarios1.indexOf(ixTrocas[ixTr1Ind1] - offSetIndividuo));
		horarios1.remove(horarios1.indexOf(ixTrocas[ixTr2Ind1] - offSetIndividuo));
		horarios2.remove(horarios2.indexOf(ixTrocas[ixTr1Ind2] - offSetIndividuo));
		horarios2.remove(horarios2.indexOf(ixTrocas[ixTr2Ind2] - offSetIndividuo));
		
		return true;
		
	}
		
	private boolean compararDisciplinas(Disciplina d1, Disciplina d2) {
		if (d1 == null || d2 == null)
			return false;
		if (d1.codigo == d2.codigo && d1.professor.nome == d2.professor.nome)
			return true;
		return false;
	}
		
}
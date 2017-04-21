import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;
class Mutacao {    
    // probabilidade de um individuo ser mutado
    private static final int probabilidadeMutacao = 20;

    // mutação por inversão
    // dois genes são trocados de posição dentro do individuo
    // trocas são feitas dentro dos horarios da sala e semestre 
    public Mutacao(List<Disciplina[]> populacao){
        // rand p escolher o primeiro gene a ser trocado
        Random randGeneA = new Random();
        // rand p escolher o segundo gene a ser trocado
        Random randGeneB = new Random();
        // rand da probabilidade de um individuo ser mutado
        Random randProbabilidade = new Random();
        int i = 0;
        for(Disciplina[] individuo: populacao){
            System.out.print("Mutacao do individuo " + i + "   ");
            i++;
            // verifica a probabilidade de mutação para cada individuo
            int probabilidadeGerada = randProbabilidade.nextInt(100);
            System.out.print("Probabilidade " + probabilidadeGerada + "   ");
            if(probabilidadeGerada < probabilidadeMutacao){
                // muta esse indivíduo
                // busca primeiro gene para mutar
                Disciplina geneA = null;
                int indexGeneA = 0;
                while (geneA == null){
                    indexGeneA = randGeneA.nextInt(individuo.length);
                    geneA = individuo[indexGeneA];
                }
                // pega a lista de horarios possiveis do semestre para trocar
                // só faz troca dentro do semestre
                // não faz sentido trocar com outro horario pois quebra a restrição
                List<Integer> horariosDisponiveisSemestre = new ArrayList<>(geneA.semestre.horariosDisponiveis);           
                // verifica a sala do semestre
                int j;
                if(geneA.semestre.sala == 101) {
                    j = 0;
                } else if(geneA.semestre.sala == 102) {
                    j = 30;
                } else if(geneA.semestre.sala == 103) {
                    j = 60;
                } else if(geneA.semestre.sala == 104) {
                    j = 90;
                } else{
                    j = 120;
                }
                // remove o horario do gene A da lista para nao trocar com ele mesmo
                int indexRemover = horariosDisponiveisSemestre.indexOf(indexGeneA - j);
                horariosDisponiveisSemestre.remove(indexRemover);
                // rand nos horarios disponiveis do semestre para achar um gene para trocar
                // não importa se esse gene é null, importa só se está na lista do semestre
                int indexHorarioDisponivel = randGeneB.nextInt(horariosDisponiveisSemestre.size());
                int horarioDisponivel = horariosDisponiveisSemestre.get(indexHorarioDisponivel);
                // horario disponivel varia de 0 a 29
                // j é o offset da sala
                // por exemplo o horario 06 ta sala 103 é o indice 66
                int indexGeneB = horarioDisponivel + j;
                Disciplina geneB = individuo[indexGeneB];
                
                // valida se a troca é valida
                if(ValidaTroca(individuo, geneA, indexGeneB) && ValidaTroca(individuo, geneB, indexGeneA)){
                    // faz a troca
                    System.out.print("Fez a troca do gene " + indexGeneA + " pelo " + indexGeneB);
                    individuo[indexGeneA] = geneB;
                    individuo[indexGeneB] = geneA;
                }else{
                    System.out.print("Mutacao invalida");
                }
            }
            System.out.println("");
        }
    }

    // verifica se pode por a disciplina no indice passado
    private boolean ValidaTroca(Disciplina[] individuo, Disciplina disciplina, int index){
        if(disciplina != null){
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
            if(pares.indexOf(index) > -1){
                for(int v: pares){
                    if(individuo[v] != null){
                        if(individuo[v].professor.nome == disciplina.professor.nome){
                            return false;
                        }
                    }
                }
            }
            if(pares2.indexOf(index) > -1){
                for(int v: pares2){
                    if(individuo[v] != null){
                        if(individuo[v].professor.nome == disciplina.professor.nome){
                            return false;
                        }
                    }
                }
            }
            if(pares3.indexOf(index) > -1){
                for(int v: pares3){
                    if(individuo[v] != null){
                        if(individuo[v].professor.nome == disciplina.professor.nome){
                            return false;
                        }
                    }
                }
            }
            if(pares4.indexOf(index) > -1){
                for(int v: pares4){
                    if(individuo[v] != null){
                        if(individuo[v].professor.nome == disciplina.professor.nome){
                            return false;
                        }
                    }
                }
            }
            // valida se o professor ja não está dando aula em outra sala nesse horario
            int horarioOutrasSalas = index % 30;  
            while(horarioOutrasSalas < 149){
                if(individuo[horarioOutrasSalas] != null){
                    if(individuo[horarioOutrasSalas].professor.nome == disciplina.professor.nome){
                        return false;
                    }
                }
                horarioOutrasSalas += 30;
            }
        }     
        return true;
    }
}
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
        for(i = 0; i < numeroIndividuos; i++){
            Disciplina[] genes = new Disciplina[numeroGenes];
            List<Disciplina> disciplinasAux = new ArrayList<>(arquivo.disciplinas);
            Random rand = new Random();
            while(disciplinasAux.size() > 0){
                Disciplina disciplina;
                int index = rand.nextInt(disciplinasAux.size());
                disciplina = disciplinasAux.get(index);
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
                        // verifica se o profesor quer dar aula nesse horario
                        // essa vai na fitness
                        // if(disciplina.professor.horariosIndisponiveis.contains(j - k)){
                        //     professorValido = false;
                        // }
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

                        if(semestreValido && paresHorariosValidos && !aulaMesmoHorarioOutraSala){
                            genes[j] = disciplina;
                            break;
                        }
                        j++;
                    }else{
                        j++;
                    }
                }
                disciplinasAux.remove(index);
            }
            populacaoInicial.add(genes);
        }
    }
}
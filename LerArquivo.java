import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
class LerArquivo {
    public int quantidadeProfessores;
    public int quantidadeSemestres;
    public int quantidadeDisciplinas;
    public List<Professor> professores;
    public List<Semestre> semestres;
    public List<Disciplina> disciplinas;
    public List<PeriodosDisciplina> periodosDisciplina;

    private static final String fileName = "curso.dat";

    public LerArquivo() throws Exception{
        try{
            professores = new ArrayList<Professor>();
            semestres = new ArrayList<Semestre>();
            disciplinas = new ArrayList<Disciplina>();
            periodosDisciplina = new ArrayList<PeriodosDisciplina>();

            int i = 0, j = 0;
            Scanner scanner = new Scanner(new File(fileName));

            quantidadeProfessores = scanner.nextInt();
            for(i = 0; i < quantidadeProfessores; i++){
                Professor professor = new Professor();
                professor.nome = scanner.next();
                professor.numeroHorariosIndisponiveis = scanner.nextInt();
                professor.horariosIndisponiveis = new ArrayList<Integer>();
                for(j = 0; j < professor.numeroHorariosIndisponiveis; j++){
                    professor.horariosIndisponiveis.add(scanner.nextInt());
                }
                professores.add(professor);
            }

            quantidadeSemestres = scanner.nextInt();
            for(i = 0; i < quantidadeSemestres; i++){
                Semestre semestre = new Semestre();
                semestre.codigo = scanner.next();
                semestre.sala = scanner.nextInt();
                semestre.numeroHorariosDisponiveis = scanner.nextInt();
                semestre.horariosDisponiveis = new ArrayList<Integer>();
                for(j = 0; j < semestre.numeroHorariosDisponiveis; j++){
                    semestre.horariosDisponiveis.add(scanner.nextInt());
                }
                semestres.add(semestre);
            }     

            quantidadeDisciplinas = scanner.nextInt();
            for(i = 0; i < quantidadeDisciplinas; i++){
                String codigoDisciplina = scanner.next();
                int numeroPeriodos = scanner.nextInt();
                String codigoSemestre = scanner.next();
                String nomeProfessor = scanner.next();
                for(j = 0; j < numeroPeriodos; j++){
                    Disciplina disciplina = new Disciplina();
                    disciplina.codigo = codigoDisciplina;
                    Semestre semestre = semestres.stream().filter(x -> x.codigo.equals(codigoSemestre)).findFirst().get();
                    Professor professor = professores.stream().filter(x -> x.nome.equals(nomeProfessor)).findFirst().get();
                    disciplina.semestre = semestre;
                    disciplina.professor = professor;
                    disciplinas.add(disciplina);
                }

                PeriodosDisciplina periodos = periodosDisciplina.stream().filter(x -> x.codigoDisciplina.equals(codigoDisciplina)).findFirst().orElse(null);
                if(periodos != null){
                    periodos.quantidadePeriodos += numeroPeriodos;
                }else{
                    periodos = new PeriodosDisciplina();
                    periodos.codigoDisciplina = codigoDisciplina;
                    periodos.quantidadePeriodos = numeroPeriodos;
                    periodosDisciplina.add(periodos);
                }
            }   

        }catch(Exception ex){
            throw ex;
        }
    } 
}
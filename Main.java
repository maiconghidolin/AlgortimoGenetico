class Main{

    public static void main(String args[]){
        try{
            LerArquivo lerArquivo = new LerArquivo();

            System.out.println("Professores:");
            for(Professor professor: lerArquivo.professores){
                System.out.println(professor.nome + " " + professor.numeroHorariosIndisponiveis + " " + professor.horariosIndisponiveis.toString());
            }

            System.out.println("\nSemestres:");            
            for(Semestre semestre: lerArquivo.semestres){
                System.out.println(semestre.codigo + " " + semestre.sala + " " + semestre.numeroHorariosDisponiveis + " " + semestre.horariosDisponiveis.toString());
            }

            System.out.println("\nDisciplinas:");            
            for(Disciplina disciplina: lerArquivo.disciplinas){
                System.out.println(disciplina.codigo + " " + disciplina.semestre.codigo + " " + disciplina.professor.nome);
            }

            Populacao populacao = new Populacao(lerArquivo);

        } catch(Exception ex){
            System.out.println(ex.getCause().getMessage());
        }
    }
}
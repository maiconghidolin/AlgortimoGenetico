import java.util.List;
class Main{

    public static void main(String args[]){
        try{
            LerArquivo lerArquivo = new LerArquivo();
            Populacao populacao = new Populacao(lerArquivo);

            System.out.println("Populacao:");            
            for(Disciplina[] popini: populacao.populacaoInicial){
                System.out.println("\nIndividuo: " + populacao.populacaoInicial.indexOf(popini));  
                for(Disciplina disciplina: popini){
                    if(disciplina != null){
                        System.out.print(disciplina.codigo + ", ");
                    }else{
                        System.out.print(", ");
                    }
                }
                System.out.println();
            }
        } catch(Exception ex){
            System.out.println(ex.getCause().getMessage());
        }
    }
}
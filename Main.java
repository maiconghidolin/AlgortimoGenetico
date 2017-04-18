import java.util.List;
class Main{

    public static void main(String args[]){
        try{
            LerArquivo lerArquivo = new LerArquivo();
            Populacao populacao = new Populacao(lerArquivo);

            for(PeriodosDisciplina periodo: lerArquivo.periodosDisciplina){
                System.out.println(periodo.codigoDisciplina + " " + periodo.quantidadePeriodos);
            }
        } catch(Exception ex){
            System.out.println(ex.getCause().getMessage());
        }
    }
}
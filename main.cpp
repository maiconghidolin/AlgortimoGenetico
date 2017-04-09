#include <stdio.h>
#include <iostream>
#include <fstream> 
#include <string>
#include <list>

using namespace std;

class Professor{
    public:
    std::string nome;
    int numeroHorariosIndisponiveis;
    std::list<int> horariosIndisponiveis;
};

class Semestre{
    public:
    std::string codigo;
    int sala;
    int numeroHorariosDisponiveis;
    std::list<int> horariosDisponiveis;
};

class Disciplina{
    public:
    std::string codigo;
    int numeroPeriodos;
    std::string semestre;
    std::string professor;
};

class LerArquivo{
    public:

    LerArquivo(){
        quantidadeProfessores = 0;
        quantidadeSemestres = 0;
        quantidadeDisciplinas = 0;
        
        FILE *arq;
        if((arq = fopen("curso.dat", "r")) != NULL){   
            int i, j;

            fscanf(arq,"%d\n", &quantidadeProfessores);
            for(i = 0; i < quantidadeProfessores; i++){
                Professor *professor = new Professor();
                fscanf(arq,"%s", professor->nome);
                fscanf(arq,"%d", &professor->numeroHorariosIndisponiveis);
                for(j = 0; j < professor->numeroHorariosIndisponiveis; j++){
                    int lido;
                    fscanf(arq,"%d", &lido);
                    professor->horariosIndisponiveis.push_back(lido);
                }
                professores.push_back(professor);
            }

            fscanf(arq,"%d\n", &quantidadeSemestres);
            for(i = 0; i < quantidadeSemestres; i++){
                Semestre *semestre = new Semestre();
                fscanf(arq,"%s", semestre->codigo);
                fscanf(arq,"%d", &semestre->sala);
                fscanf(arq,"%d", &semestre->numeroHorariosDisponiveis);
                for(j = 0; j < semestre->numeroHorariosDisponiveis; j++){
                    int lido;
                    fscanf(arq,"%d", &lido);
                    semestre->horariosDisponiveis.push_back(lido);
                }
            }     

            fscanf(arq,"%d\n", &quantidadeDisciplinas);
            for(i = 0; i < quantidadeDisciplinas; i++){
                Disciplina *disciplina = new Disciplina();
                fscanf(arq,"%s", disciplina->codigo);
                fscanf(arq,"%d", disciplina->numeroPeriodos);
                fscanf(arq,"%s", disciplina->semestre);
                fscanf(arq,"%s", disciplina->professor);
                disciplinas.push_back(disciplina);
            }   
        }else{
            cout << "Problema com o arquivo!"; 
        }
    }

    int quantidadeProfessores;
    int quantidadeSemestres;
    int quantidadeDisciplinas;
    std::list<Professor*> professores;
    std::list<Semestre*> semestres;
    std::list<Disciplina*> disciplinas;

};

int main(){
	
	LerArquivo *lerArquivo = new LerArquivo();

    

	return 0;	            
}

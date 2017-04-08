#include <stdio.h>
#include <stdlib.h>
#include <iostream>
#include <fstream> 
using namespace std;

//Armazena os professores e seus horários não preferidos
typedef struct{
	char nome_prof[70];
	int qt_horar_ngostam;
	int horar_ngostam[70];
}professor_t;

//Armazena os semestres, sala e horários disponiveis
typedef struct{
	int sala;
	char cod_semestre[10];
	int num_horar;
	int horar_disp[70];
}semestre_t;

//Armazena disciplina
typedef struct{
	char cod_disciplina[10];
	int num_periodos;
	char cod_semestre[10];
	char nome_prof[70];
}disciplina_t;

int main(){
	
	int qt_prof, qt_semestres, qt_disciplina;
	qt_prof=qt_semestres=qt_disciplina=0;
  	int i = 0;
	int k;
   	FILE *arq;
	professor_t *prof=NULL;
	semestre_t *semestre=NULL;
	disciplina_t *disc=NULL;
       	
	
	if((arq = fopen("curso.dat", "r")) == NULL){
       		printf("\nProblema com o arquivo !\n");
		return 0;
	}
       

	//Lê o numero de professores e os horários no qual cada um não gostaria de dar aula
	fscanf(arq,"%d\n", &qt_prof);
	prof=(professor_t*)malloc(sizeof(professor_t)*qt_prof);

	for(i=0; i<qt_prof; i++){
		fscanf(arq,"%s", prof[i].nome_prof);
		printf("\n|%s| ", prof[i].nome_prof);
		fscanf(arq,"%d", &prof[i].qt_horar_ngostam);
		printf("|%d| ", prof[i].qt_horar_ngostam);
		for(k=0; k<prof[i].qt_horar_ngostam; k++){
			fscanf(arq,"%d", &prof[i].horar_ngostam[k]);
			printf(" |%d|", prof[i].horar_ngostam[k]);
		}
		printf("\n");
	}     
	printf("\n\n\n\n");
	
	//Lê o numero de semestres, seus códigos, suas respestivas salas e os horários disponíveis
	fscanf(arq,"%d\n", &qt_semestres);
	semestre=(semestre_t*)malloc(sizeof(semestre_t)*qt_semestres);

	for(i=0; i<qt_semestres; i++){
		fscanf(arq,"%s", semestre[i].cod_semestre);
		printf("\n|%s| ", semestre[i].cod_semestre);
		fscanf(arq,"%d", &semestre[i].sala);
		printf("|%d| ", semestre[i].sala);
		fscanf(arq,"%d", &semestre[i].num_horar);
		printf("|%d| ", semestre[i].num_horar);
		for(k=0; k<semestre[i].num_horar; k++){
			fscanf(arq,"%d", &semestre[i].horar_disp[k]);
			printf(" |%d|", semestre[i].horar_disp[k]);
		}
		printf("\n");
	}     
	printf("\n\n\n\n");

	//Lê o numero de disciplinas, seu código, periodo, código do semestre e nome do professor vinculado
	fscanf(arq,"%d\n", &qt_disciplina);
	disc=(disciplina_t*)malloc(sizeof(disciplina_t)*qt_disciplina);

	for(i=0; i<qt_disciplina; i++){
		fscanf(arq,"%s", disc[i].cod_disciplina);
		printf("\n|%s| ", disc[i].cod_disciplina);
		fscanf(arq,"%d", &disc[i].num_periodos);
		printf("|%d| ", disc[i].num_periodos);
		fscanf(arq,"%s", disc[i].cod_semestre);
		printf("|%s| ", disc[i].cod_semestre);
		fscanf(arq,"%s", disc[i].nome_prof);
		printf(" |%s|", disc[i].nome_prof);
		printf("\n");	
	}     
	printf("\n\n\n\n");

	fclose(arq);
	free(disc);free(semestre);free(prof);
	  
	return 0;	            
}

JFLAGS = -g
JC = javac
.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java

CLASSES = \
	LerArquivo.java \
	Disciplina.java \
	Populacao.java \
	Semestre.java \
	Professor.java \
	Individuo.java \
	Fitness.java \
	CrossOver.java \
	Mutacao.java \
	Diretivas.java \
	Utils.java \
	Main.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) *.class

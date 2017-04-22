class Disciplina{
    public String codigo;
    public Semestre semestre;
    public Professor professor;
	public String codigoNomeProfessor() {
		return this == null ? "" : (this.codigo + (this.professor == null ? "" : this.professor.nome));
	}
};
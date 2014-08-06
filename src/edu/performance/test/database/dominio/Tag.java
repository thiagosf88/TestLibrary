package edu.performance.test.database.dominio;

public class Tag {

	private String descricao;

	public Tag(String descricao) {
		super();
		this.descricao = descricao;
	}

	public String getDescricao() {
		return descricao;
	}

	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}

	public String toString() {
		return descricao;

	}
}

package edu.performance.test.database.dominio;

import java.util.ArrayList;
import java.util.GregorianCalendar;
import java.util.List;

public class Tarefa {
	private Long id;
	private Long idSeries;
	private GregorianCalendar dataPrevista;
	private String horaPrevista;
	private GregorianCalendar dataInsercao;
	private GregorianCalendar completaEm;
	private String repeticao;
	private String name;
	private String prioridade;
	private List<String> tags;
	private String url;
	private ArrayList<Nota> notas;

	private String lista;

	public Tarefa() {
		super();
		this.id = Long.valueOf(0);
		this.idSeries = Long.valueOf(0);
		this.dataPrevista = new GregorianCalendar();
		this.horaPrevista = "09:00";
		this.dataInsercao = new GregorianCalendar();
		this.completaEm = new GregorianCalendar();
		this.repeticao = "every day";
		this.name = "New empty Task ";
		this.prioridade = "N";
		this.tags = new ArrayList<String>();
		this.lista = "";
		this.url = "";
		this.notas = new ArrayList<Nota>();

	}

	public Tarefa(Tarefa t) {
		super();
		this.id = t.id;
		this.idSeries = t.idSeries;
		this.dataPrevista = t.dataPrevista;
		this.horaPrevista = t.horaPrevista;
		this.dataInsercao = t.dataInsercao;
		this.completaEm = t.completaEm;
		this.name = t.name;
		this.prioridade = t.prioridade;
		this.tags = t.tags;
		this.lista = t.lista;
		this.url = t.url;
		this.notas = t.notas;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getIdSeries() {
		return idSeries;
	}

	public void setIdSeries(Long idSeries) {
		this.idSeries = idSeries;
	}

	public GregorianCalendar getDataPrevista() {
		return dataPrevista;
	}

	public void setDataPrevista(GregorianCalendar dataPrevista) {
		this.dataPrevista = dataPrevista;
	}

	public String getHoraPrevista() {
		return horaPrevista;
	}

	public void setHoraPrevista(String horaPrevista) {
		this.horaPrevista = horaPrevista;
	}

	public GregorianCalendar getDataInsercao() {
		return dataInsercao;
	}

	public void setDataInsercao(GregorianCalendar dataInsercao) {
		this.dataInsercao = dataInsercao;
	}

	public GregorianCalendar getCompletaEm() {
		return completaEm;
	}

	public void setCompletaEm(GregorianCalendar completaEm) {
		this.completaEm = completaEm;
	}

	public String getRepeticao() {
		return repeticao;
	}

	public void setRepeticao(String repeticao) {
		this.repeticao = repeticao;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getPrioridade() {
		return prioridade;
	}

	public void setPrioridade(String prioridade) {
		this.prioridade = prioridade;
	}

	public List<String> getTags() {
		return tags;
	}

	public void setTags(List<String> tags) {
		this.tags = tags;
	}

	public String getLista() {
		return lista;
	}

	public void setLista(String lista) {
		this.lista = lista;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public ArrayList<Nota> getNotas() {
		return notas;
	}

	public void setNotas(ArrayList<Nota> notas) {
		this.notas = notas;
	}

	public String toString() {
		/*
		 * return "nome: " + name + "\n" + "id: " + id + "\n" + "idSeries: " +
		 * idSeries + "\n" + "data Prevista: " + dataPrevista + "\n" +
		 * "data Cria��o: " + dataInsercao + "\n" + "completada em: " +
		 * completaEm + "\n" + "prioridade: " + prioridade + "\n" + "tags: " +
		 * tags + "\n" + "pasta: " + pasta + "\n" + "lista: " + lista + "\n" ;
		 */
		return name;

	}
}

package edu.performance.test.database.util;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import edu.performance.test.PerformanceTestActivity;
import edu.performance.test.R;
import edu.performance.test.database.dominio.Lista;
import edu.performance.test.database.dominio.Nota;
import edu.performance.test.database.dominio.Tarefa;

public class LoadData {

	public ArrayList<Tarefa> getTasks(PerformanceTestActivity app) {

		Document d = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			d = dBuilder.parse(app.getResources()
					.openRawResource(R.raw.tarefas));
			// app.getResources().openRawResource("tasks.xml");
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (d == null)
			return null;

		NodeList nodeLst = d.getElementsByTagName("rsp");
		ArrayList<Tarefa> tarefas = new ArrayList<Tarefa>();

		for (int i = 0; i < nodeLst.getLength(); i++) {

			Node node = nodeLst.item(i);
			Element pai = null;
			if (node.getNodeType() == Node.ELEMENT_NODE)
				pai = (Element) node;
			if (pai.getAttribute("stat").equals("ok")) {

				Element tasks = (Element) pai.getFirstChild();

				// tasks.getFirstChild() = list
				int qntListas = tasks.getChildNodes().getLength(), qntTarefasLista = 0, qntTarefasRepeticao = 1;
				Element list = null, tarefaLista = null;

				Tarefa tmp = null, tmpR = null;
				Long idLista = null;
				ArrayList<String> tags = null;
				ArrayList<Nota> notas = null;

				for (int jn = 0; jn < qntListas; jn++) {

					list = (Element) tasks.getChildNodes().item(jn);
					idLista = Long.valueOf(list.getAttribute("id"));
					qntTarefasLista = list.getChildNodes().getLength();

					for (int k = 0; k < qntTarefasLista; k++) {

						tmp = new Tarefa();
						tarefaLista = (Element) list.getChildNodes().item(k);
						tmp.setIdSeries(Long.valueOf(tarefaLista
								.getAttribute("id")));
						tmp.setName(tarefaLista.getAttribute("name"));

						// Pegando dados da URL
						tmp.setUrl(tarefaLista.getAttribute("url"));

						int tm = (tarefaLista).getElementsByTagName("tag")
								.getLength();

						tags = new ArrayList<String>();

						for (int n = 0; n < tm; n++) {

							tags.add(new String((tarefaLista)
									.getElementsByTagName("tag").item(n)
									.getChildNodes().item(0).getNodeValue()));
						}

						// _________________________________________________________
						int tn = (tarefaLista).getElementsByTagName("note")
								.getLength();

						notas = new ArrayList<Nota>();
						Nota nt = null;
						for (int n1 = 0; n1 < tn; n1++) {
							nt = new Nota();

							nt.setId(Long.valueOf(((Element) (tarefaLista
									.getElementsByTagName("note").item(n1)))
									.getAttribute("id")));
							nt.setTitulo(((Element) (tarefaLista
									.getElementsByTagName("note").item(n1)))
									.getAttribute("title"));
							nt.setNota(new String((tarefaLista)
									.getElementsByTagName("note").item(n1)
									.getChildNodes().item(0).getNodeValue()));
							notas.add(nt);

							nt = null;
						}
						tmp.setNotas(notas);

						qntTarefasRepeticao = (tarefaLista)
								.getElementsByTagName("task").getLength();
						if ((tarefaLista).getElementsByTagName("rrule").item(0) != null) {

							tmp.setRepeticao("");
						}

						tmp.setPrioridade(((Element) (tarefaLista)
								.getElementsByTagName("task").item(0))
								.getAttribute("priority"));
						if (((Element) (tarefaLista).getElementsByTagName(
								"task").item(0)).getAttribute("has_due_time")
								.equals("1")) {

							tmp.setHoraPrevista("9:00");
						} else {
							tmp.setHoraPrevista(null);

						}

						tmp.setLista(idLista.toString());

						tmp.setCompletaEm(TrataData
								.trata(((Element) (tarefaLista)
										.getElementsByTagName("task").item(0))
										.getAttribute("completed")));

						tmp.setTags(tags);
						tmpR = new Tarefa(tmp);
						for (int rp = 0; rp < qntTarefasRepeticao; rp++) {

							tmp.setDataPrevista(TrataData
									.trata(((Element) (tarefaLista)
											.getElementsByTagName("task").item(
													rp)).getAttribute("due")));

							tmp.setId(Long.valueOf(((Element) (tarefaLista)
									.getElementsByTagName("task").item(rp))
									.getAttribute("id")));
							tarefas.add(tmp);
							tmp = null;
							tmp = tmpR;
						}

					}

				}

			} else {

				return null;
			}
		}

		return tarefas;

	}

	public ArrayList<Lista> getLists(PerformanceTestActivity app) {

		Document d = null;
		try {
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			d = dBuilder
					.parse(app.getResources().openRawResource(R.raw.listas));
		} catch (ParserConfigurationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SAXException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		if (d == null)
			return null;

		ArrayList<Lista> listas = new ArrayList<Lista>();
		NodeList nodeLst = d.getElementsByTagName("rsp");
		for (int i = 0; i < nodeLst.getLength(); i++) {

			Node node = nodeLst.item(i);
			Element pai = null;
			if (node.getNodeType() == Node.ELEMENT_NODE)
				pai = (Element) node;
			if (pai.getAttribute("stat").equals("ok")) {

				int tm = (pai).getElementsByTagName("list").getLength();

				for (int m = 0; m < tm; m++) {
					listas.add(new Lista(Long.valueOf(((Element) pai
							.getElementsByTagName("list").item(m))
							.getAttribute("id")), ((Element) pai
							.getElementsByTagName("list").item(m))
							.getAttribute("name")));

				}

			} else {

				return null;

			}
		}

		return listas;
	}

}

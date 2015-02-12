package edu.performance.test;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import android.content.Context;
import android.content.Intent;
import edu.performance.test.filerandomoperation.FileRandomOperation;

public class TestsManager {
	
	public static ArrayList<Intent> getTestList(Context appRef){
		ArrayList<Intent> tests = new ArrayList<Intent>();
		Intent aTest = null;
		
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();

		dbf.setNamespaceAware(false);

		DocumentBuilder docBuilder = null;
		try {
			docBuilder = dbf.newDocumentBuilder();
		} catch (ParserConfigurationException e1) {
			
			e1.printStackTrace();
		}

		Document d = null;
		try {
			d = docBuilder.parse(new File(Library.getFileLocation() + "/listadetestes.xml"));
		} catch ( IOException e) {
			
			e.printStackTrace();
			return null;
			
		} catch (SAXException e) {
			
			e.printStackTrace();
			return null;
		}		
		
		NodeList nodeLst = d.getElementsByTagName("testList");
		for (int i = 0; i < nodeLst.getLength(); i++) {

			Node node = nodeLst.item(i);
			Element testList = null, testItem = null;
			if (node.getNodeType() == Node.ELEMENT_NODE)
				testList = (Element) node;
			
			

				int numberOfTestes = (testList).getElementsByTagName("test").getLength();

				for (int m = 0; m < numberOfTestes; m++) {
					testItem = (Element)(testList).getElementsByTagName("test").item(m);
					
					for(int a = 0; a < testItem.getChildNodes().getLength(); a++){
						if(testItem.getChildNodes().item(a).getNodeName().contains("activityName"))
							try {
								aTest = new Intent(appRef, Class.forName(testItem.getChildNodes().item(a).getTextContent()));
							} catch (DOMException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							} catch (ClassNotFoundException e) {
								// TODO Auto-generated catch block
								System.err.println("A classe defina por: " + testItem.getChildNodes().item(a).getTextContent() + " não foi encontrada na biblioteca");
							}
						if(testItem.getChildNodes().item(a).getNodeName().contains("extraBool"))
							aTest.putExtra(((Element)testItem.getChildNodes().item(a)).getAttribute("name"), Boolean.parseBoolean(testItem.getChildNodes().item(a).getTextContent()));
						if(testItem.getChildNodes().item(a).getNodeName().contains("extraInt"))
							aTest.putExtra(((Element)testItem.getChildNodes().item(a)).getAttribute("name"), Integer.parseInt(testItem.getChildNodes().item(a).getTextContent()));
						if(testItem.getChildNodes().item(a).getNodeName().contains("extraDec"))
							aTest.putExtra(((Element)testItem.getChildNodes().item(a)).getAttribute("name"), Double.parseDouble(testItem.getChildNodes().item(a).getTextContent()));
						if(testItem.getChildNodes().item(a).getNodeName().contains("extraStr"))
							aTest.putExtra(((Element)testItem.getChildNodes().item(a)).getAttribute("name"), testItem.getChildNodes().item(a).getTextContent());
						if(testItem.getChildNodes().item(a).getNodeName().contains("extraStrArr"))
							aTest.putExtra(((Element)testItem.getChildNodes().item(a)).getAttribute("name"), testItem.getChildNodes().item(a).getTextContent().split("-;-"));
						if(testItem.getChildNodes().item(a).getNodeName().contains("extraAStrID"))
							aTest.putExtra(((Element)testItem.getChildNodes().item(a)).getAttribute("name"), appRef.getResources().getStringArray(Integer.parseInt(testItem.getChildNodes().item(a).getTextContent())));
						if(testItem.getChildNodes().item(a).getNodeName().contains("extraStrID"))
							aTest.putExtra(((Element)testItem.getChildNodes().item(a)).getAttribute("name"), appRef.getResources().getString(Integer.parseInt(testItem.getChildNodes().item(a).getTextContent())));
						
						
						
					
					}
					System.out.println(aTest);
					System.out.println(aTest.getExtras());
					tests.add(aTest);

				}
			
		
	}
		
		
		return tests;
	}

}

package org.harpeng.serverstatus.utils;

import java.io.FileInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class MyXMLParser {
	private String file;

	public MyXMLParser(String file) {
		this.file = file;
	}

	private Element getRootElement() throws Exception {
		Element rootElement = null;
		try {
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory
					.newInstance();
			DocumentBuilder builder = builderFactory.newDocumentBuilder();
			Document document = builder.parse(new FileInputStream(this.file));
			rootElement = document.getDocumentElement();
		} catch (SAXException e) {
			new Exception(e.getMessage());
		} catch (IOException e) {
			new Exception(e.getMessage());
		} catch (ParserConfigurationException e) {
			new Exception(e.getMessage());
		}
		return rootElement;
	}

	public String getElementValue(String name) {
		Element element;
		String ret;
		try {
			NodeList list = this.getRootElement().getElementsByTagName(name);
			element = (Element) list.item(0);
			ret = element.getTextContent();
		} catch (Exception e) {
			ret = "";
		}
		return ret;
	}
}

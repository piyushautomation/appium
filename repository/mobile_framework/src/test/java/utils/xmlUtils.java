package utils;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Node;

public class xmlUtils {

	public static String getChildNodeValue(String xmlFilePath, String parentNodeName, String childNodeName) {
		String result = null;
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(xmlFilePath);
			XPathFactory factory = XPathFactory.newInstance();
			XPath path = factory.newXPath();
			Node node;
			node = (Node) path.evaluate("//" + parentNodeName + "/" + childNodeName, doc, XPathConstants.NODE);
			result = node.getTextContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	public static String getChildNodeValue(String xmlFilePath, String parentNodeName) {
		String result = null;
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(xmlFilePath);
			XPathFactory factory = XPathFactory.newInstance();
			XPath path = factory.newXPath();
			Node node;
			node = (Node) path.evaluate("//" + parentNodeName , doc, XPathConstants.NODE);
			result = node.getTextContent();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	public static void updateXmlTagValue(String xmlFilePath, String nodeName, String value) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(xmlFilePath);
			// Node data = doc.getFirstChild();
			Node startdate = doc.getElementsByTagName(nodeName).item(0);
			System.out.println(startdate.getTextContent());
			startdate.setTextContent(value);
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(xmlFilePath));
			transformer.transform(source, result);

			System.out.println("Done");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void updateMultipleXmlTagValue(String xmlFilePath, LinkedHashMap<String, String> map) {
		try {
			DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
			Document doc = docBuilder.parse(xmlFilePath);

			// Get the root element
			// Node data = doc.getFirstChild();
			for (Map.Entry<String, String> entry : map.entrySet()) {
				Node startdate = doc.getElementsByTagName(entry.getKey()).item(0);
				System.out.println(startdate.getTextContent());
				startdate.setTextContent(entry.getValue());
			}
			// write the content into xml file
			TransformerFactory transformerFactory = TransformerFactory.newInstance();
			Transformer transformer = transformerFactory.newTransformer();
			DOMSource source = new DOMSource(doc);
			StreamResult result = new StreamResult(new File(xmlFilePath));
			transformer.transform(source, result);

			System.out.println("Done");

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}

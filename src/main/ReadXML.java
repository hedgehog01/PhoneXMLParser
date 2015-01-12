/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 *
 * @author nathanr
 */
public class ReadXML {

    /**
     * Method to print all specific text in tags in the XML
     *
     * @param XMLName the XML to get results from (full XML path)
     * @param mainElement
     * @param tagName the tag to get results from
     */
    public static void getAllXMLTagTextByName(String XMLName, String mainElement, String tagName) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = docFactory.newDocumentBuilder();
            Document doc = builder.parse(XMLName);

            // normalize text representation
            doc.getDocumentElement().normalize();
            System.out.println("Root element of the doc is "
                    + doc.getDocumentElement().getNodeName());

            NodeList nodeList = doc.getElementsByTagName(mainElement);
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    NodeList xmlChilderenNodes = element.getChildNodes();
                    getNodeTextInfo(xmlChilderenNodes, tagName);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /*
     * private method that prints all tag text info from specific tag in all XML
     */
    private static void getNodeTextInfo(NodeList xmlChilderenNodes, String tagName) {
        for (int i = 0; i < xmlChilderenNodes.getLength(); i++) {
            Node n = xmlChilderenNodes.item(i);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Element name = (Element) n;
                if (name.getTagName().equals(tagName)) {
                    String tagText = name.getTextContent();

                    System.out.println(tagText);
                }

            }
        }
    }

    /*
     * private method that check Phone name and Guid name attribute
     */
    private static void getNodeNameInfo(NodeList xmlChilderenNodes) {
        String phoneName = null;
        String phoneGuidName = null;
        for (int j = 0; j < xmlChilderenNodes.getLength(); j++) {
            Node n = xmlChilderenNodes.item(j);
            if (n.getNodeType() == Node.ELEMENT_NODE) {
                Element name = (Element) n;
                if (name.getAttribute("name").isEmpty()) {
                    System.out.println(name.getTagName() + " " + name.getTextContent());
                }
                if (name.getTagName().equals("Guid")) {
                    phoneGuidName = name.getAttribute("name");
                }
                if (name.getTagName().equals("Name")) {
                    phoneName = name.getTextContent();
                }

                if ((phoneName != null) && (phoneGuidName != null) && (phoneName.equals(phoneGuidName) && (j == xmlChilderenNodes.getLength() - 2))) {

                    System.out.println("Guid name and phone name are equal");
                    phoneName = null;
                    phoneGuidName = null;

                }
                //System.out.println(name.getTagName() + " " + name.getTextContent() + " attribute: " + name.getAttribute("name"));

            }
        }
    }

    public static void readXMLByName(String XMLName, String tagName) {
        try {
            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder builder = docFactory.newDocumentBuilder();
            Document doc = builder.parse(XMLName);

            // normalize text representation
            doc.getDocumentElement().normalize();
            System.out.println("Root element of the doc is "
                    + doc.getDocumentElement().getNodeName());

            NodeList nodeList = doc.getElementsByTagName(tagName);
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    NodeList xmlChilderenNodes = element.getChildNodes();
                    getNodeNameInfo(xmlChilderenNodes);
                }
            }
        } catch (ParserConfigurationException | SAXException | IOException ex) {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

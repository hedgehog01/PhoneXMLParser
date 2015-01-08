/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 */
package main;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

/**
 * http://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/
 *
 * @author nathanr
 */
public class WriteXML {

    /**
     * Class to write new XML
     *
     * @param XMLName The name of the file that will be created
     * @param rootElmnt The XML root element
     * @param mainChildElement The main child Element (Phone etc)
     */
    public static void writeXMLByName(String XMLName, String rootElmnt, String mainChildElement) {

        try {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();

            //root element
            Document doc = docBuilder.newDocument();
            Element rootElement = doc.createElement(rootElmnt);
            doc.appendChild(rootElement);

            // main element (phone?)
            Element mainElement = doc.createElement(mainChildElement);
            rootElement.appendChild(mainElement);

            //name element
            Element phoneName = doc.createElement("Name");
            rootElement.appendChild(doc.createTextNode("Phone 1"));

            //write the content into XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(XMLName));

            //output for consule for testing
            StreamResult consultResult = new StreamResult(System.out);

            transformer.transform(source, result);

            System.out.println("File Saved!");

        } catch (ParserConfigurationException | TransformerConfigurationException ex) {
            Logger.getLogger(WriteXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex) {
            Logger.getLogger(WriteXML.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

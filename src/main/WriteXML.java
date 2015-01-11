/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.

 */
package main;

import java.io.File;
import java.io.IOException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

/**
 * http://www.mkyong.com/java/how-to-create-xml-file-in-java-dom/
 *
 * @author nathanr
 */
public class WriteXML
{
    
        private static final String ROOT_ELEMENT = "dataroot";
        private static final String MAIN_ELEMENT = "PHONE";
        private static final String GUID_TAG = "Guid";
        private static final String NAME_TAG = "Name";
        private static final String GUIG_NAME_ATTR = "name";

    private static final Logger LOG = Logger.getLogger(WriteXML.class.getName());

    /**
     * Method to write new XML
     *
     * @param XMLName The name of the file that will be created
     * @param rootElmnt The XML root element
     * @param mainChildElement The main child Element (Phone etc)
     */
    public static void writeXMLByName(String XMLName, String rootElmnt, String mainChildElement)
    {

        try
        {

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
            phoneName.appendChild(doc.createTextNode("Phone 1"));
            mainElement.appendChild(phoneName);

            //Guid Element
            Element phoneGuid = doc.createElement("Guid");
            phoneGuid.appendChild(doc.createTextNode("578e0e0a-b5fc-4052-926a-ab0afd732a26"));
            mainElement.appendChild(phoneGuid);

            //set Guid name attribute
            Attr guidName = doc.createAttribute("name");
            guidName.setValue("Phone 1");
            phoneGuid.setAttributeNode(guidName);

            //write the content into XML file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(XMLName));

            //output for consule for testing
            StreamResult consultResult = new StreamResult(System.out);
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount", "2");
            transformer.transform(source, consultResult);
            transformer.transform(source, result);

            System.out.println("File Saved!");

        } catch (ParserConfigurationException | TransformerConfigurationException ex)
        {
            Logger.getLogger(WriteXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex)
        {
            Logger.getLogger(WriteXML.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    /**
     * method to update existing XML Node element (Tag)
     *
     * @param XMLFilePath the XML file to update path (Full path)
     * @param elementToUpdate the tag name to be updated
     * @param oldElementValue The current Element value
     * @param newElementValue the new Element Value (Empty/null string will not
     * modify the tag)
     * @param newAttribut the new Element attribute (Empty/null string will not
     * modify the tag)
     */
    public static void updateXMLNodeElement(String XMLFilePath, String elementToUpdate, String oldElementValue, String newElementValue, String newAttribut)
    {

        try
        {

            DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
            Document doc = docBuilder.parse(XMLFilePath);
            
            //Remove standalone from XML
            doc.setXmlStandalone(true);

            //Get XML root element list
            //Node dataRoot = doc.getFirstChild();

            //get Phone node
            NodeList phoneNodeList = doc.getElementsByTagName(MAIN_ELEMENT);
            for (int i = 0; i < phoneNodeList.getLength(); i++)
            {

                Node node = phoneNodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE)
                {
                    Element element = (Element) node;

                    NodeList phoneChilderenNodes = element.getChildNodes();
                    //getNodeInfo(phoineChilderenNodes);
                    for (int j = 0; j < phoneChilderenNodes.getLength(); j++)
                    {
                        Node phoneChild = phoneChilderenNodes.item(j);
                        String nodeName = phoneChild.getNodeName();
                        String nodeValue = phoneChild.getTextContent();
                        if ((elementToUpdate.equals(nodeName)) && (oldElementValue.equals(nodeValue)))
                        {
                            LOG.log(Level.INFO, "element to update found");
                            phoneChild.setTextContent(newElementValue);
                        }
                        //if tag to update is the phone name - update name attribute in Guid tag
                        if ((elementToUpdate.equals(NAME_TAG)) && (nodeName.equals(GUID_TAG)))
                        {
                            // update GUID name attribute to be same as phone name
                            NamedNodeMap attr = phoneChild.getAttributes();
                            Node nodeAttr = attr.getNamedItem(GUIG_NAME_ATTR);
                            nodeAttr.setTextContent(newElementValue);
                        }
                    }
                }

            }
            // write the content into xml file
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult consultResult = new StreamResult(System.out);
            if (XMLFilePath.contains(".XML") || (XMLFilePath.contains(".xml")))
            {
                
                 XMLFilePath = XMLFilePath.replaceAll(".xml", "");
                XMLFilePath = XMLFilePath.replaceAll(".XML", "");
                LOG.log(Level.INFO, "XML File path contained .xml and will be removed - new file name: {0}",XMLFilePath);
            }
            //create output folder (if doesn't exist)
            File dir = new File ("output");
            if (!dir.exists())
                dir.mkdir();
            StreamResult result = new StreamResult(new File(dir + "\\" + XMLFilePath + "_" + LocalDate.now() + ".xml"));
            transformer.transform(source, consultResult);
            transformer.transform(source, result);

            System.out.println("Done");

        } catch (ParserConfigurationException | SAXException | IOException ex)
        {
            Logger.getLogger(WriteXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerConfigurationException ex)
        {
            Logger.getLogger(WriteXML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (TransformerException ex)
        {
            Logger.getLogger(WriteXML.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

}

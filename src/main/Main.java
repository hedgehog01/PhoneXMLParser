/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;



/**
 *
 * @author nathanr
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        //ReadXML.readXMLByName("test.xml", "PHONE");
        //WriteXML.writeXMLByName("phonetest.xml", "dataroot", "Phone");
        //WriteXML.updateXMLNodeElement("test.xml", "Name", "ZMKN110Z", "new Name","");
        //ReadXML.getAllXMLTagTextByName("test.xml","PHONE", "Name");
        //ReadXML.getAllXMLTagTextByName("test.xml","PHONE", "Guid");
        ReadXML.getAllNodeElements("test.xml", "PHONE", "ZMKN110Z");
    }
}

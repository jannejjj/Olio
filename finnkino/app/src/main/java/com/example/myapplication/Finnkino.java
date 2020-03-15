package com.example.myapplication;


import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.util.ArrayList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Finnkino {

    ArrayList<Theatre> theatreList = new ArrayList();

    private static Finnkino finnkino = new Finnkino(); // Tehdään singleton

    public static Finnkino getInstance() {
        return finnkino; // singletonin palautus
    }

    public Finnkino()
    {

    }


    public void readXML() {

        theatreList.clear(); // emptying the list before filling it up (is the user were to spam the read button for example)

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String url = "https://www.finnkino.fi/xml/TheatreAreas/";

            Document doc = builder.parse(url);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getDocumentElement().getElementsByTagName("TheatreArea"); // NodeList of every theatre node

            for (int i = 0; i < nList.getLength(); i++) { // Iterating through the NodeList and listing the Theatres into the ArrayList
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String id = element.getElementsByTagName("ID").item(0).getTextContent();
                    String name = element.getElementsByTagName("Name").item(0).getTextContent();

                    Theatre t = new Theatre(name, id);
                    theatreList.add(t);

                }
            }
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (SAXException ex) {
            ex.printStackTrace();
        } catch (ParserConfigurationException ex) {
            ex.printStackTrace();
        } finally {
            System.out.println("******** DONE *********");
        }
    }

    public ArrayList getTheatreNames() // this provides a list of theatres for the spinner
    {
        ArrayList<String> TheatreNames = new ArrayList();
        TheatreNames.add("Choose a Finnkino:");
        for (Theatre t : theatreList) {
            String s = t.getName();
            TheatreNames.add(s);
        }
        System.out.println("lol");
        return TheatreNames;
    }
}
package com.example.myapplication;

/* Honestly i was too lazy to make a new project so anything Kino-named should actually be SmartPost-related. Sorry for the confusion */

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

public class Finnkino {

    ArrayList<Theatre> postList = new ArrayList();

    private static Finnkino finnkino = new Finnkino(); // Creating singleton

    public static Finnkino getInstance() {
        return finnkino;
    } // returning singleton

    public Finnkino()
    {

    }


    public void readXML_FI() {

        postList.clear(); // emptying the list before filling it up (is the user were to spam the read button for example)

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String url = "http://iseteenindus.smartpost.ee/api/?request=destinations&country=FI&type=APT";

            Document doc = builder.parse(url);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getDocumentElement().getElementsByTagName("item"); // NodeList of every theatre node

            for (int i = 0; i < nList.getLength(); i++) { // Iterating through the NodeList and listing the Theatres into the ArrayList
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String country = element.getElementsByTagName("country").item(0).getTextContent();
                    String name = element.getElementsByTagName("name").item(0).getTextContent();
                    String open = element.getElementsByTagName("availability").item(0).getTextContent();
                    String address = element.getElementsByTagName("address").item(0).getTextContent();


                    /*String[] split = open.split(" ");

                    String openDays = split[0];
                    String openHours = split[1];*/



                    Theatre t = new Theatre(country, name, open, address);
                    postList.add(t);

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

    public void readXML_EST() {

        try {
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            String url = "http://iseteenindus.smartpost.ee/api/?request=destinations&country=EE&type=APT";

            Document doc = builder.parse(url);
            doc.getDocumentElement().normalize();

            NodeList nList = doc.getDocumentElement().getElementsByTagName("item"); // NodeList of every theatre (post office) node

            for (int i = 0; i < nList.getLength(); i++) { // Iterating through the NodeList and listing the Theatres (post offices) into the ArrayList
                Node node = nList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    Element element = (Element) node;

                    String country = element.getElementsByTagName("country").item(0).getTextContent();
                    String name = element.getElementsByTagName("name").item(0).getTextContent();
                    String open = element.getElementsByTagName("availability").item(0).getTextContent();
                    String address = element.getElementsByTagName("address").item(0).getTextContent();

                    List<Boolean> openDays = new ArrayList<Boolean>(Arrays.asList(new Boolean[10]));
                    Collections.fill(openDays, false);

                    // Translating the weekdays to Finnish

                    String openTranslated = "";

                    if (open.contains("E-P"))
                    {
                        openTranslated = open.replace("E", "ma");
                    }
                    if (open.contains("L"))
                    {
                        openTranslated = openTranslated.replace("L", "la");
                    }
                    if (open.contains("P"))
                    {
                        openTranslated = openTranslated.replace("P", "su");
                    }

                    Theatre t = new Theatre(country, name, openTranslated, address); // actually a post office
                    postList.add(t);

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


    public ArrayList getPostNames() // this provides a list of theatres for the spinner
    {
        ArrayList<String> postNames = new ArrayList();
        postNames.add("Choose a post office:");
        for (Theatre t : postList) {
            String s = t.getName();
            postNames.add(s);
        }
        return postNames;
    }


    public Theatre getInfo(int pos) // not used since i only completed assignment 3 / 5
    {
        Theatre t = postList.get(pos);
        return t;
    }

    public ArrayList getPostList() {


        return postList;

    }

    public int getPostListLength() {
        return postList.size();
    }


}
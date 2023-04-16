package xml.reader;

import java.io.*;
import java.util.Scanner;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadXMLFile {


public void run() {
    Scanner scanner = new Scanner(System.in);
    System.out.print("Enter the fields you want to print out (comma-separated): ");
    String fieldsInput = "";
    if (scanner.hasNextLine()) {
        fieldsInput = scanner.nextLine();
    }
    String[] outputFields = fieldsInput.split(",");
    
    try {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("data.xml");
        parseXMLFile(inputStream, outputFields);
    } catch (Exception e) {
        e.printStackTrace();
    }
}


public void parseXMLFile(InputStream inputStream, String[] outputFields) {
    try {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputStream);
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getElementsByTagName("*");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                Element element = (Element) node;
                for (String field : outputFields) {
                    NodeList fieldList = element.getElementsByTagName(field);
                    if (fieldList.getLength() > 0) {
                        System.out.println(field + ": " + fieldList.item(0).getTextContent());
                    }
                }
            }
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

    



  public void readFile() {
    try (InputStream inputStream = getClass().getResourceAsStream("/data.xml");
         BufferedReader br = new BufferedReader(new InputStreamReader(inputStream))) {
        String line;
        while ((line = br.readLine()) != null) {
            System.out.println(line);
        }
    } catch (Exception e) {
        e.printStackTrace();
    }
}

}

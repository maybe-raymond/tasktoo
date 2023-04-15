package xml.reader;

import java.io.*;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

public class ReadXMLFile {
    
  public void run() {
    try {
        InputStream inputStream = getClass().getResourceAsStream("/data.xml");
        parseXMLFile(inputStream);
    } catch (Exception e) {
        e.printStackTrace();
        }
    }

    public void parseXMLFile(InputStream inputStream) {
    try {
        DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
        Document doc = dBuilder.parse(inputStream);
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getElementsByTagName("*");
        for (int i = 0; i < nodeList.getLength(); i++) {
            Node node = nodeList.item(i);
            if (node.getNodeType() == Node.ELEMENT_NODE) {
                System.out.println(node.getTextContent().trim().replace("/n", ""));
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

package xml.reader;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class ReadXMLFile {

    private static final String XML_FILE_PATH = "src/main/resources/data.xml";

    public void run() {
        try {
            List<String> fields = getUserInput();
            Map<String, List<String>> data = parseXMLFile(new File(XML_FILE_PATH), fields);
            String json = convertToJson(data);
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    private List<String> getUserInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter comma-separated list of fields to output: ");
        String input = scanner.nextLine();
        return List.of(input.split("\\s*,\\s*"));
    }


    private Map<String, List<String>> parseXMLFile(File file, List<String> fields) throws Exception {
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document doc = db.parse(file);
        doc.getDocumentElement().normalize();

        Map<String, List<String>> data = new HashMap<>();

        for (String field : fields) {
            NodeList nodeList = doc.getElementsByTagName(field);
            List<String> fieldData = new ArrayList<>();
            for (int i = 0; i < nodeList.getLength(); i++) {
                Node node = nodeList.item(i);
                if (node.getNodeType() == Node.ELEMENT_NODE) {
                    fieldData.add(node.getTextContent());
                }
            }
            data.put(field, fieldData);
        }

        return data;
    }

    private String convertToJson(Map<String, List<String>> data) throws JsonProcessingException {
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.writeValueAsString(data);
    }
}

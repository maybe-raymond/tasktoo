package xml.reader;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

import com.fasterxml.jackson.databind.ObjectMapper;

public class ReadXMLFile {
    
    private static class XMLHandler extends DefaultHandler {
        
        private String currentField;
        private Object object;
        
        public XMLHandler(Object object) {
            this.object = object;
        }
        
        @Override
        public void startElement(String uri, String localName, String qName, Attributes attributes) throws SAXException {
            currentField = qName;
        }
        
        @Override
        public void characters(char[] ch, int start, int length) throws SAXException {
            String value = new String(ch, start, length).trim();
            setObjectField(currentField, value);
        }
        
        private void setObjectField(String fieldName, String value) {
            try {
                object.getClass().getField(fieldName).set(object, value);
            } catch (NoSuchFieldException | IllegalAccessException e) {
                // Ignore fields that don't exist
            }
        }
    }
    
    private static boolean isFieldIncluded(List<String> fields, String fieldName) {
        if (fields.isEmpty()) {
            return true;
        }
        return fields.contains(fieldName);
    }
    
    public static void run() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter the fields to output (comma-separated): ");
        String fieldsInput = scanner.nextLine();
        List<String> fields = new ArrayList<>();
        if (!fieldsInput.isEmpty()) {
            for (String fieldName : fieldsInput.split(",")) {
                fields.add(fieldName.trim());
            }
        }
        scanner.close();
        
        try (InputStream inputStream = ReadXMLFile.class.getClassLoader().getResourceAsStream("data.xml")) {
            SAXParserFactory factory = SAXParserFactory.newInstance();
            SAXParser saxParser = factory.newSAXParser();
            Object object = new Object();
            XMLHandler handler = new XMLHandler(object);
            saxParser.parse(inputStream, handler);
            ObjectMapper mapper = new ObjectMapper();
            String json = mapper.writeValueAsString(object);
            System.out.println(json);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static class Object {
        public String field1;
        public String field2;
        public String field3;
    }
    
}

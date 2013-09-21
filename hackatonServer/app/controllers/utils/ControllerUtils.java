import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;

 
 
// Creating a java DOM XML parser
DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
DocumentBuilder builder = null;
builder = builderFactory.newDocumentBuilder();

//Parsing xml with java dom parser
String xml = ...;
Document xmlDocument = builder.parse(new ByteArrayInputStream(xml.getBytes()));



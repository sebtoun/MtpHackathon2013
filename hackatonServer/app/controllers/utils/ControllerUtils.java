package controllers.utils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import java.io.IOException;
import org.xml.sax.SAXException;
import play.libs.F.Function;
import play.libs.WS;
import play.mvc.*; 

public class ControllerUtils {


	public static String getDropOffs(float left, float bottom, float right, float top){


		String requestURL = 
			"http://api.openstreetmap.org/api/0.6/map?"
			+left.toString()+","
			+bottom.toString()+","
			+right.toString()+","
			+top.toString();

		return xml(requestURL);

		 


		// Creating a java DOM XML parser
		/*DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		try {
		    builder = builderFactory.newDocumentBuilder();
		} catch (ParserConfigurationException e) {
		    e.printStackTrace();  
		}
		//Parsing xml with java dom parser
		try {
			Document xmlDocument = builder.parse(new ByteArrayInputStream(xml.getBytes()));
		} catch (SAXException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		*/	
		

	}
}



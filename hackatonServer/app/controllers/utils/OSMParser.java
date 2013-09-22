package controllers.utils;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import org.w3c.dom.Document;
import java.io.IOException;
import org.xml.sax.SAXException;
import java.util.Vector;
import java.io.ByteArrayInputStream;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathFactory;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import play.libs.Json;
import org.codehaus.jackson.node.ObjectNode;
import org.codehaus.jackson.node.ArrayNode;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpressionException;
import org.codehaus.jackson.node.JsonNodeFactory;
import java.util.*;


public class OSMParser 
{

	public static String buildURLBoundingBox(float left, float bottom, float right, float top)
	{
		String requestURL = 
				"http://api.openstreetmap.org/api/0.6/map?bbox="
				+left+","
				+bottom+","
				+right+","
				+top;
		return requestURL ;
	}

	//returns a vector of recycling places within the bbox
	public static String getDropOffsNearBBox(float left, float bottom, float right, float top){
		
		//Json that we will return
		ArrayNode closeDropOffs = new ArrayNode(JsonNodeFactory.instance);
		try {
			// Creating a java DOM XML parser
			DocumentBuilderFactory builderFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = null;
			Document xmlDocument;
		    builder = builderFactory.newDocumentBuilder();

		    //get the xml via the bbox
		    String url = buildURLBoundingBox(left, bottom, right, top) ;
		    System.out.println("URL : " + url);
		    String xml = HttpGet.getHTML(url);

			//Parsing xml with java dom parser
		    xmlDocument = builder.parse(new ByteArrayInputStream(xml.getBytes()));

		    //creation of an xpath object
		    XPath xPath = XPathFactory.newInstance().newXPath();

			//creating the xpath expression
			String expression = "/osm/node/tag[@v=\"recycling\"]/..";

			//json que l'on retourne:

			//Filling the Json with all the close recycling drop offs
			NodeList nodeList = (NodeList) xPath.compile(expression).evaluate(xmlDocument, XPathConstants.NODESET);
			for (int i=0; i<nodeList.getLength(); i++){
				Node currentDropOff = nodeList.item(i);

				//retrieve longitude, latitude and dropOffId
				String dropOffId = currentDropOff.getAttributes().getNamedItem("id").getNodeValue();
				String latitude = currentDropOff.getAttributes().getNamedItem("lat").getNodeValue();
				String longitude = currentDropOff.getAttributes().getNamedItem("lon").getNodeValue();
				ObjectNode dropOff = Json.newObject();
				dropOff.put("idOSM", dropOffId);
				dropOff.put("latitude", latitude);
				dropOff.put("longitude", longitude);
				System.out.println(currentDropOff.hasChildNodes());

				//retrive the different types of recyclables at the dropOffId
				NodeList tags = currentDropOff.getChildNodes();
				ArrayNode recyclingTypes = new ArrayNode(JsonNodeFactory.instance);
				for(int ii=0; ii<tags.getLength(); ii++){
					Node tag = tags.item(ii);	
					if (tag.getNodeName()=="tag") {
						String kAttribute = tag.getAttributes().getNamedItem("k").getNodeValue();
						System.out.println(kAttribute);
						if (kAttribute.contains("recycling")) {
							recyclingTypes.add(kAttribute.substring(10));
						}
						
					}
				}
				dropOff.put("items", recyclingTypes);
				closeDropOffs.add(dropOff);
			}
		} catch (ParserConfigurationException e) {
		    e.printStackTrace();
		} catch (SAXException e) {
		    e.printStackTrace();
		} catch (IOException e) {
		    e.printStackTrace();
		}
		  catch (XPathExpressionException e) {
            e.printStackTrace();
        } 
		return closeDropOffs.toString();
	}
}
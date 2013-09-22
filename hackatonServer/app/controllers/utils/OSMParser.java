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
		    String xml = HttpGet.getHTML(buildURLBoundingBox(left, bottom, right, top));

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
				Node currentItem = nodeList.item(i);

				//retrieve longitude, latitude and dropOffId
				String dropOffId = currentItem.getAttributes().getNamedItem("id").getNodeValue();
				String latitude = currentItem.getAttributes().getNamedItem("lat").getNodeValue();
				String longitude = currentItem.getAttributes().getNamedItem("lon").getNodeValue();
				ObjectNode dropOff = Json.newObject();
				dropOff.put("idOSM", dropOffId);
				dropOff.put("latitude", latitude);
				dropOff.put("longitude", longitude);
				System.out.println(currentItem.hasChildNodes());

				//retrive the different types of recyclables at the dropOff
				/*
				NodeList tags = currentItem.getChildNodes();
				ObjectNode recyclingTypes = Json.newObject();
				int typeNumber = 0;
				for(int ii=0; ii<tags.getLength(); ii++){
					Node tag = tags.item(ii);	
					System.out.println(tag.getNodeName());
					if (tag.getNodeName()=="tag") {
						String kAttribute = tag.getAttributes().getNamedItem("k").getNodeValue();
						System.out.println(kAttribute);
						if (kAttribute.contains("recycling")) {
							recyclingTypes.put("type"+typeNumber, kAttribute.substring(10));
							typeNumber++;
						}
						
					}
				}
				closeDropOffs.add(recyclingTypes);
				*/
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
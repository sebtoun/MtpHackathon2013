package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import org.codehaus.jackson.node.ObjectNode;
import play.libs.Json;
import play.mvc.BodyParser;

public class Geoloc extends Controller {
	
	
	/**
	 * Contrôleur
	latitude, longitude, distance
	Calculer la Bounding Box
	XML = faire requête http GET OSM boundingBox
	Liste de Dropoffs = Parser XML
	retourner Liste de DropOffs
	 * @param latitude
	 * @param longitude
	 * @param distance
	 * @return
	 */
	@BodyParser.Of(BodyParser.Json.class)
	public static Result getDropoffsBox(float latitude, float longitude, long distance)
	{
		ObjectNode result = Json.newObject();
		result.put("status", "KO");
	    result.put("message", "Missing parameter [name]");
	    System.out.println("[JSON] " + result);
		return ok(result);
	}

}

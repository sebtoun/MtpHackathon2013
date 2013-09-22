package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import models.DropOff;

import org.codehaus.jackson.node.ObjectNode;

import controllers.utils.BoundingBox;
import controllers.utils.HttpGet;
import controllers.utils.OSMParser;
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
	 * @param distance en metres
	 * @return
	 */
	@BodyParser.Of(BodyParser.Json.class)
	public static Result getDropoffsBox(float latitude, float longitude, long distance)
	{
		//Calcul de la BoundingBox
		BoundingBox box = new BoundingBox(latitude, longitude, distance);
		System.out.println("left : " + box.getLeft() + " bottom : " + box.getBottom() + " right : " + box.getRight() + " top : " + box.getTop());
		
		System.out.println(OSMParser.buildURLBoundingBox(box.getLeft(), box.getBottom(), box.getRight(), box.getTop()));
		System.out.println(OSMParser.getDropOffsNearBBox(box.getLeft(), box.getBottom(), box.getRight(), box.getTop()));
		
		String xml = HttpGet.getHTML(OSMParser.buildURLBoundingBox(box.getLeft(), box.getBottom(), box.getRight(), box.getTop()));
				
		ObjectNode result = Json.newObject();
		result.put("status", "KO");
	    result.put("message", "test ok");
	    System.out.println("[JSON] " + result);
		return ok(result);
	}
	
	public static DropOff getDropOff(float latitude, float longitude)
	{
		DropOff dropOff = new DropOff();
		dropOff.setLatitude(latitude);
		dropOff.setLongitude(longitude);
		dropOff.setIdOSM(-1l);
		//TODO
		//récupérer sur OSM si il y a un point de dépot des déchets tout prêt
		DropOff.create(dropOff);
		return dropOff ;
	}

}
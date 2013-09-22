package controllers;

import play.mvc.Controller;
import play.mvc.Result;
import models.DropOff;

import org.codehaus.jackson.JsonNode;
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
		String json = OSMParser.getDropOffsNearBBox(box.getLeft(), box.getBottom(), box.getRight(), box.getTop());
		return ok(json);
	}
	
	public static DropOff getDropOff(float latitude, float longitude)
	{
		DropOff dropOff = new DropOff();
		dropOff.setLatitude(latitude);
		dropOff.setLongitude(longitude);
		dropOff.setIdOSM(-1l);
		BoundingBox box = new BoundingBox(latitude, longitude, 1);
		String json = OSMParser.getDropOffsNearBBox(box.getLeft(), box.getBottom(), box.getRight(), box.getTop());
		System.out.println(json);
		JsonNode listeDropOffs = Json.parse(json);
		int nbDropOff = listeDropOffs.size();
		if(nbDropOff > 0)
		{
			Long idOSM = listeDropOffs.get(0).get("idOSM").asLong() ;
			DropOff dropOffByOSM = DropOff.findByIdOSM(idOSM);
			if(dropOffByOSM != null) dropOff = dropOffByOSM ;
			else
			{
				dropOff.setIdOSM(idOSM);
				DropOff.create(dropOff);
			}
		}
		else DropOff.create(dropOff);
		return dropOff;
	}

}
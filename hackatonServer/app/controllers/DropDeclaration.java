package controllers;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import models.Content;
import models.Drop;
import models.DropOff;
import models.Item;
import models.Photo;
import models.UserAccount;
import play.data.DynamicForm;
import play.data.Form;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;

public class DropDeclaration extends Controller 
{

	/**
	 * Crée un Drop avec l'utilisateur connecté
	 * @return
	 */
	public static Result createDrop()
	{
		MultipartFormData body = request().body().asMultipartFormData();
		System.out.println("Body : " + body);
		if(body != null)
		{
			FilePart picture = body.getFile("picture");
			if (picture != null) 
			{
				System.out.println("picture !!!");
				//try {
			    String fileName = picture.getFilename();
			    String contentType = picture.getContentType(); 
			    File file = picture.getFile();
			    System.out.println("filename :" + fileName );
			    //TODO
			    //PlaceHolder : ici il faut mettre le post Twitter de la photo
			    /*String urlPhotoTwitter = "http://upload.wikimedia.org/wikipedia/commons/4/4a/Flickr_-_The_U.S._Army_-_Trash_collection_in_Iraqi_city.jpg" ;
			    Photo photo = new Photo();
				photo.setUrl(new URL(urlPhotoTwitter));
				Photo.create(photo);
				
			    DynamicForm requestData = Form.form().bindFromRequest();
			    Float latitude = Float.parseFloat(requestData.get("latitude"));
			    Float longitude = Float.parseFloat(requestData.get("longitude"));
			    //sessionToken en int, latitude, longitude
			    //TODO
			    //PlaceHolder : ici il faut récupérer l'utilisateur à partir du token
			    UserAccount user = UserAccount.findById(""+1);
			    //TODO
			    //PlaceHolder : ici il faut récupérer la liste d'items que veut jeter l'utilisateur
			    List<Item> items = new ArrayList<Item>();
			    //ajouter les items
			    Content content = new Content();
			    content.setItems(items);
			    Content.create(content);
			    
			    DropOff dropOff = Geoloc.getDropOff(latitude, longitude);
			    
			    Drop drop = new Drop();
			    drop.setPhoto(photo);
			    drop.setUser(user);
			    drop.setDropOff(dropOff);
			    drop.setContent(content);
			    drop.setCreationDate(new Date());
			    Drop.create(drop);
			    return ok();*/
			/*	} catch (MalformedURLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
					flash("error", "Pb posting on Twitter");
				    return redirect(routes.Application.index());
				}*/
				return ok();
			} 
			else 
			{
				System.out.println("ERROR Missing File");
			    flash("error", "Missing file");
			    return redirect(routes.Application.index());    
			}
		}
		System.out.println("ERROR Missing Body"); 
		return badRequest();
	}
}
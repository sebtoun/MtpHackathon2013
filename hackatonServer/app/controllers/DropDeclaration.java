package controllers;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import models.Drop;
import models.DropOff;
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
		FilePart picture = body.getFile("picture");
		if (picture != null) 
		{
			try {
		    String fileName = picture.getFilename();
		    String contentType = picture.getContentType(); 
		    File file = picture.getFile();
		    //TODO
		    //PlaceHolder : ici il faut mettre le post Twitter de la photo
		    String urlPhotoTwitter = "http://upload.wikimedia.org/wikipedia/commons/4/4a/Flickr_-_The_U.S._Army_-_Trash_collection_in_Iraqi_city.jpg" ;
		    Photo photo = new Photo();
			photo.setUrl(new URL(urlPhotoTwitter));
			Photo.create(photo);
			
		    DynamicForm requestData = Form.form().bindFromRequest();
		    //sessionToken en int, latitude, longitude
		    //TODO
		    //PlaceHolder : ici il faut récupérer l'utilisateur à partir du token
		    UserAccount user = UserAccount.findById(""+1);
		    //TODO
		    //PlaceHolder : ici il faut avec la latitude longitude soit renvoyer un DropOff existant soit en créer un "non renseigné"
		    DropOff dropOff = new DropOff();
		    
		    Drop drop = new Drop();
		    drop.setPhoto(photo);
		    drop.setUser(user);
		    drop.setDropOff(dropOff);
		    
		    Drop.create(drop);
		    return ok();
			} catch (MalformedURLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				flash("error", "Pb posting on Twitter");
			    return redirect(routes.Application.index());
			}
		} 
		else 
		{
			System.out.println("ERROR Missing File");
		    flash("error", "Missing file");
		    return redirect(routes.Application.index());    
		}
	}
}
package controllers;

import java.io.File;

import models.Drop;
import models.DropOff;
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
		FilePart picture = body.getFile("picture");
		if (picture != null) 
		{
		    String fileName = picture.getFilename();
		    String contentType = picture.getContentType(); 
		    File file = picture.getFile();
		    DynamicForm requestData = Form.form().bindFromRequest();
		    //sessionToken en int, latitude, longitude
		    
		    return ok();
		} 
		else 
		{
		    flash("error", "Missing file");
		    return redirect(routes.Application.index());    
		}
	}
}

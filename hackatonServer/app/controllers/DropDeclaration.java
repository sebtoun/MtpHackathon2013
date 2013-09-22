package controllers;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import models.Content;
import models.Drop;
import models.DropOff;
import models.Item;
import models.Photo;
import models.UserAccount;

import org.apache.http.entity.mime.MultipartEntity;
import org.apache.http.entity.mime.content.FileBody;

import play.Logger;
import play.data.DynamicForm;
import play.data.Form;
import play.libs.F.Function;
import play.libs.F.Promise;
import play.libs.WS;
import play.libs.WS.Response;
import play.mvc.Controller;
import play.mvc.Http.MultipartFormData;
import play.mvc.Http.MultipartFormData.FilePart;
import play.mvc.Result;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class DropDeclaration extends Controller 
{

	/**
	 * Crée un Drop avec l'utilisateur connecté
	 * @return
	 */
	public static Result createDrop()
	{
		// si on est connecté
		if(session("username") != null)
		{
			MultipartFormData body = request().body().asMultipartFormData();
			System.out.println("Body : " + body);
			if(body != null)
			{
				FilePart picture = body.getFile("picture");
				if (picture != null) 
				{
					System.out.println("picture !!!");
				    String fileName = picture.getFilename();
				    String contentType = picture.getContentType(); 
				    File file = picture.getFile();
				    System.out.println("filename :" + fileName );
					
				    DynamicForm requestData = Form.form().bindFromRequest();
				    Float latitude = Float.parseFloat(requestData.get("latitude"));
				    Float longitude = Float.parseFloat(requestData.get("longitude"));
				    System.out.println("latitude : " + latitude + " longitude : " + longitude);
				    
				    //sessionToken en int, latitude, longitude
				    //TODO
				    //PlaceHolder : ici il faut récupérer l'utilisateur à partir du token
				    UserAccount user = UserAccount.findByNickname(session("username"));
				    //TODO
				    //PlaceHolder : ici il faut récupérer la liste d'items que veut jeter l'utilisateur
				    List<Item> items = new ArrayList<Item>();
				    //ajouter les items
				    Content content = new Content();
				    content.setItems(items);
				    Content.create(content);
				    
				    DropOff dropOff = Geoloc.getDropOff(latitude, longitude);
				    
				    final Drop drop = new Drop();
				    //drop.setPhoto(photo);
				    drop.setUser(user);
				    drop.setDropOff(dropOff);
				    drop.setContent(content);
				    drop.setCreationDate(new Date());
				    drop.likes = 0l;
				    drop.dislikes = 0l;
				    Drop.create(drop);
				    System.out.println("drop id : " + drop.getId());
				    
				    MultipartEntity entity = new MultipartEntity();
					entity.addPart("picture", new FileBody(file));
					
					ByteArrayOutputStream out = new ByteArrayOutputStream();
					try {
						entity.writeTo(out);
					} catch (IOException e) {
						e.printStackTrace();
					}
					
					InputStream in = new ByteArrayInputStream(out.toByteArray());
					
					Promise<Response> response = WS.url("https://graph.facebook.com/me/photos")
							.setQueryParameter("access_token", session("access_token"))
							.setHeader(entity.getContentType().getName(), entity.getContentType().getValue())
							.post(in);
					
					response.map(new Function<WS.Response, Result>() 
			    	{
			            public Result apply(WS.Response response) 
			            {
			            	if(response.getStatus() == 200)
			            	{
			            		Type hashMapType = new TypeToken<HashMap<String, String>>() {}.getType();
			            		Gson gson = new Gson();
			            		
			            		Map<String, String> result = gson.fromJson(response.getBody(), hashMapType);
			            		
			            		Photo photo = new Photo();
								try {
									photo.setUrl(new URL("http://graph.facebook.com/" + result.get("id")));
								} catch (MalformedURLException e) {
									e.printStackTrace();
									return badRequest();
								}
								Photo.create(photo);
			            		
			                    return ok();
			            	}
			            	else
			            	{
			            		Logger.error("Code d'erreur de retour : " + response.getStatus() + " / " + response.getBody());
			            		return badRequest();
			            	}
			            }
			    	});
					
					return redirect(routes.Feeds.listUserFeed(session("username")));
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
		
		System.out.println("User not connected.");
		return badRequest();
	}
	
	public static Result showForm(float latitude, float longitude)
	{
		return ok(views.html.createDrop.render(latitude, longitude));
	}
}
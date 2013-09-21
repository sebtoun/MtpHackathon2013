package controllers;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.SignatureException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;
import org.apache.http.util.EntityUtils;

import controllers.utils.HttpUtils;

import play.*;
import play.libs.F.Function;
import play.libs.F.Promise;
import play.libs.WS;
import play.libs.WS.Response;
import play.mvc.*;

import views.html.*;

public class Application extends Controller {
  
    public static Result index() 
    {
        return ok(index.render("Your new application is ready."));
    }
    
    public static Result signin()
    {
    	return ok(views.html.signin.render());
    }
  
    /**
     * Stocke un request token (pour faire des requêtes sur l'API Twitter) dans la session.
     * Redirige vers @param callback
     * @param callback url de redirection
     * @return
     */
    public static Result fetchToken(final String callback)
    {
    	String httpMethod = "POST";
    	String baseURL = "https://api.twitter.com/oauth/request_token";
    	Map<String, String> parameters = new HashMap<String, String>();
    	
    	try 
    	{
			parameters.put("oauth_consumer_key", URLEncoder.encode(Play.application().configuration().getString("key.consumer"), "UTF-8"));
			parameters.put("oauth_nonce", URLEncoder.encode(UUID.randomUUID().toString(), "UTF-8"));
	    	parameters.put("oauth_signature_method", URLEncoder.encode("HMAC-SHA1", "UTF-8"));
	    	parameters.put("oauth_timestamp", URLEncoder.encode(Long.toString(System.currentTimeMillis()), "UTF-8"));
	    	parameters.put("oauth_callback", URLEncoder.encode("http://localhost:9000" + callback, "UTF-8"));
	    	parameters.put("oauth_version", URLEncoder.encode("1.0", "UTF-8"));
		} 
    	catch (UnsupportedEncodingException e1) 
    	{
			e1.printStackTrace();
		}
    	
    	
    	String authHeader = "OAuth ";
    	
    	try 
    	{
    		authHeader += "oauth_callback=\"" + parameters.get("oauth_callback")  + "\", ";
    		authHeader += "oauth_consumer_key=\"" + parameters.get("oauth_consumer_key") + "\", ";
        	authHeader += "oauth_nonce=\"" + parameters.get("oauth_nonce") + "\", ";
			authHeader += "oauth_signature=\"" + URLEncoder.encode(HttpUtils.createSignature(baseURL, parameters, httpMethod, ""), "UTF-8") + "\", ";
			authHeader += "oauth_signature_method=\"" + parameters.get("oauth_signature_method") + "\", ";
	    	authHeader += "oauth_timestamp=\"" + parameters.get("oauth_timestamp") + "\", ";
	    	authHeader += "oauth_version=\"" + parameters.get("oauth_version") + "\", ";
		} 
    	catch (UnsupportedEncodingException e) 
    	{
			e.printStackTrace();
		}
    	
    	Promise<Response> response = WS.url(baseURL).setHeader("Authorization", authHeader).post("");
    	
    	return async(response.map(new Function<WS.Response, Result>() 
    	{
            public Result apply(WS.Response response) 
            {
            	if(response.getStatus() == 200)
            	{
            		Map<String, String> parameters = HttpUtils.parsePostParameters(response.getBody());
                	
                	if(parameters.containsKey("oauth_token") && parameters.containsKey("oauth_token_secret") && parameters.containsKey("oauth_callback_confirmed"))
                	{
                		if(parameters.get("oauth_callback_confirmed").equals("true"))
                		{
                			session("oauth_token", parameters.get("oauth_token"));
                			session("oauth_token_secret", parameters.get("oauth_token_secret"));
                		}
                	}

                    return redirect(callback);
            	}
            	else
            	{
            		System.out.println("Code d'erreur de retour : " + response.getStatus() + " / " + response.getBody());
            		return badRequest();
            	}
            }
        }));
    }
    
    
}

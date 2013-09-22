package controllers;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import models.FacebookAccount;
import models.UserAccount;

import org.codehaus.jackson.node.ObjectNode;

import com.google.gson.Gson;

import play.Logger;
import play.Play;
import play.libs.F.Function;
import play.libs.F.Promise;
import play.libs.Json;
import play.libs.WS;
import play.libs.WS.Response;
import play.mvc.Controller;
import play.mvc.Result;
import views.html.index;
import controllers.utils.OAuth1;
import controllers.utils.TwitterHttpUtils;

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
    public static Result fetchTwitterRequestToken(final String callback)
    {
    	String httpMethod = "POST";
    	String baseURL = "http://api.twitter.com/oauth/request_token";
    	Map<String, String> parameters = new HashMap<String, String>();
    	
    	parameters.put("wut", "lol");
		parameters.put("oauth_consumer_key", Play.application().configuration().getString("twitter.key.consumer"));
		parameters.put("oauth_nonce", TwitterHttpUtils.randomString(42));
    	parameters.put("oauth_signature_method", "HMAC-SHA1");
    	parameters.put("oauth_timestamp", Long.toString(System.currentTimeMillis()));
    	parameters.put("oauth_callback", "http://www.xandar.fr/" + callback);
    	parameters.put("oauth_version", "1.0");
    	
    	
    	String authHeader = "OAuth ";
    	
		authHeader += "oauth_callback=\"" + OAuth1.percentEncode(parameters.get("oauth_callback"))  + "\", ";
		authHeader += "oauth_consumer_key=\"" + OAuth1.percentEncode(parameters.get("oauth_consumer_key")) + "\", ";
    	authHeader += "oauth_nonce=\"" + OAuth1.percentEncode(parameters.get("oauth_nonce")) + "\", ";
		authHeader += "oauth_signature=\"" + OAuth1.percentEncode(TwitterHttpUtils.createSignature(baseURL, parameters, httpMethod, "")) + "\", ";
		authHeader += "oauth_signature_method=\"" + OAuth1.percentEncode(parameters.get("oauth_signature_method")) + "\", ";
    	authHeader += "oauth_timestamp=\"" + OAuth1.percentEncode(parameters.get("oauth_timestamp")) + "\", ";
    	authHeader += "oauth_version=\"" + OAuth1.percentEncode(parameters.get("oauth_version")) + "\"";
    	
    	Logger.debug("Headers : " + authHeader);
    	    	
    	Promise<Response> response = WS.url(baseURL).setHeader("Authorization", authHeader).post("wut=lol");
    	
    	return async(response.map(new Function<WS.Response, Result>() 
    	{
            public Result apply(WS.Response response) 
            {
            	if(response.getStatus() == 200)
            	{
            		Map<String, String> parameters = TwitterHttpUtils.parsePostParameters(response.getBody());
                	
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
    
    /**
     * Redirige vers Facebook pour s'athentifier
     * @return
     */
    public static Result loginInWithFacebook()
    {
    	String appId = Play.application().configuration().getString("facebook.appid");
    	String redirectURI = "http://localhost:9000/checkLogin";
    	return redirect("https://www.facebook.com/dialog/oauth?client_id=" + appId + "&redirect_uri=" + redirectURI);
    }
    
    /**
     * Récupère un facebook code pour demander l'access token
     * @return
     */
    public static Result checkLogin()
    {
    	Set<Entry<String, String[]>> entries = request().queryString().entrySet();
    	Map<String, String> parameters = new HashMap<String, String>();
    	
        for (Entry<String,String[]> entry : entries) 
        {
            final String key = entry.getKey();
            final String value = entry.getValue()[0];
            parameters.put(key, value);
        }
        
        if(parameters.containsKey("code"))
        {
        	session("facebook_code", parameters.get("code"));
        	return redirect(routes.Application.getAccessToken());
        }
        
        return badRequest();
    }
    
    /**
     * Récupère un access token pour faire des requêtes sur facebook
     * @return
     */
    public static Result getAccessToken()
    {
    	String appId = Play.application().configuration().getString("facebook.appid");
    	String redirectUri = "http://localhost:9000/checkLogin";
    	String clientSecret = Play.application().configuration().getString("facebook.appsecret");
    	String code = session("facebook_code");
    	
    	String urlLoc = "https://graph.facebook.com/oauth/access_token";
    	
    	Promise<Response> response = WS.url(urlLoc).
    			setQueryParameter("client_id", appId).
    			setQueryParameter("redirect_uri", redirectUri).
    			setQueryParameter("client_secret", clientSecret).
    			setQueryParameter("code", code).get();
    	
    	return async(response.map(new Function<WS.Response, Result>() 
    	{
            public Result apply(WS.Response response) 
            {
            	if(response.getStatus() == 200)
            	{
            		Map<String, String> parameters = TwitterHttpUtils.parsePostParameters(response.getBody());

            		if(parameters.containsKey("access_token"))
            		{
            			session("access_token", parameters.get("access_token"));
            			
            			// on calcule quand le token ne sera plus valide
            			Long secondsUntilExpires = new Long(parameters.get("expires"));
            			Long currentTimestamp = System.currentTimeMillis();
            			
            			session("access_token_expire", Long.toString(secondsUntilExpires + currentTimestamp));
            		}
            		
                    return redirect(routes.Application.getUserInfos());
            	}
            	else
            	{
            		Logger.error("Code d'erreur de retour : " + response.getStatus() + " / " + response.getBody());
            		return badRequest();
            	}
            }
        }));
    }
    
    /**
     * Interroge facebook pour connaitre les infos du token
     * @return
     */
    public static Result checkAccessToken()
    {
    	/*if(session("access_token") != null)
    	{
    		if(Long.parseLong(session("access_token_expires")) <= System.currentTimeMillis())
    		{
    			// le token n'est plus bon, il faut le regénérer.
    			redirect(routes.Application.loginInWithFacebook());
    		}
    		else
    		{
    			// on vérifie que le token est authentique
    			return ok();
    		}
    	}
    	else
    	{
    		redirect(routes.Application.loginInWithFacebook());
    	}*/
    	
    	return ok();
    }
    
    /**
     * Vérifie que le token est bon
     * @return
     */
    private static boolean isTokenSecure()
    {
    	return false;
    }
    
    /**
     * Récupère les infos de la personne connectée
     * @return
     */
    public static Result getUserInfos()
    {
    	String urlLoc = "https://graph.facebook.com/me";
    	
    	Promise<Response> response = WS.url(urlLoc).setQueryParameter("access_token",  session("access_token")).get();
    	
    	return async(response.map(new Function<WS.Response, Result>() 
    	{
            public Result apply(WS.Response response) 
            {
            	if(response.getStatus() == 200)
            	{
            		Gson gson = new Gson();
            		FacebookAccount fbAccount = gson.fromJson(response.getBody(), FacebookAccount.class);
            		
            		Logger.debug("Received response : " + response.getBody());
            		
            		// une fois qu'on a les infos, on vérifie si l'utilisateur existe dans notre appli. Si oui, on met a jour ses infos FB. Si non, on le crée.
            		UserAccount userAccount = UserAccount.findByNickname(fbAccount.username);
            		
            		if(userAccount == null)
            		{
            			userAccount = new UserAccount();
            			userAccount.facebookAccount = fbAccount;
            			userAccount.nickname = fbAccount.username;
            			userAccount.save();
            		}
            		else
            		{
            			userAccount.facebookAccount = fbAccount;
            			userAccount.save();
            		}
            		
            		session("username", fbAccount.username);
            		session("name", fbAccount.name);
            		
                    return redirect("/");
            	}
            	else
            	{
            		Logger.error("Code d'erreur de retour : " + response.getStatus() + " / " + response.getBody());
            		return badRequest();
            	}
            }
        }));
    }
    
    public static Result clearSession()
    {
    	session().clear();
    	return redirect("/");
    }
}

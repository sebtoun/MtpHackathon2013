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

import play.*;
import play.libs.WS;
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
  
    public static Result confirmSignIn()
    {
    	String httpMethod = "POST";
    	String baseURL = "";
    	Map<String, String> parameters = new HashMap<String, String>();
    	
    	parameters.put("oauth_consumer_key", Play.application().configuration().getString("key.consumer"));
    	parameters.put("oauth_nonce", UUID.randomUUID().toString());
    	parameters.put("oauth_signature_method", "HMAC-SHA1");
    	parameters.put("oauth_timestamp", Long.toString(System.currentTimeMillis()));
    	parameters.put("oauth_callback", "");
    	parameters.put("oauth_version", "1.0");
    	
    	String authHeader = "OAuth ";
    	
    	authHeader += "oauth_callback=\"" + parameters.get("oauth_callback") + "\",";
    	authHeader += "oauth_consumer_key=\"" + parameters.get("oauth_consumer_key") + "\",\n";
    	authHeader += "oauth_nonce=\"" + parameters.get("oauth_nonce") + "\",";
    	authHeader += "oauth_signature=\"" + createSignature(baseURL, parameters, httpMethod, "") + "\",";
    	authHeader += "oauth_signature_method=\"" + parameters.get("oauth_signature_method") + "\",";
    	authHeader += "oauth_timestamp=\"" + parameters.get("oauth_timestamp") + "\",";
    	authHeader += "oauth_version=\"" + parameters.get("oauth_version") + "\",";
    	
    	//WS.url("").setHeader("Authorization", );
    	
    	return ok();
    }
    
    public static String createSignature(String baseURL, Map<String, String> parameters, String method, String oauthToken)
    {
    	String baseParameters = "";
    	
    	int i = 0;
    	
    	List<String> sortedKeys = new ArrayList<String>(parameters.keySet());
    	Collections.sort(sortedKeys);
    	
    	for(String key : sortedKeys)
    	{
    		if(i + 1 < parameters.size())
    			baseParameters += key + "=" + parameters.get(key) + "&";
    		else
    			baseParameters += key + "=" + parameters.get(key);
    		
    		i++;
    	}
    	
    	String baseSignature = "";
    	
    	try {
			baseSignature = method + "&" + URLEncoder.encode(baseURL, "UTF-8") + "&" + URLEncoder.encode(baseParameters, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	
    	String signingKey = Play.application().configuration().getString("key.consumer.secret") + "&" + oauthToken;
    	
    	return hmacSha1(baseSignature, signingKey);
    }	
    
    public static String hmacSha1(String value, String key) {
        try {
            // Get an hmac_sha1 key from the raw key bytes
            byte[] keyBytes = key.getBytes();           
            SecretKeySpec signingKey = new SecretKeySpec(keyBytes, "HmacSHA1");

            // Get an hmac_sha1 Mac instance and initialize with the signing key
            Mac mac = Mac.getInstance("HmacSHA1");
            mac.init(signingKey);

            // Compute the hmac on input data bytes
            byte[] rawHmac = mac.doFinal(value.getBytes());

            // Convert raw bytes to Hex
            byte[] hexBytes = new Hex().encode(rawHmac);

            //  Covert array of Hex bytes to a String
            return new String(hexBytes, "UTF-8");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}

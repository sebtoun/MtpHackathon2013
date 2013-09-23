package controllers.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import javax.crypto.Mac;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

import play.Logger;
import play.Play;

public class TwitterHttpUtils 
{
	public static String createSignature(String baseURL, Map<String, String> parameters, String method, String oauthTokenSecret)
    {
    	String baseParameters = "";
    	
    	int i = 0;
    	
    	List<String> sortedKeys = new ArrayList<String>(parameters.keySet());
    	Collections.sort(sortedKeys);
    	
    	for(String key : sortedKeys)
    	{
    		if(i + 1 < parameters.size())
    			baseParameters += OAuth1.percentEncode(key) + "=" + OAuth1.percentEncode(parameters.get(key)) + "&";
    		else
    			baseParameters += OAuth1.percentEncode(key) + "=" + OAuth1.percentEncode(parameters.get(key));
    		
    		i++;
    	}
    	
    	Logger.debug("Base Parameters : " + baseParameters);
    	
    	String baseSignature = "";
    	
		baseSignature = method + "&" + OAuth1.percentEncode(baseURL) + "&" + OAuth1.percentEncode(baseParameters);
    	
    	Logger.debug("Base Signature After :" + baseSignature);
    	
    	String signingKey = OAuth1.percentEncode(Play.application().configuration().getString("twitter.key.secret.consumer")) + "&" + OAuth1.percentEncode(oauthTokenSecret);
    	
    	//Logger.debug("Signing key : " + signingKey);
    	
    	String hash = "";
		try {
			hash = hmacSha1(baseSignature, signingKey);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
    	
    	//Logger.debug("hash : " + hash);
    	
    	return hash;
    }
    
    /**
     * Construit a partir d'une chaine de type cle1=val1&cle2=val&... une hashmap 
     * @param rawBody
     * @return
     */
    public static Map<String, String> parsePostParameters(String rawBody)
    {
    	Map<String, String> parameters = new HashMap<String, String>();
    	
    	String[] rawParameters = rawBody.split("&");
    	
    	for(int i = 0; i < rawParameters.length; i++)
    	{
    		String rawParameter = rawParameters[i];
    		String[] parsedParameter = rawParameter.split("=");
    		
    		parameters.put(parsedParameter[0], parsedParameter[1]);
    	}
    	
    	return parameters;
    }
    
    /**
     * Crée une signature type HMAC_SHA1 en base64
     * @param value
     * @param key
     * @return
     */
    private static String hmacSha1(String baseString, String keyString) throws GeneralSecurityException, UnsupportedEncodingException {

        SecretKey secretKey = null;

        byte[] keyBytes = keyString.getBytes();
        secretKey = new SecretKeySpec(keyBytes, "HmacSHA1");

        Mac mac = Mac.getInstance("HmacSHA1");

        mac.init(secretKey);

        byte[] text = baseString.getBytes();

        return new String(Base64.encodeBase64(mac.doFinal(text))).trim();
    }
    
	public static String randomString(int len) 
	{
		String AB = "0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ";
		Random rnd = new Random();
		
		StringBuilder sb = new StringBuilder(len);
		for (int i = 0; i < len; i++)
			sb.append(AB.charAt(rnd.nextInt(AB.length())));
		return sb.toString();
	}

}

package controllers.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Hex;

import play.Logger;
import play.Play;

public class HttpUtils 
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
    			baseParameters += key + "=" + parameters.get(key) + "&";
    		else
    			baseParameters += key + "=" + parameters.get(key);
    		
    		i++;
    	}
    	
    	Logger.debug("Base Parameters : " + baseParameters);
    	
    	String baseSignature = "";
    	
    	try {
			baseSignature = method + "&" + URLEncoder.encode(baseURL, "UTF-8") + "&" + URLEncoder.encode(baseParameters, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
    	
    	Logger.debug("Base Signature :" + baseSignature);
    	
    	String signingKey = Play.application().configuration().getString("key.secret.consumer") + "&" + oauthTokenSecret;
    	
    	Logger.debug("Signing key : " + signingKey);
    	
    	String hash = hmacSha1(baseSignature, signingKey);
    	
    	Logger.debug("hash : " + hash);
    	
    	return hash;
    }
    
    /**
     * Crée une signature tye HMAC_SHA1
     * @param value
     * @param key
     * @return
     */
    public static String hmacSha1(String value, String key) 
    {
        try 
        {
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
    
    /**
     * Construit a partir d'une chaine de type cle1=val1&cle2=val&... une hashmap 
     * @param rawBody
     * @return
     */
    public static Map<String, String> parsePostParameters(String rawBody)
    {
    	Logger.debug("Raw post parameters = " + rawBody);
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

}

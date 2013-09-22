package controllers.utils;

import java.io.*;
import java.net.*;
import java.lang.StringBuilder;

public class HttpGet {

   public static String getHTML(String urlToRead) {
      URL url;
      HttpURLConnection conn;
      BufferedReader rd;
      String line;
      StringBuilder result = new StringBuilder();
      try {
         url = new URL(urlToRead);
         conn = (HttpURLConnection) url.openConnection();
         conn.setRequestMethod("GET");
         rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
         while ((line = rd.readLine()) != null) {
            result.append(line); 
         }
         rd.close();
      } catch (Exception e) {
         e.printStackTrace();
      }
      return result.toString();
   }
}
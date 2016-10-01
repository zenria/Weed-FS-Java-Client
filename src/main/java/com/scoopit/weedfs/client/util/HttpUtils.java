package com.scoopit.weedfs.client.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.apache.http.HttpResponse;

public class HttpUtils {

    /**
     * @deprecated
     * @param response
     * @throws IOException
     */
    public static void debugLog (HttpResponse response) throws IOException{

      BufferedReader br= new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
      StringBuilder sb = new StringBuilder();
      String line;
      while ((line = br.readLine()) != null) {
          sb.append(line);
      } 
      System.out.println(sb.toString());
      br.close();
    }
}

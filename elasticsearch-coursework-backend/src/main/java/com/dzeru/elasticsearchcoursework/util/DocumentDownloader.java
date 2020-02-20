package com.dzeru.elasticsearchcoursework.util;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class DocumentDownloader {

    public static String downloadDocument(String url) throws Exception {
        try {
            URL urlObj = new URL(url);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlObj.openStream()));
            StringBuilder responseBuilder = new StringBuilder();
            String inputLine;

            while((inputLine = bufferedReader.readLine()) != null) {
                responseBuilder.append(inputLine);
            }
            bufferedReader.close();

            return responseBuilder.toString();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return "";
    }
}

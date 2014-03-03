package com.flickrbrowser.rest;

import org.apache.commons.io.IOUtils;
import org.apache.http.client.methods.HttpGet;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 Static client that can be used to perform http requests
 */
public class RestClient {

    public static String execute(String url) {
        try {
            return execute(new URL(url));
        } catch(IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String execute(HttpGet request) {
        try {
            return execute(request.getURI().toURL());
        } catch(IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    public static String execute(URL url) {
        try {
            HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setConnectTimeout(3000);
            urlConnection.setReadTimeout(5000);
            InputStream in = null;
            try {
                in = new BufferedInputStream(urlConnection.getInputStream());
                String result = getStringFromInputStream(in);
                return result;
            } finally {
                IOUtils.closeQuietly(in);
                urlConnection.disconnect();
            }
        } catch(IOException e) {
            e.printStackTrace();
            return "";
        }
    }

    private static String getStringFromInputStream(InputStream is) {

        BufferedReader br = null;
        StringBuilder sb = new StringBuilder();

        String line;
        try {

            br = new BufferedReader(new InputStreamReader(is));
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }

        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return sb.toString();
    }


}

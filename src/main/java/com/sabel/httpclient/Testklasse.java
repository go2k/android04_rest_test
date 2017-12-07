package com.sabel.httpclient;


import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;


public class Testklasse {

    public static void main(String[] args) throws IOException {
        URL url = new URL("https://bitaps.com/api/ticker/average");
        Proxy proxy = new Proxy(Proxy.Type.HTTP, new InetSocketAddress("192.168.100.1", 8080));
        URLConnection urlConnection = url.openConnection(proxy);
        BufferedReader br = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

        String line = "";
        String json = "";

        while ((line = br.readLine()) != null) {
            json = line;
        }
        System.out.println(json);

        String[] splits = json.split("eur");
        json = splits[1].substring(4, 11);
        System.out.println(json);
        br.close();

        System.out.println("-------------------");

        // Aufgabe String (JSON) in ein JAVA_Objekt "umwandeln"

        URL urlNeu = new URL("https://bitaps.com/api/ticker/average");
        URLConnection urlConnectionNeu = urlNeu.openConnection(proxy);
        InputStream is = null;
        try {
            is = urlConnectionNeu.getInputStream();
            JsonReader jsonReader = Json.createReader(is);
            JsonObject jsonObject = jsonReader.readObject();
            System.out.println(jsonObject.toString());
            JsonObject fx_rates = jsonObject.getJsonObject("fx_rates");
            System.out.println(fx_rates.toString());
            System.out.println(fx_rates.getString("eur"));

        } catch (Exception e) {
            e.getStackTrace();
        } finally {
            if (is != null) {
                is.close();
            }
        }
        System.out.println("-------------------");
    }


}

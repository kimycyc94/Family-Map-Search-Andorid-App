package com.example.familymapclient;

import com.google.gson.*;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import Request.*;
import Result.*;

public class ServerProxy {
    URL url;
    private String serverHost;
    private int serverPort;

    public ServerProxy(String serverHost, int serverPort) {
        this.serverHost = serverHost;
        this.serverPort = serverPort;
    }

    private String URLPorter(URL url, String req) {
        try {
            String returnVal;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("POST");
            conn.setReadTimeout(10000);
            conn.setDoOutput(true);
            conn.connect();

            OutputStream reqBody = conn.getOutputStream();
            reqBody.write(req.getBytes());
            reqBody.close();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.e("ServerProxy", "Error: 200");
                returnVal = StringReader(conn.getErrorStream());
                return returnVal;
            }
            else {
                returnVal = StringReader(conn.getInputStream());
                return returnVal;
            }
        } catch (IOException e) {
            e.printStackTrace();
            Log.e("ServerProxy", "Error: URL Porter failed");
            return null;
        }
    }

    private String URLGetter(URL url, String authToken) {
        try {
            String returnVal;
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestProperty("Authorization", authToken);
            conn.setRequestMethod("GET");
            conn.setReadTimeout(10000);
            conn.setDoOutput(false);
            conn.connect();

            if (conn.getResponseCode() != HttpURLConnection.HTTP_OK) {
                Log.e("ServerProxy", "Error: 200");
                returnVal = StringReader(conn.getErrorStream());
                return returnVal;
            }
            else {
                returnVal = StringReader(conn.getInputStream());
                return returnVal;
            }
        } catch (IOException e) {
            Log.e("ServerProxy", "Error: URL Getter failed");
            return null;
        }
    }

    private String StringReader(InputStream stream) throws IOException {
        InputStreamReader reader = new InputStreamReader(stream);
        StringBuilder strBuilder = new StringBuilder();
        String returnVal;
        char[] charArray = new char[1024];
        int i;
        while((i = reader.read(charArray)) > 0) {
            strBuilder.append(charArray, 0, i);
        }
        returnVal = strBuilder.toString();
        return returnVal;
    }

    //============================ Returning results From here ============================//
    public RegisterResult registerResult(RegisterRequest request) {
        try {
            url = new URL("http", serverHost, serverPort, "/user/register");
        } catch (MalformedURLException e) {
            Log.e("ServerProxy", "Error: Register has been failed");
            return null;
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String req = gson.toJson(request);
        String result = URLPorter(url, req);
        RegisterResult returnVal = gson.fromJson(result, RegisterResult.class);
        return returnVal;
    }

    public LoginResult loginResult(LoginRequest request) {
        try {
            url = new URL("http", serverHost, serverPort, "/user/login");
        } catch (MalformedURLException e) {
            Log.e("ServerProxy", "Error: Login has been failed");
            return null;
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String req = gson.toJson(request);
        String result = URLPorter(url, req);
        LoginResult returnVal = gson.fromJson(result, LoginResult.class);
        return returnVal;
    }

    public OnePersonResult getPerson(String personID, String authToken) {
        try {
            url = new URL("http", serverHost, serverPort, "/person/" + personID);
        } catch (MalformedURLException e) {
            Log.e("ServerProxy", "Error: getPerson has been failed");
            return null;
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String result = URLGetter(url, authToken);
        OnePersonResult returnVal = gson.fromJson(result, OnePersonResult.class);
        return returnVal;
    }

    public AllPersonsResult getAllPersons(String authToken) {
        try {
            url = new URL("http", serverHost, serverPort, "/person");
        } catch (MalformedURLException e) {
            Log.e("ServerProxy", "Error: getAllPersons has been failed");
            return null;
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String result = URLGetter(url, authToken);
        AllPersonsResult returnVal = gson.fromJson(result, AllPersonsResult.class);
        return returnVal;
    }

    public AllEventsResult getAllEvents(String authToken) {
        try {
            url = new URL("http", serverHost, serverPort, "/event");
        } catch (MalformedURLException e) {
            Log.e("ServerProxy", "Error: getAllEvents has been failed");
            return null;
        }
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String result = URLGetter(url, authToken);
        AllEventsResult returnVal = gson.fromJson(result, AllEventsResult.class);
        return returnVal;
    }
    //============================== Returning results done ==============================//
}

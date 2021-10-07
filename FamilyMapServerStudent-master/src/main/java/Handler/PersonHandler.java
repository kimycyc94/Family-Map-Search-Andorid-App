package Handler;

import Result.OnePersonResult;
import Result.AllPersonsResult;
import Serializer.Serializer;
import Service.PersonService;
import Service.AllPersonsService;
import com.sun.net.httpserver.*;
import java.io.*;
import java.net.HttpURLConnection;

public class PersonHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            //if it gets "GET"
            if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
                if (exchange.getRequestHeaders().containsKey("Authorization")) {
                    String urlPath = exchange.getRequestURI().toString();
                    String authToken = exchange.getRequestHeaders().getFirst("Authorization");
                    OutputStream respBody = exchange.getResponseBody();
                    String returnValue = "";
                    boolean success = false;

                    if (split(urlPath).length <= 2) {  //All persons
                        AllPersonsService allPersonsServe = new AllPersonsService();
                        AllPersonsResult result = allPersonsServe.getAllPersons(authToken);
                        returnValue = Serializer.serialize(result);
                        success = result.isSuccess();
                    }
                    else {  // Single person
                        String persons = new String();
                        for (int i = 8; i < urlPath.length(); i++) {
                            persons += urlPath.charAt(i);
                        }
                        PersonService personServe = new PersonService();
                        OnePersonResult result = personServe.getPerson(authToken, persons);
                        returnValue = Serializer.serialize(result);
                        success = result.isSuccess();
                    }

                    if (success != false) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    }
                    else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    }
                    writeString(returnValue, respBody);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, 0);
                }
            }
            //if it's not getting "GET"
            else {
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
            }
        }
        catch (IOException e) {
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);
            e.printStackTrace();
        }
        exchange.getResponseBody().close();
    }

    private String[] split(String str) {
        String tokens[] = str.split("/");
        return tokens;
    }
}

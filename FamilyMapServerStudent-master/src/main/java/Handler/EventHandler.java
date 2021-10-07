package Handler;

import Result.AllEventsResult;
import Result.OneEventResult;
import Result.ParentResult;
import Service.AllEventsService;
import Service.EventService;
import com.sun.net.httpserver.*;
import java.io.*;
import java.net.HttpURLConnection;
import Serializer.*;

public class EventHandler extends Handler {
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

                    if (split(urlPath).length <= 2) {  //All events
                        AllEventsService allEventsServe = new AllEventsService();
                        AllEventsResult result = allEventsServe.getAllEvents(authToken);
                        returnValue = Serializer.serialize(result);
                        success = result.isSuccess();
                    }
                    else {  // Single event
                        String events = new String();
                        for (int i = 7; i < urlPath.length(); i++) {
                            events += urlPath.charAt(i);
                        }
                        EventService eventServe = new EventService();
                        OneEventResult result = eventServe.getEvent(authToken, events);
                        if (result.isSuccess() != true) {
                            ParentResult finalResult = new ParentResult();
                            finalResult.setMessage(result.getMessage());
                            finalResult.setSuccess(result.isSuccess());
                            returnValue = Serializer.serialize(finalResult);
                            success = finalResult.getSuccess();
                        }
                        else {
                            returnValue = Serializer.serialize(result);
                            success = result.isSuccess();
                        }
                    }

                    if (success == true) {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                    }
                    else {
                        exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                    }
                    //exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
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

package Handler;

import Result.FillResult;
import Serializer.Serializer;
import Service.FillService;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class FillHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            //if it gets "GET"
            if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
                FillService fillServe = new FillService();
                FillResult result;
                String urlPath = exchange.getRequestURI().toString();
                OutputStream respBody = exchange.getResponseBody();
                int generation = 4;
                String associatedUserName = "";
                String returnValue = "";

                for (int i = 6; i < urlPath.length(); i++) {
                    if (urlPath.charAt(i) != '/') {
                        associatedUserName += urlPath.charAt(i);
                    }
                    else {
                        if (urlPath.length() > 1 + i) {
                            generation = Character.getNumericValue(urlPath.charAt(1 + i));
                            if (generation == 0) {
                                generation = 4;
                            }
                            break;
                        }
                    }
                }

                result = fillServe.fill(associatedUserName, generation);
                boolean success = result.getSuccess();
                if (success != false) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                //exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                returnValue = Serializer.serialize(result);
                writeString(returnValue, respBody);
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
}

package Handler;

import Request.LoadRequest;
import Result.LoadResult;
import Serializer.Serializer;
import Service.LoadService;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;

public class LoadHandler extends Handler {
    @Override
    public void handle(HttpExchange exchange) throws IOException {
        LoadResult result;
        OutputStream responseBody = exchange.getResponseBody();
        String returnValue = "";
        boolean success;
        try {
            // Determine the HTTP request type (GET, POST, etc.).
            // Only allow POST requests for this operation.
            // This operation requires a POST request, because the
            // client is "posting" information to the server for processing.
            if (exchange.getRequestMethod().toUpperCase().equals("POST")) {
                // Extract the JSON string from the HTTP request body

                // Get the request body input stream
                InputStream reqBody = exchange.getRequestBody();

                // Read JSON string from the input stream
                String reqData = readString(reqBody);

                LoadRequest loadReq = new LoadRequest();
                loadReq = (LoadRequest) Serializer.deserialize(reqData, loadReq);
                LoadService loadServe = new LoadService();
                result = loadServe.load(loadReq);
                returnValue = Serializer.serialize(result);
                success = result.getSuccess();
                // Display/log the request JSON data

                if (success != false) {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_BAD_REQUEST, 0);
                }
                // Start sending the HTTP response to the client, starting with
                // the status code and any defined headers.
                //exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
            } else {
                // The auth token was invalid somehow, so we return a "not authorized"
                // status code to the client.
                exchange.sendResponseHeaders(HttpURLConnection.HTTP_UNAUTHORIZED, 0);
            }
            writeString(returnValue, responseBody);
            // We are not sending a response body, so close the response body
            // output stream, indicating that the response is complete.
            exchange.getResponseBody().close();
        } catch (IOException e) {
            // Some kind of internal error has occurred inside the server (not the
            // client's fault), so we return an "internal server error" status code
            // to the client.
            exchange.sendResponseHeaders(HttpURLConnection.HTTP_INTERNAL_ERROR, 0);

            // We are not sending a response body, so close the response body
            // output stream, indicating that the response is complete.
            exchange.getResponseBody().close();

            // Display/log the stack trace
            e.printStackTrace();
        }
    }
}

package Handler;

import com.sun.net.httpserver.HttpExchange;
import java.io.IOException;
import java.io.*;
import java.net.HttpURLConnection;
import java.nio.file.Files;

public class FileHandler extends Handler {

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        try {
            //if it gets "GET"
            if (exchange.getRequestMethod().toUpperCase().equals("GET")) {
                String urlPath = exchange.getRequestURI().toString();

                if (urlPath == null || urlPath.equals("/")) {
                    urlPath = "/index.html";
                }
                String filePath = "web" + urlPath;
                File file = new File(filePath);
                if (!file.exists()) {
                    filePath = "web/HTML/404.html";
                    file = new File(filePath);
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_NOT_FOUND, 0);
                }
                else {
                    exchange.sendResponseHeaders(HttpURLConnection.HTTP_OK, 0);
                }

                OutputStream respBody = exchange.getResponseBody();
                Files.copy(file.toPath(), respBody);
                respBody.flush();
                respBody.close();
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

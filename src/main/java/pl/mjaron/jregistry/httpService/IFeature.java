package pl.mjaron.jregistry.httpService;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;

import java.io.IOException;

/**
 * Interface of any feature added to property server.
 * Usually te features are:
 * * Hosting properties data.
 * hosting web browser interface to present / modify registry data.
 */
public interface IFeature extends com.sun.net.httpserver.HttpHandler {

    /**
     * @return path to the HTTP resource. Standard features have arbitrary paths.
     */
    String getHttpPath();

    static void setResponseHeaders(HttpExchange exchange, String contentType, int responseCode) throws IOException {
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.set("Content-Type", contentType);
        exchange.sendResponseHeaders(responseCode, 0);
    }

    String HTTP_CONTENT_HTML = "text/html";
    int HTTP_RESPONSE_OK = 200;

}

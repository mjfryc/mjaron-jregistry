package pl.mjaron.jregistry.httpService;

import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;
import pl.mjaron.jregistry.core.IProperty;
import pl.mjaron.jregistry.core.IPropertyVisitor;
import pl.mjaron.jregistry.core.PropertyPath;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.concurrent.Executors;

/**
 * Source && inspiration: https://stackoverflow.com/a/1186372/6835932
 * Source: https://docs.oracle.com/javase/8/docs/jre/api/net/httpserver/spec/com/sun/net/httpserver/HttpServer.html
 * Funny tree: https://codepen.io/philippkuehn/pen/QbrOaN
 * Simple Json reference: https://crunchify.com/how-to-write-json-object-to-file-in-java/
 */
public class PropertyServer {
    public static void main(String[] args) throws IOException {
        PropertyServer srv = new PropertyServer(null, 8080).start();
    }

    private final HttpServer server;

    public PropertyServer(final IProperty root, final int port) {
        try {
            final InetSocketAddress address = new InetSocketAddress(port);
            server = HttpServer.create(address, 0);
            server.createContext("/", new HttpHandler(root));
            server.setExecutor(Executors.newCachedThreadPool());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Property server has failed.", e);
        }
    }

    public PropertyServer start() {
        server.start();
        System.out.println("Property server is listening on port " + server.getAddress().getPort());
        return this;
    }
}

class HttpHandler implements com.sun.net.httpserver.HttpHandler {

    private IProperty root;

    private static final String HTTP_CONTENT_HTML = "text/html";
    private static final int HTTP_RESPONSE_OK = 200;

    private static void setResponseHeaders(HttpExchange exchange, String contentType, int responseCode) throws IOException {
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.set("Content-Type", contentType);
        exchange.sendResponseHeaders(responseCode, 0);
    }

    public HttpHandler(IProperty root) {
        this.root = root;
    }

    public void handle(HttpExchange exchange) throws IOException {
        try {
            String requestMethod = exchange.getRequestMethod();
            final String path = exchange.getRequestURI().getPath();
            System.out.println("Handling request: " + exchange.getRequestMethod() + "|" + exchange.getRequestURI().getPath());
            if (requestMethod.equalsIgnoreCase("GET")) {
                if (path.equals("/h")) {
                    handleHeaders(exchange);
                } else if (path.equals("/pure.css") || path.equals("/grids-responsive-min.css")) {
                    handlePureCss(exchange);
                } else if (path.equals("/r")) {
                    handleRegistryData(exchange);
                } else if (path.equals("/")) {
                    handleRegistry(exchange);
                }
            }
            if (requestMethod.equalsIgnoreCase("POST")) {
                if (path.startsWith("/u")) {
                    handleRegistryUpdatePost(exchange);
                }
            }
        } catch (final Exception e) {
            e.printStackTrace();
        }
    }

    private void handlePureCss(HttpExchange exchange) throws IOException {
        OutputStream responseBody = null;
        try {
            Headers responseHeaders = exchange.getResponseHeaders();
            responseHeaders.set("Content-Type", "text/css");
            exchange.sendResponseHeaders(200, 0);
            responseBody = exchange.getResponseBody();
            if (exchange.getRequestURI().getPath().equals("/pure.css")) {
                responseBody.write(PureCss.content().getBytes(StandardCharsets.UTF_8));
            }
            else if (exchange.getRequestURI().getPath().equals("/grids-responsive-min.css")) {
                responseBody.write(PureCss.content().getBytes(StandardCharsets.UTF_8));
            }
        } finally {
            assert responseBody != null;
            responseBody.close();
        }
    }

    private void handleHeaders(HttpExchange exchange) throws IOException {
        Headers responseHeaders = exchange.getResponseHeaders();
        responseHeaders.set("Content-Type", "text/plain");
        exchange.sendResponseHeaders(200, 0);

        OutputStream responseBody = null;
        try {
            responseBody = exchange.getResponseBody();
            Headers requestHeaders = exchange.getRequestHeaders();
            Set<String> keySet = requestHeaders.keySet();
            for (String key : keySet) {
                List<String> values = requestHeaders.get(key);
                String s = key + " = " + values.toString() + "\n";
                responseBody.write(s.getBytes());
            }
        } finally {
            assert responseBody != null;
            responseBody.close();
        }

    }

    private void handleRegistryData(HttpExchange exchange) throws IOException {
        OutputStream r = null;
        try {
            setResponseHeaders(exchange, HTTP_CONTENT_HTML, HTTP_RESPONSE_OK);
            r = exchange.getResponseBody();
            final String jProperties = root.accept(new IPropertyVisitor.JsonVisitor()).getJsonString();
            r.write(jProperties.getBytes(StandardCharsets.UTF_8));
            r.flush();
        } finally {
            assert r != null;
            r.close();
        }
    }


    private void handleRegistry(HttpExchange exchange) throws IOException {

        OutputStream r = null;
        try {
            setResponseHeaders(exchange, HTTP_CONTENT_HTML, HTTP_RESPONSE_OK);
            r = exchange.getResponseBody();
            r.write(MainPageHtml.generate().getBytes(StandardCharsets.UTF_8));
            r.flush();
        } finally {
            assert r != null;
            r.close();
        }
        System.out.println();

        final String jProperties = root.accept(new IPropertyVisitor.JsonVisitor()).getJsonString();
        System.out.println(jProperties);
    }

    private static Map<String, String> parseQuery(String query) {
        if (query.startsWith("?")) {
            query = query.substring(1);
        }
        Map<String, String> map = new HashMap<>();
        final String[] valuePairs = query.split("&");
        for (final String valuePairEntry : valuePairs) {
            final String[] nameValueSplit = valuePairEntry.split("=");
            if (nameValueSplit.length == 2) {
                map.put(nameValueSplit[0], nameValueSplit[1]);
            }
        }
        return map;
    }

    private void handleRegistryUpdatePost(HttpExchange exchange) throws IOException {
        OutputStream r = null;
        try {
            String query = exchange.getRequestURI().getQuery();

            final Map<String, String> params = parseQuery(query);
            final String path = params.get("path");
            final String value = params.get("value");
            final IProperty property = IProperty.getChildByPath(root, new PropertyPath(path));
            property.getIO().setTextValue(value);
            System.out.println("About updating value. Path: [" + path + "], value: [" + value + "].");

            setResponseHeaders(exchange, HTTP_CONTENT_HTML, HTTP_RESPONSE_OK);
            r = exchange.getResponseBody();
            r.write("Registry is updated.".getBytes(StandardCharsets.UTF_8));
            r.flush();
        } catch (final Exception e) {
            e.printStackTrace();
            throw e;
        }
        finally {
            assert r != null;
            r.close();
        }
    }
}


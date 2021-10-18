package pl.mjaron.jregistry.httpService;

import com.sun.net.httpserver.HttpExchange;
import pl.mjaron.jregistry.core.IProperty;
import pl.mjaron.jregistry.core.IPropertyVisitor;
import pl.mjaron.jregistry.core.JsonTreePropertyVisitor;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * Hosts all properties as a json tree.
 */
public class DataTreeFeature implements IFeature {

    private final IProperty root;

    public DataTreeFeature(final IProperty root) {
        this.root = root;
    }

    @Override
    public String getHttpPath() {
        return "/tree";
    }

    @Override
    public void handle(HttpExchange exchange) throws IOException {
        String requestMethod = exchange.getRequestMethod();
        final String path = exchange.getRequestURI().getPath();
        if (requestMethod.equalsIgnoreCase("GET")) {
            if (path.equalsIgnoreCase(this.getHttpPath())) {
                handleRegistryData(exchange);
            }
        }
    }

    private void handleRegistryData(HttpExchange exchange) throws IOException {
        OutputStream r = null;
        try {
            IFeature.setResponseHeaders(exchange, HTTP_CONTENT_HTML, HTTP_RESPONSE_OK);
            r = exchange.getResponseBody();
            final String jProperties = root.accept(new JsonTreePropertyVisitor()).getJsonString();
            r.write(jProperties.getBytes(StandardCharsets.UTF_8));
            r.flush();
        } finally {
            assert r != null;
            r.close();
        }
    }
}

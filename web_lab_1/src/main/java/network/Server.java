package network;

import Exceptions.FormatException;
import com.fastcgi.FCGIInterface;
import tools.Calculator;
import tools.DataInspector;
import tools.DataParser;

import java.awt.font.NumericShaper;
import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

public class Server {
    private static final String HTTP_RESPONSE = """
            HTTP/1.1 200 OK
            Content-Type: application/json
            Content-Length: %d

            %s
            """;
    private static final String HTTP_ERROR = """
            HTTP/1.1 400 Bad Request
            Content-Type: application/json
            Content-Length: %d

            %s
            """;
    private static final String RESULT_JSON = """
            {
                "good": true,
                "time": "%s",
                "start": "%s",
                "result": %b
            }
            """;
    private static final String ERROR_JSON = """
            {
                "good": false,
                "start": "%s",
                "reason": "%s"
            }
            """;

    private final DataParser parser = new DataParser();
    private final DataInspector inspector = new DataInspector();
    private final Calculator calculator = new Calculator();

    public void start() {
        FCGIInterface fcgi = new FCGIInterface();

        while (fcgi.FCGIaccept() >= 0) {
            Instant start = Instant.now();
            String response;
            String queryString = FCGIInterface.request.params.getProperty("QUERY_STRING");
            Map<String, String> param;

            try {
                param = parser.parse(queryString);
                inspector.inspectData(param);

                BigDecimal x = BigDecimal.valueOf(Double.parseDouble(param.get("x")));
                float y = Float.parseFloat(param.get("y"));
                float r = Float.parseFloat(param.get("r"));
                boolean result = calculator.calculate(x, y, r);

                String time = Instant.now().toString();
                String startStr = start.toString();
                String json = String.format(RESULT_JSON, time, startStr, result);
                response = String.format(HTTP_RESPONSE, json.getBytes(java.nio.charset.StandardCharsets.UTF_8).length, json);
            } catch (FormatException e) {
                String startStr = start.toString();
                String json = String.format(ERROR_JSON, startStr, e.getMessage() + " " + e.getClass().getName());
                response = String.format(HTTP_ERROR, json.getBytes(java.nio.charset.StandardCharsets.UTF_8).length, json);
            } catch (NumberFormatException e) {
                String startStr = start.toString();
                String json = String.format(ERROR_JSON, startStr, "invalid number format");
                response = String.format(HTTP_ERROR, json.getBytes(java.nio.charset.StandardCharsets.UTF_8).length, json);
            }

            System.out.print(response);
        }
    }
}
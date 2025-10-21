package tools;

import java.util.HashMap;
import java.util.Map;

public class DataParser {
    public Map<String, String> parse(String jsonStr) {
        final Map<String, String> params = new HashMap<>();
        final String[] pairs = jsonStr.split("&");
        for (String pair : pairs) {
            String[] keyValue = pair.split("=");

            if (keyValue.length > 1) {
                params.put(keyValue[0], keyValue[1]);
            } else {
                params.put(keyValue[0], "");
            }
        }
        return params;
    }
}

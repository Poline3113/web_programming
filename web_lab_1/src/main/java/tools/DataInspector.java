package tools;

import Exceptions.FormatException;
import java.util.Map;

public class DataInspector {
    public void inspectData(Map<String, String> data) throws FormatException {
        String x = data.get("x");
        String y = data.get("y");
        String r = data.get("r");

        inspectX(x);
        inspectY(y);
        inspectR(r);
    }

    private void inspectX(String x) throws FormatException {

        if (isNumeric(x)) {
            throw new FormatException(String.format("X must be a num, %s", x));
        }
    }

    private void inspectY(String y) throws FormatException {
        if (isNumeric(y)) {
            throw new FormatException("Y must be a num");
        }
    }

    private void inspectR(String r) throws FormatException {
        if (isNumeric(r)) {
            throw new FormatException("R  must be a num");
        }
    }

    public static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        }
        return !str.matches("-?\\d+([.,]\\d+)?");
    }
}

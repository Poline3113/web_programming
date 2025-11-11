package checker;

import exceptions.FormatException;

public class DataInspector {
    public static void inspectData(String x, String y, String r) throws FormatException {
        inspectX(x);
        inspectY(y);
        inspectR(r);
    }

    private static void inspectX(String x) throws FormatException {
        if (isNumeric(x)) {
            throw new FormatException(String.format("X must be a num, %s", x));
        }
    }

    private static void inspectY(String y) throws FormatException {
        if (isNumeric(y)) {
            throw new FormatException(String.format("Y must be a num, %s", y));
        }
    }

    private static void inspectR(String r) throws FormatException {
        if (isNumeric(r)) {
            throw new FormatException(String.format("R must be a num, %s", r));
        }
        float num = Float.parseFloat(r);
        if (num < 1 || num > 3 || num % .5 != 0) {
            throw new FormatException("R not in {1, 1.5, 2, 2.5, 3}");
        }
    }

    private static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        }
        return !str.matches("-?\\d+([.,]\\d+)?");
    }
}

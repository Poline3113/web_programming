package inspector;

import exception.FormatException;

public class DataInspector {
    public static void inspectData(Float x, String y, String r) throws FormatException {
        inspectX(x);
        inspectY(y);
        inspectR(r);
    }

    private static void inspectX(Float x) throws FormatException {
        if (x == null) {
            throw new FormatException("X must cannot be a null");
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
        float rd = Float.parseFloat(r);
        if (rd < 1 || rd > 5 || rd % .5 != 0) {
            throw new FormatException("R not in {1, 2, 3, 4, 5}");
        }
    }

    private static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return true;
        }
        return !str.matches("-?\\d+([.,]\\d+)?");
    }
}

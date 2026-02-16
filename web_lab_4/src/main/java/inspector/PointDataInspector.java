package inspector;

import exception.FormatException;

public class PointDataInspector {
    public static void inspectData(String x, String y, String r) throws FormatException {
        inspectX(x);
        inspectY(y);
        inspectR(r);
    }

    private static void inspectX(String x) throws FormatException {
        if (!isNumeric(x)) {
            throw new FormatException("X must be a num, %s");
        }
    }

    private static void inspectY(String y) throws FormatException {
        if (!isNumeric(y)) {
            throw new FormatException("Y must be a num, %l");
        }
    }

    private static void inspectR(String r) throws FormatException {
        if (!isNumeric(r)) {
            throw new FormatException(String.format("R must be a num, %s", r));
        }
        float rNum = Float.parseFloat(r);
        if (rNum < -4 || rNum > 4 || rNum % .5 != 0) {
            throw new FormatException("R not in {-4,-3,-2,-1,0,1,2,3,4}");
        }
    }

    private static boolean isNumeric(String str) {
        if (str == null || str.isEmpty()) {
            return false;
        }
        return str.matches("-?\\d+([.,]\\d+)?");
    }
}

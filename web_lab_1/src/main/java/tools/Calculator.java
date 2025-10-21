package tools;

import Exceptions.FormatException;

import java.math.BigDecimal;

public class Calculator {
    public boolean calculate(BigDecimal x, float y, float r) throws FormatException {
        if (!(r >= 1 && r <= 3 && r % 0.5 == 0)) {
            throw new FormatException("wrong R");
        }
        if (!(y >= -3 && y <= 5 && y % 1 == 0)) {
            throw new FormatException("wrong Y");
        }
        if (!(x.compareTo(BigDecimal.valueOf(-5)) >= 0 && x.compareTo(BigDecimal.valueOf(5)) <= 0)) {
            throw new FormatException("wrong X");
        }

        if (x.compareTo(BigDecimal.valueOf(0)) <= 0 && y >= 0) {
            return x.compareTo(BigDecimal.valueOf(-r)) >= 0 && y <= r;
        }

        if (x.compareTo(BigDecimal.valueOf(0)) <= 0 && y <= 0) {
            return BigDecimal.valueOf(y).compareTo(x.multiply(BigDecimal.valueOf(-2)).add(BigDecimal.valueOf(-r))) >= 0;
        }

        if (x.compareTo(BigDecimal.valueOf(0)) >= 0 && y <= 0) {
            return BigDecimal.valueOf(Math.pow(r, 2)).compareTo(x.multiply(x).add(BigDecimal.valueOf(Math.pow(y, 2)))) >= 0;
        }

        return false;
    }
}

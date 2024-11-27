package org.banregio.mx.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class PagoCalculator {

    private static final double INTERES = 0.075;
    private static final double IVA = 0.16;
    private static final int DIAS_COMERCIAL = 360;

    public static double calcularInteres(double monto, long plazo) {
        return round(monto * plazo * INTERES / DIAS_COMERCIAL, 2);
    }

    public static double calcularIVA(double interes) {
        return round(interes * IVA, 2);
    }

    public static double calcularPagoTotal(double monto, double interes, double iva) {
        return round(monto + interes + iva, 2);
    }

    private static double round(double value, int places) {
        BigDecimal bd = BigDecimal.valueOf(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}

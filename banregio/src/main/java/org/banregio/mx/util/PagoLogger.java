package org.banregio.mx.util;

import org.banregio.mx.entity.Prestamo;

public class PagoLogger {
    public static void logPagoRealizado(String cliente, Prestamo prestamo, long plazo, double interes, double iva, double pago) {
        System.out.println("Pago realizado: Cliente=" + cliente +
                ", Préstamo ID=" + prestamo.getId() +
                ", Plazo=" + plazo + ", Monto=" + prestamo.getMonto() +
                ", Interés=" + interes + ", IVA=" + iva + ", Pago=" + pago);
    }
}

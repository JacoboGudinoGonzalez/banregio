package org.banregio.mx.service;

import org.banregio.mx.dao.CuentaDebitoDAO;
import org.banregio.mx.dao.PrestamoDAO;
import org.banregio.mx.entity.CuentaDebito;
import org.banregio.mx.entity.Prestamo;
import org.banregio.mx.util.PagoCalculator;
import org.banregio.mx.util.PagoLogger;

import java.sql.Connection;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class PagoService {
    private static final LocalDate FECHA_ACTUAL = LocalDate.of(2021, 2, 15);

    private final CuentaDebitoDAO cuentaDebitoDAO;
    private final PrestamoDAO prestamoDAO;

    public PagoService(Connection connection) {
        this.cuentaDebitoDAO = new CuentaDebitoDAO(connection);
        this.prestamoDAO = new PrestamoDAO(connection);
    }

    public void realizarPagos() throws SQLException {
        List<CuentaDebito> cuentas = cuentaDebitoDAO.obtenerCuentasActivas();

        for (CuentaDebito cuenta : cuentas) {
            double saldoDisponible = cuenta.getMonto();
            List<Prestamo> prestamos = prestamoDAO.obtenerPrestamosPendientes(cuenta.getCliente());

            for (Prestamo prestamo : prestamos) {
                long plazo = ChronoUnit.DAYS.between(prestamo.getFecha(), FECHA_ACTUAL);
                double interes = PagoCalculator.calcularInteres(prestamo.getMonto(), plazo);
                double iva = PagoCalculator.calcularIVA(interes);
                double pago = PagoCalculator.calcularPagoTotal(prestamo.getMonto(), interes, iva);

                if (saldoDisponible >= pago) {
                    prestamoDAO.actualizarEstadoPrestamo(prestamo.getId(), "Pagado");
                    cuentaDebitoDAO.actualizarSaldo(cuenta.getCliente(), saldoDisponible - pago);
                    saldoDisponible -= pago;

                    PagoLogger.logPagoRealizado(cuenta.getCliente(), prestamo, plazo, interes, iva, pago);
                }
            }
        }
    }
}

package org.banregio.mx;

import org.banregio.mx.service.PagoService;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

public class Main {

    private static final String URL = "jdbc:mysql://localhost:3306/banregio";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    public static void main(String[] args) {

            try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
                connection.setAutoCommit(false);

                PagoService pagoService = new PagoService(connection);
                pagoService.realizarPagos();

                connection.commit();
                System.out.println("Todos los pagos se han procesado correctamente.");
            } catch (Exception e) {
                e.printStackTrace();
            }
    }

}

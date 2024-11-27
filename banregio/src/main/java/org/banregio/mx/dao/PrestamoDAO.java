package org.banregio.mx.dao;

import org.banregio.mx.entity.Prestamo;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {
    private final Connection connection;

    public PrestamoDAO(Connection connection) {
        this.connection = connection;
    }

    public List<Prestamo> obtenerPrestamosPendientes(String cliente) throws SQLException {
        String query = "SELECT Id, Fecha, Monto, Estado FROM Prestamos WHERE Cliente = ? AND Estado = 'Pendiente' ORDER BY Fecha ASC";
        List<Prestamo> prestamos = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, cliente);

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    prestamos.add(new Prestamo(
                            rs.getInt("Id"),
                            cliente,
                            rs.getDate("Fecha").toLocalDate(),
                            rs.getDouble("Monto"),
                            rs.getString("Estado")
                    ));
                }
            }
        }
        return prestamos;
    }

    public void actualizarEstadoPrestamo(int prestamoId, String nuevoEstado) throws SQLException {
        String query = "UPDATE Prestamos SET Estado = ? WHERE Id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setString(1, nuevoEstado);
            stmt.setInt(2, prestamoId);
            stmt.executeUpdate();
        }
    }
}

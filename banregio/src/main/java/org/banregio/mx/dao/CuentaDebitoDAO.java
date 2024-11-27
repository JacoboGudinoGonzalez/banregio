package org.banregio.mx.dao;

import org.banregio.mx.entity.CuentaDebito;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CuentaDebitoDAO {
    private final Connection connection;

    public CuentaDebitoDAO(Connection connection) {
        this.connection = connection;
    }

    public List<CuentaDebito> obtenerCuentasActivas() throws SQLException {
        String query = "SELECT Cliente, Monto, Estado FROM CuentasDebito WHERE Estado = 'Activa'";
        List<CuentaDebito> cuentas = new ArrayList<>();

        try (PreparedStatement stmt = connection.prepareStatement(query);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                cuentas.add(new CuentaDebito(
                        rs.getString("Cliente"),
                        rs.getDouble("Monto"),
                        rs.getString("Estado")
                ));
            }
        }
        return cuentas;
    }

    public void actualizarSaldo(String cliente, double nuevoSaldo) throws SQLException {
        String query = "UPDATE CuentasDebito SET Monto = ? WHERE Cliente = ?";
        try (PreparedStatement stmt = connection.prepareStatement(query)) {
            stmt.setDouble(1, nuevoSaldo);
            stmt.setString(2, cliente);
            stmt.executeUpdate();
        }
    }
}


# unagraBack

Author(s): - Jacobo Gudiño

[GitHub](https://github.com/JacoboGudinoGonzalez/banregio.git)

Last updated: 2024-11-26

## Descripción del Proyecto

Esta aplicación implementa un sistema de gestión de pagos de préstamos para clientes, utilizando una arquitectura basada en capas (DAO, Service y Model). La lógica calcula el monto de los pagos pendientes, considerando el interés y el IVA, y actualiza las cuentas y los préstamos en una base de datos MySQL.

La aplicación:

Calcula los pagos pendientes de los clientes.
Realiza el cobro solo si el saldo disponible es suficiente.
Actualiza el estado de los préstamos a "Pagado".
Reduce el saldo disponible en las cuentas de débito.
Muestra los pagos realizados y el estado actualizado de las cuentas.

## Tecnologías Utilizadas

    Backend
Java 17 (compatible con Lombok)
JDBC para la conexión con la base de datos MySQL.
Lombok para reducir código repetitivo (getters, setters, etc.).

    Base de Datos
MySQL para almacenar los datos de préstamos y cuentas.

    Estructura del Proyecto

El proyecto está organizado en tres capas principales:

- Model: Representa las entidades de negocio (CuentaDebito, Prestamo).
- DAO: Gestiona las operaciones de acceso a datos para las entidades.
- Service: Implementa la lógica de negocio (PagoService).
- Utilidades: Componentes auxiliares para cálculos y logging (PagoCalculator, PagoLogger).


## Requisitos Previos
1. Configuración del Entorno
   JDK 8 o superior.
   MySQL instalado y configurado.
   IDE compatible con Lombok (por ejemplo, IntelliJ IDEA o Eclipse).
   Maven para gestionar las dependencias.

2. Configuración de Base de Datos
   Crea la base de datos banregio y las tablas necesarias con los siguientes scripts:
   
    CREATE DATABASE banregio;
    USE banregio;

3. Crear la Tabla CuentasDebito
   CREATE TABLE CuentasDebito (
   Cliente VARCHAR(20) NOT NULL PRIMARY KEY,
   Monto DECIMAL(15, 2) NOT NULL,
   Estado ENUM('Activa', 'Bloqueada', 'Cancelada') NOT NULL
   );
4. Crear la Tabla Prestamos
   CREATE TABLE Prestamos (
   Id INT AUTO_INCREMENT PRIMARY KEY,
   Cliente VARCHAR(20) NOT NULL,
   Fecha DATE NOT NULL,
   Monto DECIMAL(15, 2) NOT NULL,
   Estado ENUM('Pendiente de Pago', 'Pagado') NOT NULL,
   FOREIGN KEY (Cliente) REFERENCES CuentasDebito(Cliente)
   );
5. Insertar Datos de Ejemplo
   -- Datos de CuentasDebito
   INSERT INTO CuentasDebito (Cliente, Monto, Estado)
   VALUES
   ('00103228', 15375.28, 'Activa'),
   ('70099925', 3728.51, 'Bloqueada'),
   ('00298185', 0.00, 'Cancelada'),
   ('15000125', 235.28, 'Activa');

    -- Datos de Prestamos
    INSERT INTO Prestamos (Cliente, Fecha, Monto, Estado)
    VALUES
    ('00103228', '2021-01-10', 37500.00, 'Pendiente de Pago'),
    ('00103228', '2021-01-19', 725.18, 'Pendiente de Pago'),
    ('00103228', '2021-01-31', 1578.22, 'Pendiente de Pago'),
    ('00103228', '2021-02-04', 380.00, 'Pendiente de Pago'),
    ('70099925', '2021-01-07', 2175.25, 'Pagado'),
    ('70099925', '2021-01-13', 499.99, 'Pagado'),
    ('70099925', '2021-01-24', 5725.18, 'Pendiente de Pago'),
    ('70099925', '2021-02-07', 876.13, 'Pendiente de Pago'),
    ('00298185', '2021-02-04', 545.55, 'Pendiente de Pago'),
    ('15000125', '2020-12-31', 15220.00, 'Pagado');
6. git clone https://github.com/JacoboGudinoGonzalez/banregio.git
   cd banregio
7. private static final String URL = "jdbc:mysql://localhost:3306/banregio";
   private static final String USER = "root";
   private static final String PASSWORD = "admin";

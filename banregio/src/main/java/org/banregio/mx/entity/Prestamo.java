package org.banregio.mx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Prestamo {
    private int id;
    private String cliente;
    private LocalDate fecha;
    private double monto;
    private String estado;
}
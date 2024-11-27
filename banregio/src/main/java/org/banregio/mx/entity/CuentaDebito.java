package org.banregio.mx.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CuentaDebito {
    private String cliente;
    private double monto;
    private String estado;

}

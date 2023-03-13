package com.example.demo.Entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name="DatosMP")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class MercadoPagoDatos extends Base{
    private Long idPago;
    private Date fechaCreacion;
    private Date fechaAprobacion;
    //TODO Investigar las formas de pago y englobarlas en un ENUM
    private String formaDePago;
    //TODO Redundante?
    private String metodoDePago;
    private String nroTarjeta;
    private String estado;
}

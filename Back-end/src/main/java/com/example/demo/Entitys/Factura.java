package com.example.demo.Entitys;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Factura extends Base{
    private Date fecha;
    private int numero;
    private Double montoDescuento;
    //TODO Investigar formas de pago y ponerlas en un enum
    private String formaDePago;
    private String nroTarjeta;
    private Double totalVenta;
    private Double totalCosto;
    //TODO Revisar esta relacion no me parece que las facturas solo tengan un pedido en su total
    @OneToOne
    @JoinColumn(name = "pedido_list_id")
    private Pedido pedidoList;
}

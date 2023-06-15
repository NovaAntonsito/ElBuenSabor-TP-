package com.example.demo.Entitys;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Pedido")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Pedido extends Base{
    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "id_usuario_fk")
    private Usuario usuarioPedido;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_direccion_fk")
    private Direccion direccionPedido;
    @ManyToOne
    @JoinColumn
    private Carrito carritoComprado;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_estado_fk")
    private Estado estado;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_MP_datos_fk", nullable = true)
    private MP_Datos MercadoPagoDatos;
    private Double total;
}

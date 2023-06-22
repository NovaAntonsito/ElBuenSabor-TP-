package com.example.demo.Entitys;

import com.example.demo.Entitys.Enum.EstadoPedido;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "Pedido")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Pedido extends Base{
    @OneToOne
    @JoinColumn(name = "id_usuario_fk")
    private Usuario usuarioPedido;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_direccion_fk")
    private Direccion direccionPedido;
    @ManyToOne
    @JoinColumn
    private Carrito carritoComprado;

    private EstadoPedido estado;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_MP_datos_fk", nullable = true)
    private MP_Datos MercadoPagoDatos;

    private Date fechaInicio;

    private Date fechaFinal;

    private Double total;
}

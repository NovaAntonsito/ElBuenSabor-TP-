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
    Usuario usuarioPedido;
    Double total;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_direccion_fk")
    Direccion direccionPedido;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_estado_fk")
    Estado estado;
    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_MP_datos_fk")
    MP_Datos MercadoPagoDatos;
}

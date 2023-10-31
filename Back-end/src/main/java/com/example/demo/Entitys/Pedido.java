package com.example.demo.Entitys;

import com.example.demo.Config.WebSocketEventListener;
import com.example.demo.Controllers.DTOS.InsumoCarritoDTO;
import com.example.demo.Controllers.DTOS.ProductosCarritoDTO;
import com.example.demo.Entitys.Enum.EstadoMP;
import com.example.demo.Entitys.Enum.EstadoPedido;
import jakarta.persistence.*;
import lombok.*;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "Pedido")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Slf4j
@ToString
public class Pedido extends Base {
    @OneToOne
    @JoinColumn(name = "id_usuario_fk")
    private Usuario usuarioPedido;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_direccion_fk")
    private Direccion direccionPedido;

    @ManyToMany
    @JoinTable(name = "pedido_producto",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id"))
    private List<Producto> productosManufacturados;


    @ManyToMany
    @JoinTable(name = "pedido_productoAdicional",
            joinColumns = @JoinColumn(name = "pedido_id"),
            inverseJoinColumns = @JoinColumn(name = "insumo_id"))
    private List<Insumo> productosAdicionales;



    private EstadoMP estadoMP;

    private EstadoPedido estado;

    private Boolean esDelivery;

    private Boolean esMercadoPago;

    private Date fechaInicio;

    private Date fechaFinal;

    private Double costoEnvio;
    private Double descuentoTotal;
    private Double total;


    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
        try {
            WebSocketEventListener.notifyEstadoPedidoChange(this);
        } catch (IOException e) {
            log.info(e.getMessage());
        }
    }
}

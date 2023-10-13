package com.example.demo.Controllers.DTOS;

import com.example.demo.Entitys.*;
import com.example.demo.Entitys.Enum.EstadoPedido;
import com.example.demo.Services.DireccionService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DetallesCompra {

    private DireccionService direccionService;

    private EstadoPedido estado;

    private Boolean esDelivery;

    private long direccionId = -1;

    private Boolean esMercadoPago;

    public Pedido toEntity(DetallesCompra detallesCompra, Usuario userFound, Carrito carrito, Double totalCompra) throws Exception {
        Pedido newPedido = new Pedido();
        if (detallesCompra.getDireccionId() != -1){
            newPedido.setDireccionPedido(direccionService.getOneDireccion(detallesCompra.getDireccionId()));
        }
        newPedido.setUsuarioPedido(userFound);

        newPedido.setEstado(detallesCompra.getEstado());
        newPedido.setCarritoComprado(carrito);
        newPedido.setFechaInicio(new Date());
        newPedido.setEsDelivery(detallesCompra.getEsDelivery());
        newPedido.setEsMercadoPago(detallesCompra.esMercadoPago);
        if (detallesCompra.esDelivery){
            totalCompra += 500;
        }
        if (!detallesCompra.esMercadoPago) {
            totalCompra -= totalCompra*0.15;
        }

        newPedido.setTotal(totalCompra);
        return newPedido;
    }
}

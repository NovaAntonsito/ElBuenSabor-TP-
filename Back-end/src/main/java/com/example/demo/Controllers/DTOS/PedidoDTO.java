package com.example.demo.Controllers.DTOS;

import com.example.demo.Entitys.*;
import com.example.demo.Entitys.Enum.EstadoPedido;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PedidoDTO {

    private EstadoPedido estado;

    private Boolean esDelivery;

    private Direccion direccion;

    private Boolean esMercadoPago;

    public Pedido toEntity(PedidoDTO pedidoDTO, Usuario userFound, Carrito cartFound){
        Pedido newPedido = new Pedido();
        Double precioTotal = 0.0;
        newPedido.setDireccionPedido(pedidoDTO.getDireccion());
        newPedido.setUsuarioPedido(userFound);
        for(Producto producto : cartFound.getProductosComprados()){
            precioTotal += producto.getPrecioUnitario();
            for (Insumo insumo : cartFound.getProductosAdicionales()) {
                precioTotal += insumo.getCosto();
            }
        }
        newPedido.setEstado(pedidoDTO.getEstado());
        newPedido.setCarritoComprado(cartFound);
        newPedido.setFechaInicio(new Date());

        newPedido.setTotal(precioTotal * (1 + 0.3));
        return newPedido;
    }
}

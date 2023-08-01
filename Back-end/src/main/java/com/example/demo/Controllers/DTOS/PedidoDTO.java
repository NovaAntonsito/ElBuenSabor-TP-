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

    private List<ProductosCarritoDTO> productosComprados;



    private MP_Datos MercadoPagoDatos;



    public Pedido toEntity(PedidoDTO pedidoDTO, Usuario userFound, Carrito cartFound){
        Pedido newPedido = new Pedido();
        Double precioTotal = 0.0;
        newPedido.setDireccionPedido(userFound.getDireccionList().get(0));
        newPedido.setUsuarioPedido(userFound);
        newPedido.setMercadoPagoDatos(pedidoDTO.getMercadoPagoDatos());
        for (Producto prods: cartFound.getProductosComprados()) {
            ProductosCarritoDTO newDTO = new ProductosCarritoDTO();
            newDTO.setProducto(prods.getNombre());
            for (ProductoInsumos insumos : prods.getInsumos()) {
                precioTotal += insumos.getInsumo().getCosto();
            }
        }
        newPedido.setEstado(pedidoDTO.getEstado());
        newPedido.setCarritoComprado(cartFound);
        newPedido.setFechaInicio(new Date());
        newPedido.setTotal(precioTotal * (1 + 0.3));
        return newPedido;
    }
}


package com.example.demo.Controllers.DTOS;

        import com.example.demo.Entitys.Carrito;
        import com.example.demo.Entitys.Direccion;
        import com.example.demo.Entitys.Enum.EstadoPedido;
        import lombok.AllArgsConstructor;
        import lombok.Getter;
        import lombok.NoArgsConstructor;
        import lombok.Setter;

        import java.util.Date;
        import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class PedidoDTO {
    private long  pedidoID;
    private CarritoDTO carritoDTO;
    private Direccion direccionPedido;
    private EstadoPedido estado;

    private Boolean esDelivery;

    private Boolean esMercadoPago;
    private Double costeEnvio;
    private Double descuentoAplicado;
    private Double total;
    private Date fechaInicio;

}
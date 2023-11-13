package com.example.demo.Controllers.DTOS;

import com.example.demo.Entitys.Carrito;
import com.example.demo.Entitys.Insumo;
import com.example.demo.Entitys.Producto;
import com.example.demo.Services.InsumoService;
import com.example.demo.Services.ProductoService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarritoDTO {
    private List<ProductosCarritoDTO> productosManufacturados;
    private List<InsumoCarritoDTO> productosAgregados;
    private Double totalCompra;

    public Carrito toCarrito (ProductoService productoService, InsumoService insumoService) throws Exception {
         Carrito newCarrito = new Carrito();
        List<Producto> productosComprados = new ArrayList<>();
        List<Insumo> productosAdicionales = new ArrayList<>();
        for (ProductosCarritoDTO productosCarritoDTO:this.productosManufacturados) {
            var productoFound = productoService.findbyID(productosCarritoDTO.getProductoId());
            if (productoFound != null){
                for (int i = 0; i < productosCarritoDTO.getCantidad(); i++) {
                    productosComprados.add(productoFound);
                }
            }
        }
        for (InsumoCarritoDTO insumoCarritoDTO:this.productosAgregados) {
            var productoFound = insumoService.findByID(insumoCarritoDTO.getId());
            if (productoFound != null){
                for (int i = 0; i < insumoCarritoDTO.getCantidad(); i++) {
                    productosAdicionales.add(productoFound);
                }
            }
        }
        newCarrito.setProductosComprados(productosComprados);
        newCarrito.setProductosAdicionales(productosAdicionales);
     return newCarrito;
    }
}

package com.example.demo.Controllers.DTOS;

import com.example.demo.Entitys.Insumo;
import com.example.demo.Entitys.Producto;
import com.example.demo.Entitys.ProductoInsumos;
import com.example.demo.Services.ConfigLocalService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class ProductosCarritoDTO {

    private String producto;
    private Long productoId;
    private Long cantidad;
    private Double precioUnitario;
    private Double precioTotalSinDescuento;
    private Double precioTotal;
    private Long descuento;
    private String imgURL;
    private Long tiempoCocina;






    public List<ProductosCarritoDTO> toDTO(List<Producto> productosComprados) {
        List<ProductosCarritoDTO> productosCarritoDTOList = new ArrayList<>();

        for (Producto producto : productosComprados) {
            // Verificar si el producto ya existe en la lista
            boolean existeProducto = false;

            for (ProductosCarritoDTO dto : productosCarritoDTOList) {
                if (dto.getProductoId().equals(producto.getID())) {
                    // El producto ya existe en la lista, aumentar la cantidad y actualizar el precio total
                    dto.setCantidad(dto.getCantidad() + 1);
                    existeProducto = true;
                    break;
                }
            }

            if (!existeProducto) {
                // Agregar el producto a la lista si no existe
                ProductosCarritoDTO nuevoProducto = new ProductosCarritoDTO();
                nuevoProducto.setProducto(producto.getNombre());
                nuevoProducto.setCantidad(1L);
                nuevoProducto.setDescuento(producto.getDescuento());
                double precioUnitario =  0;
                for(ProductoInsumos insumos : producto.getInsumos()){
                    precioUnitario += insumos.getInsumo().getCosto();
                }
                nuevoProducto.setPrecioUnitario(precioUnitario);
                nuevoProducto.setTiempoCocina(producto.getTiempoCocina());
                nuevoProducto.setPrecioTotal(precioUnitario);
                nuevoProducto.setImgURL(producto.getImgURL());
                nuevoProducto.setProductoId(producto.getID());
                productosCarritoDTOList.add(nuevoProducto);
            }
        }
        return productosCarritoDTOList;
    }


}

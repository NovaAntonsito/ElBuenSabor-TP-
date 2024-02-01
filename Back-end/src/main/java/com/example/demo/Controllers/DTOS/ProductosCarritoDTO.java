package com.example.demo.Controllers.DTOS;

import com.example.demo.Entitys.Producto;
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

    private String nombre;
    private Long productoId;
    private Long cantidad;
    private Double precioUnitario;
    private Double precioTotalSinDescuento;
    private Double precioTotal;
    private Long descuento;
    private String urlIMG;
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
                nuevoProducto.setNombre(producto.getNombre());
                nuevoProducto.setCantidad(1L);
                nuevoProducto.setDescuento(producto.getDescuento());
                nuevoProducto.setPrecioUnitario(producto.getPrecioUnitario());
                nuevoProducto.setTiempoCocina(producto.getTiempoCocina());
                nuevoProducto.setPrecioTotal(producto.getPrecioUnitario());
                nuevoProducto.setUrlIMG(producto.getImgURL());
                nuevoProducto.setProductoId(producto.getID());
                productosCarritoDTOList.add(nuevoProducto);
            }
        }

        for (ProductosCarritoDTO productosCarritoDTO : productosCarritoDTOList){

            Double precioTotalProducto = productosCarritoDTO.getPrecioTotal();
            Double precioTotalSinDescuento = productosCarritoDTO.getPrecioTotal();

            precioTotalProducto *= productosCarritoDTO.getCantidad();
            productosCarritoDTO.setPrecioTotalSinDescuento(precioTotalSinDescuento * productosCarritoDTO.getCantidad());

            // Supongamos que tienes un valor productoDescuento (long) que contiene el descuento en un rango de 0 a 100.
            long productoDescuento = productosCarritoDTO.getDescuento();

            // Dividimos el valor de productoDescuento entre 100 para obtener la fracción.
            double productoDividido =  (double) productoDescuento / 100.0;
            productoDividido = Math.round(productoDividido);

            // Calculamos el descuento aplicando la fracción productoDividido al precioTotal.
            precioTotalProducto *= (1 - productoDividido);

            productosCarritoDTO.setPrecioTotal(precioTotalProducto);
        }
        return productosCarritoDTOList;
    }


}

package com.example.demo.Controllers.DTOS;


import com.example.demo.Entitys.Categoria;
import com.example.demo.Entitys.Enum.Baja_Alta;

import com.example.demo.Entitys.Producto;
import com.example.demo.Services.CatergoriaService;
import com.example.demo.Services.ProductoService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductoDTO {

    Long id;
    String nombre;
    String descripcion;
    Long tiempoCocina;
    String receta;
    Baja_Alta alta;
    Long productoCategoria;



    public ProductoDTO toDTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getID());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setReceta(producto.getReceta());
        dto.setAlta(producto.getAlta());
        if(producto.getProductoCategoria().getID() != null){
            dto.setProductoCategoria(producto.getProductoCategoria().getID());
        }
        return dto;
    }
    public Producto toEntity(ProductoDTO dto, Categoria categoria) {
        Producto producto = new Producto();
        producto.setID(dto.getId());
        producto.setNombre(dto.getNombre());
        producto.setDescripcion(dto.getDescripcion());
        producto.setTiempoCocina(dto.getTiempoCocina());
        producto.setReceta(dto.getReceta());
        producto.setReceta(producto.getReceta());
        if(dto.getProductoCategoria() != null){
            producto.setProductoCategoria(categoria);
        }
        return producto;
    }


}

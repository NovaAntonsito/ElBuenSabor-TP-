package com.example.demo.Controllers.DTOS;


import com.example.demo.Entitys.Categoria;
import com.example.demo.Entitys.Enum.Baja_Alta;

import com.example.demo.Entitys.Producto;
import com.example.demo.Entitys.ProductoInsumos;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


import java.util.ArrayList;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Slf4j
public class  ProductoDTO {

    private Long id;
    private String nombre;
    private String imgURL;
    private String descripcion;
    private Long tiempoCocina;
    private String receta;
    private Baja_Alta estado;
    private Long productoCategoria;
    private Double precio;
    private Double valoracion;
    private Long descuento;
    private List<ProductoInsumosDTO> insumos;


    public ProductoDTO toDTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getID());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setReceta(producto.getReceta());
        dto.setEstado(producto.getAlta());
        dto.setImgURL(producto.getImgURL());
        dto.setTiempoCocina(producto.getTiempoCocina());
        dto.setDescuento(producto.getDescuento());
        dto.setValoracion(producto.getValoracion());
        if(producto.getProductoCategoria().getID() != null){
            dto.setProductoCategoria(producto.getProductoCategoria().getID());
        }
        dto.setPrecio(producto.getPrecioUnitario());
        List<ProductoInsumosDTO> productoInsumosDTOList = new ArrayList<>();
        for (var pI : producto.getInsumos()){
            productoInsumosDTOList.add(new ProductoInsumosDTO(pI.getID(),pI.getCantidad()));
        }
        dto.setInsumos(productoInsumosDTOList);
        return dto;
    }
    public Producto toEntity(ProductoDTO dto, Categoria categoria, List<ProductoInsumos> insumos, String URL) {
        Producto producto = new Producto();
        producto.setID(dto.getId());
        producto.setNombre(dto.getNombre());
        producto.setImgURL(URL);
        producto.setDescripcion(dto.getDescripcion());
        producto.setTiempoCocina(dto.getTiempoCocina());
        producto.setReceta(dto.getReceta());
        producto.setAlta(dto.getEstado());
        producto.setDescuento(dto.getDescuento());
        producto.setValoracion(dto.getValoracion());
        producto.setPrecioUnitario(dto.getPrecio());
        producto.setDescuento(dto.getDescuento());
        if(dto.getProductoCategoria() != null){
            producto.setProductoCategoria(categoria);
        }
        producto.setInsumos(insumos);
        return producto;
    }


}

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
public class ProductoDTO {

    private Long id;
    private String nombre;
    private String imgURL;
    private String descripcion;
    private Long tiempoCocina;
    private String receta;
    private Baja_Alta estado;
    private CategoriaDTO productoCategoria;
    private Double precio;
    private Double valoracion;
    private Long descuento;
    private List<ProductoInsumosDTO> insumos;

    public static ProductoDTO toDTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        dto.setId(producto.getID());
        dto.setNombre(producto.getNombre());
        dto.setDescripcion(producto.getDescripcion());
        dto.setReceta(producto.getReceta());
        dto.setEstado(producto.getEstado());
        dto.setImgURL(producto.getImgURL());
        dto.setTiempoCocina(producto.getTiempoCocina());
        dto.setDescuento(producto.getDescuento());
        dto.setValoracion(producto.getValoracion());
        if (producto.getProductoCategoria().getID() != null) {
            dto.setProductoCategoria(CategoriaDTO.toDTO(producto.getProductoCategoria()));
        }
        dto.setPrecio(producto.getPrecioUnitario());
        List<ProductoInsumosDTO> productoInsumosDTOList = new ArrayList<>();
        for (var pI : producto.getInsumos()) {
            productoInsumosDTOList.add(new ProductoInsumosDTO(pI.getInsumo().getID(), pI.getInsumo().getNombre(), pI.getCantidad()));
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
        producto.setEstado(dto.getEstado());
        producto.setDescuento(dto.getDescuento());
        producto.setValoracion(dto.getValoracion());
        producto.setPrecioUnitario(dto.getPrecio());
        producto.setDescuento(dto.getDescuento());
        if (dto.getProductoCategoria() != null) {
            producto.setProductoCategoria(categoria);
        }
        producto.setInsumos(insumos);
        return producto;
    }


}

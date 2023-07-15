package com.example.demo.Controllers.DTOS;


import com.example.demo.Entitys.Categoria;
import com.example.demo.Entitys.Enum.Baja_Alta;

import com.example.demo.Entitys.Insumo;
import com.example.demo.Entitys.Producto;
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
public class ProductoDTO {

    private Long id;
    private String nombre;
    private String imgURL;
    private String descripcion;
    private Long tiempoCocina;
    private String receta;
    private Baja_Alta estado;
    private Long productoCategoria;
    private List<Long> insumosIDS;
    private Double precio;
    private Double valoracion;
    private Long descuento;


    public ProductoDTO toDTO(Producto producto) {
        ProductoDTO dto = new ProductoDTO();
        List<Long> prodsID = new ArrayList<>();
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
        Double precio =  (double)0;
        for(Insumo insumo : producto.getInsumoSet()){
            prodsID.add(insumo.getID());
            precio += insumo.getCosto();
        }
        dto.setPrecio(precio);
        dto.setInsumosIDS(prodsID);
        return dto;
    }
    public Producto toEntity(ProductoDTO dto, Categoria categoria, List<Insumo> insumoList,String URL) {
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
        if(dto.getProductoCategoria() != null){
            producto.setProductoCategoria(categoria);
        }
        producto.setInsumoSet(insumoList);
        return producto;
    }


}

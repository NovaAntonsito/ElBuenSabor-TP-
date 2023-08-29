package com.example.demo.Controllers.DTOS;

import com.example.demo.Entitys.Insumo;
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
public class InsumoCarritoDTO {
    private Long id;
    private String nombre;
    private Long cantidad;
    private Double precioUnitario;
    private Double precioTotal;
    private String urlImg;
    public List<InsumoCarritoDTO> toDTO (List<Insumo> productosAgregados){
        List<InsumoCarritoDTO> dto = new ArrayList<>();
        for (Insumo insumo : productosAgregados){
            boolean existeProducto = false;
            for(InsumoCarritoDTO insumoCarritoDTO : dto){
                if(insumoCarritoDTO.getId().equals(insumo.getID())){
                    insumoCarritoDTO.setCantidad(insumoCarritoDTO.getCantidad() + 1);
                    insumoCarritoDTO.setPrecioUnitario(insumo.getCosto());
                    insumoCarritoDTO.setPrecioTotal(insumo.getCosto() * insumoCarritoDTO.getCantidad() );
                    existeProducto = true;
                    break;
                }
            }
            if(!existeProducto){
                InsumoCarritoDTO newProductoAgregado = new InsumoCarritoDTO();
                newProductoAgregado.setNombre(insumo.getNombre());
                newProductoAgregado.setCantidad(1L);
                newProductoAgregado.setPrecioUnitario(insumo.getCosto());
                newProductoAgregado.setPrecioTotal(insumo.getCosto());
                newProductoAgregado.setId(insumo.getID());
                newProductoAgregado.setUrlImg(insumo.getUrlIMG());
                dto.add(newProductoAgregado);
            }
        }
        return dto;
    }

}

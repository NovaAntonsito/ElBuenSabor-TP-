package com.example.demo.Controllers.DTOS;

import com.example.demo.Entitys.Enum.Baja_Alta;
import com.example.demo.Entitys.Insumo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class InsumosDTO {
    private String nombre;
    private String imagen;
    private Double stockMinimo;
    private Double stockActual;
    private Baja_Alta estado;
    private Double costo;


    public InsumosDTO toDTO (Insumo insumo){
        InsumosDTO newDTO = new InsumosDTO();
        newDTO.setNombre(insumo.getNombre());
        newDTO.setImagen(insumo.getImagen());
        newDTO.setStockActual(insumo.getStockActual());
        newDTO.setStockMinimo(insumo.getStockMinimo());
        newDTO.setEstado(insumo.getAlta());
        newDTO.setCosto(insumo.getCosto());
        return newDTO;
    }

    public Insumo toEntity (InsumosDTO dto){
        Insumo newInsumo = new Insumo();
        newInsumo.setNombre(dto.getNombre());
        newInsumo.setImagen(dto.getImagen());
        newInsumo.setStockMinimo(dto.getStockMinimo());
        newInsumo.setStockActual(dto.getStockActual());
        newInsumo.setAlta(dto.getEstado());
        newInsumo.setCosto(dto.getCosto());

        return newInsumo;
    }
}

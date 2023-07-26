package com.example.demo.Controllers.DTOS;

import com.example.demo.Entitys.Categoria;
import com.example.demo.Entitys.Enum.Baja_Alta;
import com.example.demo.Entitys.Insumo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Slf4j
public class InsumosDTO {
    private Long ID;
    private String nombre;
    private Double stockMinimo;
    private Double stockActual;
    private Baja_Alta estado;
    private Double costo;
    private CategoriaDTO categoria;


    public InsumosDTO toDTO (Insumo insumo){
        InsumosDTO newDTO = new InsumosDTO();
        newDTO.setID(insumo.getID());
        newDTO.setNombre(insumo.getNombre());
        newDTO.setStockActual(insumo.getStockActual());
        newDTO.setStockMinimo(insumo.getStockMinimo());
        newDTO.setEstado(insumo.getEstado());
        newDTO.setCosto(insumo.getCosto());
        newDTO.setCategoria(CategoriaDTO.toDTO(insumo.getCategoria()));
        return newDTO;
    }

    public Insumo toEntity (InsumosDTO dto, Categoria categoria){
        Insumo newInsumo = new Insumo();
        newInsumo.setNombre(dto.getNombre());
        newInsumo.setStockMinimo(dto.getStockMinimo());
        newInsumo.setStockActual(dto.getStockActual());
        newInsumo.setEstado(dto.getEstado());
        newInsumo.setCosto(dto.getCosto());
        newInsumo.setCategoria(categoria);
        return newInsumo;
    }
}

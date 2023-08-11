package com.example.demo.Controllers.DTOS;

import com.example.demo.Entitys.Categoria;
import com.example.demo.Entitys.Enum.Baja_Alta;
import com.example.demo.Entitys.Insumo;
import com.example.demo.Entitys.UnidadMedida;
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
    private Double stock_minimo;
    private Double stock_actual;
    private Baja_Alta estado;
    private Double costo;
    private CategoriaDTO categoria;
    private UnidadMedida unidad_medida;
    private Boolean es_complemento;


    public InsumosDTO toDTO (Insumo insumo){
        InsumosDTO newDTO = new InsumosDTO();
        newDTO.setID(insumo.getID());
        newDTO.setNombre(insumo.getNombre());
        newDTO.setStock_actual(insumo.getStock_actual());
        newDTO.setStock_minimo(insumo.getStock_minimo());
        newDTO.setEstado(insumo.getEstado());
        newDTO.setCosto(insumo.getCosto());
        newDTO.setCategoria(CategoriaDTO.toDTO(insumo.getCategoria()));
        newDTO.setUnidad_medida(insumo.getUnidad_medida());
        newDTO.setEs_complemento(insumo.getEs_complemento());
        return newDTO;
    }

    public Insumo toEntity (InsumosDTO dto){
        Insumo newInsumo = new Insumo();
        newInsumo.setNombre(dto.getNombre());
        newInsumo.setStock_minimo(dto.getStock_minimo());
        newInsumo.setStock_actual(dto.getStock_actual());
        newInsumo.setEstado(dto.getEstado());
        newInsumo.setCosto(dto.getCosto());
        if (dto.getCategoria() != null) {
            Categoria categoria = new Categoria();
            categoria.setID(dto.getCategoria().getId());
            newInsumo.setCategoria(categoria);
        }
        newInsumo.setUnidad_medida(dto.getUnidad_medida());
        newInsumo.setEs_complemento(dto.getEs_complemento());
        return newInsumo;
    }
}

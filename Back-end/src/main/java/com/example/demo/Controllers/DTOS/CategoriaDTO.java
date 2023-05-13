package com.example.demo.Controllers.DTOS;

import com.example.demo.Entitys.Categoria;
import com.example.demo.Entitys.Enum.Baja_Alta;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CategoriaDTO {
    private Long id;
    private String nombre;
    private Baja_Alta estado;
    private Long categoriaPadre;

    public static CategoriaDTO toDTO(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setNombre(categoria.getNombre());
        dto.setEstado(categoria.getAlta());
        dto.setId(categoria.getID());
        if (categoria.getCategoriaPadre() != null) {
            dto.setCategoriaPadre(categoria.getCategoriaPadre().getID());
        }
        return dto;
    }
    public Categoria toEntity(CategoriaDTO dto, Categoria categoriaPadre) {
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        categoria.setAlta(dto.getEstado());
        if (dto.getCategoriaPadre() != null) {
            categoria.setCategoriaPadre(categoriaPadre);
        }
        return categoria;
    }
}

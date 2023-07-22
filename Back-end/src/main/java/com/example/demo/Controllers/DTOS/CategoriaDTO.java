package com.example.demo.Controllers.DTOS;

import com.example.demo.Entitys.Categoria;
import com.example.demo.Entitys.Enum.Baja_Alta;
import com.example.demo.Entitys.Enum.TipoCategoria;
import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Data
public class CategoriaDTO {
    private Long id;
    private String nombre;
    private Baja_Alta estado;
    private TipoCategoria tipo;
    private Categoria categoriaPadre;
    private List<Categoria> subCategoria;

    public static CategoriaDTO toDTO(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setNombre(categoria.getNombre());
        dto.setEstado(categoria.getEstado());
        dto.setTipo(categoria.getTipo());
        dto.setId(categoria.getID());
        dto.setSubCategoria(categoria.getSubCategoria());
        if (categoria.getCategoriaPadre() != null) {
            dto.setCategoriaPadre(categoria.getCategoriaPadre());
        }
        /*if (categoria.getCategoriaPadre() != null) {
            dto.setCategoriaPadre(categoria.getCategoriaPadre().getID());
        }*/

        return dto;
    }
    public Categoria toEntity(CategoriaDTO dto/*, Categoria categoriaPadre*/) {
        Categoria categoria = new Categoria();
        categoria.setNombre(dto.getNombre());
        categoria.setEstado(dto.getEstado());
        categoria.setTipo(dto.getTipo());
        if (dto.getCategoriaPadre() != null) {
            categoria.setCategoriaPadre(categoriaPadre);
        }
        return categoria;
    }

    @Override
    public String toString() {
        return "CategoriaDTO{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", estado=" + estado +
                ", tipo=" + tipo +
                ", categoriaPadre=" + categoriaPadre +
                ", subCategoria=" + subCategoria +
                '}';
    }
}
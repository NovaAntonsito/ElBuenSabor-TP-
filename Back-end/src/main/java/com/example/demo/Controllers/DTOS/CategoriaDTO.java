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
    private CategoriaNestedDTO categoriaPadre; // Use a nested DTO for categoriaPadre
    private List<CategoriaNestedDTO> subCategoria;

    public static CategoriaDTO toDTO(Categoria categoria) {
        CategoriaDTO dto = new CategoriaDTO();
        dto.setNombre(categoria.getNombre());
        dto.setEstado(categoria.getEstado());
        dto.setTipo(categoria.getTipo());
        dto.setId(categoria.getID());
       // dto.setSubCategoria(categoria.getSubCategoria().stream().map(CategoriaNestedDTO::toDTO).toList());

        if (categoria.getCategoriaPadre() != null) {
            dto.setCategoriaPadre(CategoriaNestedDTO.toDTO(categoria.getCategoriaPadre()));
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
            categoria.setCategoriaPadre(dto.getCategoriaPadre().toEntity());
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

@Getter
@Setter
class CategoriaNestedDTO {
    private Long id;
    private String nombre;

    public static CategoriaNestedDTO toDTO(Categoria categoriaPadre) {
        CategoriaNestedDTO dto = new CategoriaNestedDTO();
        dto.setId(categoriaPadre.getID());
        dto.setNombre(categoriaPadre.getNombre());
        return dto;
    }

    public Categoria toEntity() {
        Categoria categoria = new Categoria();
        categoria.setID(this.id);
        categoria.setNombre(this.nombre);
        return categoria;
    }
}
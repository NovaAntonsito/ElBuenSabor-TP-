package com.example.demo.Controllers.DTOS;

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
    private String nombre;
    private Baja_Alta estado;
    private Long categoriaPadre;
}

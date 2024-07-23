package com.example.demo.Controllers.DTOS;

import com.example.demo.Entitys.Insumo;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Slf4j
public class ProductoInsumosDTO {
    private Long id;
    private String nombre;
    private Double cantidad;
}

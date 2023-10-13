package com.example.demo.Controllers.DTOS;

import com.example.demo.Entitys.Insumo;
import com.example.demo.Entitys.Producto;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RespuestaCarrito {
    private List<Producto> productosComprados;
    private List<Insumo> productosAdicionales;
}

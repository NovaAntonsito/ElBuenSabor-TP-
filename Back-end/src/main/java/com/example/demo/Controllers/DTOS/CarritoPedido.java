package com.example.demo.Controllers.DTOS;

import com.example.demo.Entitys.Carrito;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarritoPedido {
    private List<ProductosCarritoDTO> productosManufacturados;
    private List<InsumoCarritoDTO> insumosAgregados;
    private Double totalCompra;

}

package com.example.demo.Controllers.DTOS;

import com.example.demo.Entitys.Carrito;
import com.example.demo.Entitys.Producto;
import com.example.demo.Entitys.Usuario;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarritoDTO {
    List<Producto> productosComprados;

    public Carrito toEntity(List<Producto> productosComprados, Usuario usuario) throws Exception{
        Carrito newCart = new Carrito();
        newCart.setProductosComprados(productosComprados);
        newCart.setUsuarioAsignado(usuario);
        return newCart;
    }
}

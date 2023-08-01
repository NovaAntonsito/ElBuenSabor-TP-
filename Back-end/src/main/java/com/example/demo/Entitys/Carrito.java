package com.example.demo.Entitys;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "cart")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Carrito extends Base {

    @ManyToMany
    @JoinTable(name = "carrito_producto",
            joinColumns = @JoinColumn(name = "carrito_id"),
            inverseJoinColumns = @JoinColumn(name = "producto_id"))
    private List<Producto> productosComprados;

    @ManyToMany
    @JoinTable(name="insumosAgregados_carrito")
    private List<Insumo> productosAdicionales;

    @OneToOne
    private Usuario usuarioAsignado;
}

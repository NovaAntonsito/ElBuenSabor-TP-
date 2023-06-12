package com.example.demo.Entitys;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "Cart")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
public class Carrito extends Base {

    @OneToMany(orphanRemoval = true, cascade = CascadeType.ALL)
    List<Producto> productosComprados;

    @OneToOne
    Usuario usuarioAsignado;
}

package com.example.demo.Entitys;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Usuario")
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
public class Usuario extends Base {
    String nombre;
    String apellido;
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_rol_FK")
    Rol roles;
    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "usuario_direccion",
            joinColumns = {@JoinColumn(name = "id_usuario_fk")},
            inverseJoinColumns = {@JoinColumn(name = "id_direccion_fk")})
    List<Direccion> direccionList = new ArrayList<Direccion>();
}

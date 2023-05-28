package com.example.demo.Entitys;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Usuario")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Usuario{
    @Id
    @Column(unique = true,nullable = true)
    private String Auth0ID;






    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "usuario_direccion",
            joinColumns = {@JoinColumn(name = "id_usuario_fk")},
            inverseJoinColumns = {@JoinColumn(name = "id_direccion_fk")})
    private List<Direccion> direccionList = new ArrayList<Direccion>();

}

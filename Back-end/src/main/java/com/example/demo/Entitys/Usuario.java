package com.example.demo.Entitys;

import jakarta.persistence.*;
import lombok.*;
import com.example.demo.Entitys.Rol;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
public class Usuario {
    @Id
    private String id;

    @Column(unique = true)
    private String username;

    private String email;

    private String password;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "usuario_direccion",
            joinColumns = {@JoinColumn(name = "id_usuario_fk")},
            inverseJoinColumns = {@JoinColumn(name = "id_direccion_fk")})
    private List<Direccion> direccionList = new ArrayList<Direccion>();

    @Column()
    private Boolean bloqueado;

    @ManyToOne
    @JoinColumn(nullable = true, name = "RolAsignado")
    private Rol rol;

}

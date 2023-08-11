package com.example.demo.Entitys;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import com.example.demo.Entitys.Rol;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
@NoArgsConstructor
@ToString
@AllArgsConstructor
@Setter
@Getter
@JsonIgnoreProperties("hibernateLazyInitializer")
public class Usuario {
    @Id
    private String id;

    @Column(unique = true)
    private String username;
    private String name;
    private String email;
    private String phone_number;
    private String password;


    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "usuario_direccion",
            joinColumns = {@JoinColumn(name = "id_usuario_fk")},
            inverseJoinColumns = {@JoinColumn(name = "id_direccion_fk")})
    private List<Direccion> direccionList = new ArrayList<>();

    @Column()
    private Boolean blocked = false;

    @ManyToOne
    @JoinColumn(nullable = true, name = "RolAsignado")
    private Rol rol;

    private String picture;
    private Boolean email_verified;
    private Integer logins_count;
}

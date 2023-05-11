package com.example.demo.Entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Configuracion")
public class Configuracion extends Base {
    private Long cantCocineros;
    private String emailEmpresa;
    @Column(unique = true,name = "token_secret_MP")
    private String token_MP;
}

package com.example.demo.Entitys;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Configuracion")
public class Configuracion extends Base {
    private Long cantCocineros;
    private Double precioPorTiempo;
    private String emailEmpresa;
    private Double costoEnvio;
    private LocalTime cierreLocal;
    private LocalTime aperturaLocal;
    @Column(unique = true,name = "token_secret_MP")
    private String token_MP;
}

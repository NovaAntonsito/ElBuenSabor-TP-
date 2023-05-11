package com.example.demo.Entitys;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Direccion")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Direccion extends Base {
    private String calleNombre;
    private String departamento;
    private Long numeracion;
    private String aclaracion;
    private Long nroPiso;

}

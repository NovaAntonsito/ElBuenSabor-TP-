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
    String calleNombre;
    String departamento;
    Long numeracion;
    String aclaracion;
    Long nroPiso;

}

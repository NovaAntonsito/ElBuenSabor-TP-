package com.example.demo.Entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "Clientes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clients extends Base {
    private String nombre;
    private int edad;
    private int DNI;

}

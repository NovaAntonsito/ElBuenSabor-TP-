package com.example.demo.Entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Domicilio extends Base{
    private String calle;
    private int numero;
    private String localidad;

    @OneToOne
    @JoinColumn(name = "cliente_id")
    private Cliente cliente;
}

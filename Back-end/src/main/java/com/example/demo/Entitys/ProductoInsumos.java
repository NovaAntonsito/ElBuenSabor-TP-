package com.example.demo.Entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "productoInsumo")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ProductoInsumos extends Base {

    @ManyToOne
    @JoinColumn(name = "insumo_id")
    private Insumo insumo;

    private Double cantidad;

}

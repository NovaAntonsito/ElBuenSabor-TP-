package com.example.demo.Entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "CartItem")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class CarritoProductos extends Base {

    private String producto;

    private Long cantidad;

    private Double precioTotal;

}

package com.example.demo.Entitys;

import com.example.demo.Entitys.Enum.Baja_Alta;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;

@Entity
@Table(name = "Producto_Manufacturado")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Producto extends Base{
    String nombre;
    @Column(name = "url_Imagen",unique = true)
    String imagen;
    String descripcion;
    Long tiempoCocina;
    String receta;
    Baja_Alta alta;

}

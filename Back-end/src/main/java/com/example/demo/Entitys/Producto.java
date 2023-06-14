package com.example.demo.Entitys;

import com.example.demo.Entitys.Enum.Baja_Alta;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;


@Entity
@Table(name = "Producto_Manufacturado")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Producto extends Base{
    private String nombre;
    private String imgURL;
    private String descripcion;
    private Long tiempoCocina;
    private String receta;
    private Baja_Alta alta;
    @ManyToOne
    @JsonIgnoreProperties({"subCategoria","hibernateLazyInitializer"})
    private Categoria productoCategoria;
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "producto_insumo",
            joinColumns = @JoinColumn(name = "producto_id"),
            inverseJoinColumns = @JoinColumn(name = "insumo_id"))
    private List<Insumo> insumoSet;
}

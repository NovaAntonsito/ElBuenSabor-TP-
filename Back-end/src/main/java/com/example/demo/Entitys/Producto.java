package com.example.demo.Entitys;

import com.example.demo.Entitys.Enum.Baja_Alta;

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
    @Lob
    @Column(name = "imagen_Blob", columnDefinition = "MEDIUMBLOB")
    private byte[] imagenBlob;
    private String descripcion;
    private Long tiempoCocina;
    private String receta;
    private Baja_Alta alta;
    @ManyToOne
    private Categoria productoCategoria;
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "producto_insumo",
            joinColumns = @JoinColumn(),
            inverseJoinColumns = @JoinColumn())
    private List<Insumo> insumoSet;
}

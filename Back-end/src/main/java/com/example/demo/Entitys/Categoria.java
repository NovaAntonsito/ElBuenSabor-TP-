package com.example.demo.Entitys;

import com.example.demo.Entitys.Enum.Baja_Alta;
import com.example.demo.Entitys.Enum.TipoCategoria;
import com.fasterxml.jackson.annotation.JsonBackReference;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "Categoria")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties({"handler","hibernateLazyInitializer"})
@ToString
public class Categoria extends Base{
    private Baja_Alta estado;

    private String nombre;

    private TipoCategoria tipo;

    @ManyToOne()
    @JoinColumn(name = "categoria_padre")
    @JsonBackReference
    private Categoria categoriaPadre;

    @OneToMany(mappedBy = "categoriaPadre", fetch = FetchType.EAGER)
    @JsonManagedReference
    @Column(nullable = true)
    private List<Categoria> subCategoria;
}

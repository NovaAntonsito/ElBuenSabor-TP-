package com.example.demo.Entitys;

import com.example.demo.Entitys.Enum.Baja_Alta;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@Table(name = "Categoria")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Categoria extends Base{
    Baja_Alta alta = Baja_Alta.ALTA;

    String nombre;

    @ManyToOne()
    @JoinColumn(name = "categoria_padre")
    @JsonBackReference
    Categoria categoriaPadre;

    @JsonIgnoreProperties(value = {"categoriaPadre"}, allowGetters = true)
    @OneToMany(mappedBy = "categoriaPadre", fetch = FetchType.EAGER)
    @JsonManagedReference
    List<Categoria> subCategoria;
}

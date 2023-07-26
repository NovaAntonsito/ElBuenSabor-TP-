package com.example.demo.Entitys;

import com.example.demo.Entitys.Enum.Baja_Alta;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Insumo extends Base{
   private String nombre;
   private Double stockMinimo;
   private Double stockActual;
   private Baja_Alta estado;
   private Double costo;
    @ManyToMany(mappedBy = "insumoSet")
    @JsonIgnore
   private List<Producto> productoSet;

    @ManyToOne(fetch = FetchType.EAGER)
    private Categoria categoria;
}

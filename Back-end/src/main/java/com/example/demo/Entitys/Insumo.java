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
    String nombre;
    Double stockMinimo;
    Double stockActual;
    Baja_Alta estado;
    Double costo;
    @ManyToMany(mappedBy = "insumoSet")
    @JsonIgnore
    List<Producto> productoSet;

}

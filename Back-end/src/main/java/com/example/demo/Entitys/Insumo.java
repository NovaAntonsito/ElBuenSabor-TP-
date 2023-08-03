package com.example.demo.Entitys;

import com.example.demo.Entitys.Enum.Baja_Alta;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties("hibernateLazyInitializer")
public class Insumo extends Base{
   private String nombre;
   @Column(name = "stockMinimo")
   private Double stockMinimo;

   @Column(name = "stockActual")
   private Double stockActual;
   private Baja_Alta estado;
   private Double costo;
   @ManyToOne(fetch = FetchType.EAGER)
   private Categoria categoria;
   @Column(name = "esComplemento")
   private Boolean esComplemento;
   @ManyToOne(fetch = FetchType.EAGER)
   private UnidadMedida unidadMedida;

}

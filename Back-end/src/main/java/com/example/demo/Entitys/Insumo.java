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
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@JsonIgnoreProperties("hibernateLazyInitializer")
public class Insumo extends Base {
    private String nombre;
    @Column(name = "stockMinimo")
    private Double stock_minimo;
    private String urlIMG;
    @Column(name = "stockActual")
    private Double stock_actual;
    private Baja_Alta estado;
    private Double costo;
    @ManyToOne(fetch = FetchType.EAGER)
    private Categoria categoria;
    private Boolean es_complemento;
    @ManyToOne(fetch = FetchType.EAGER)
    private UnidadMedida unidad_medida;


}

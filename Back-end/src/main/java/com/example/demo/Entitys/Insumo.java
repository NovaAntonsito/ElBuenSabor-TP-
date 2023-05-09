package com.example.demo.Entitys;

import com.example.demo.Entitys.Enum.Baja_Alta;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Insumo extends Base{
    String nombre;
    @Column(name = "imagen_URL")
    String imagen;
    Double stockMinimo;
    Double stockActual;
    Baja_Alta alta;
    Double costo;
    //TODO Relacion entre tabla entre producto insumo
}

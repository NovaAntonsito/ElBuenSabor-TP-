package com.example.demo.Entitys;

import com.example.demo.Entitys.Enum.EstadoMP;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

import java.util.Date;

@Entity
@Table(name = "Mercado_Pago_Datos")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class MP_Datos extends Base {
    Date fecha_Creacion;
    Date fecha_Aprobacion;
    String forma_Pago;
    String metodo_Pago;
    Long nroTarjeta;
    EstadoMP estado;
}

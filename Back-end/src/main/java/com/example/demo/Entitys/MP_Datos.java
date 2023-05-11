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
    private Date fecha_Creacion;
    private Date fecha_Aprobacion;
    private String forma_Pago;
    private String metodo_Pago;
    private Long nroTarjeta;
    private EstadoMP estado;
}

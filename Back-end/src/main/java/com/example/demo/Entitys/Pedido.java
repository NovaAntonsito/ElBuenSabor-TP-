package com.example.demo.Entitys;

import com.example.demo.Entitys.Enum.Estado;
import com.example.demo.Entitys.Enum.tipoEnvio;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Pedido extends Base{
    private Date fecha;
    private int numero;
    private Estado estado;
    //TODO Esto tiene que cambiar a DateTime(Investigo y lo implemento)
    private Double tiempoEstimado;
    private tipoEnvio tipoEnvio;
    private Double total;

    @OneToOne
    @JoinColumn(name = "domicilio_a_enviar_id")
    private Domicilio domicilioAEnviar;

}

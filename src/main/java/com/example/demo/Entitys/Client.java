package com.example.demo.Entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "Clientes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Client extends Base {
    private String nombre;
    private String apelido;
    private Long telefono;
    private String email;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;

}

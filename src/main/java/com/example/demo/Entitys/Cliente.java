package com.example.demo.Entitys;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "Clientes")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cliente extends Base {
    private String nombre;
    private String apelido;
    private Long telefono;
    @Column(unique = true)
    private String email;

    @OneToOne
    @JoinColumn(name = "usuario_id")
    private User usuario;

}

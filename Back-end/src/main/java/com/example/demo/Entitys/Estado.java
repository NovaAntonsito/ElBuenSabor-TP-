package com.example.demo.Entitys;

import com.example.demo.Entitys.Enum.EstadoPedido;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;

@Entity
@Table(name = "Estado")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Estado extends Base {
    EstadoPedido estado;
}

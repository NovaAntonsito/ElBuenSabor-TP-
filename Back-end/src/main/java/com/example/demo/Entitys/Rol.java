package com.example.demo.Entitys;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.*;



@Entity()
@Table(name = "Roles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Rol extends Base{

   private String nombreRol;

}

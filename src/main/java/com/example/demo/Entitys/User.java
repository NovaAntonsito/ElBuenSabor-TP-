package com.example.demo.Entitys;


import com.example.demo.Entitys.Enum.Roles;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Usuarios")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User extends Base {
    private String username;
    private String clave;
    private Roles roles;
}

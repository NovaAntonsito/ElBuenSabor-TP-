package com.example.demo.Controllers.Auth0.Auth0Classes;

import com.example.demo.Entitys.Direccion;
import com.example.demo.Entitys.Usuario;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Slf4j
public class Auth0DTO {
    private String id;

    private String username;

    private String email;

    private List<Direccion> direccionList;

    private Boolean bloqueado;

    private String picture;

    private Boolean email_verified;

    private Integer logins_count;
    public Auth0DTO toDTO(Usuario user){
        Auth0DTO newUser = new Auth0DTO();
        newUser.setId(user.getId());
        newUser.setUsername(user.getUsername());
        newUser.setBloqueado(user.getBlocked());
        newUser.setEmail(user.getEmail());
        newUser.setDireccionList(user.getDireccionList());
        newUser.setPicture(user.getPicture());
        newUser.setEmail_verified(user.getEmail_verified());
        newUser.setLogins_count(user.getLogins_count());

        return newUser;
    }
}

package com.example.demo.Controllers.Auth0;

import com.example.demo.Controllers.Auth0.Auth0Classes.Auth0DTO;
import com.example.demo.Entitys.Direccion;
import com.example.demo.Entitys.Usuario;
import com.example.demo.Services.DireccionService;
import com.example.demo.Services.UserService;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.http.exceptions.UnirestException;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.mashape.unirest.http.HttpResponse;

import java.text.ParseException;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping(path = "api/v1/users", produces = MediaType.APPLICATION_JSON_VALUE)
public class Auth0Controller {
    //http://localhost:9000/api/v1/users

    private final UserService userService;
    private final DireccionService direccionService;


    @GetMapping("/getUsers")
    public ResponseEntity<?> getUsersFromAuth0(@PageableDefault(value = 10, page = 0) Pageable page) throws Exception {
        try {
            Page<Usuario> allUsuarios = userService.viewAllUsuarios(page);
            Page<Auth0DTO> allUsuariosDTO = allUsuarios.map(usuario -> {
                Auth0DTO usuarioDTO = new Auth0DTO();
                return usuarioDTO.toDTO(usuario);
            });
            return ResponseEntity.status(HttpStatus.OK).body(allUsuariosDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }

    }


    @GetMapping("/search")
    public ResponseEntity<?> searchUsuarios(@PageableDefault(value = 10, page = 0) Pageable page,@RequestParam("username")String username) throws Exception{
        try {
            Page<Usuario> usuarioPage = userService.filterUsuarios(username,page);
            return ResponseEntity.status(HttpStatus.OK).body(usuarioPage);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }

    }
    @GetMapping("/{username}")
    public ResponseEntity<?> getOneUsuario(@PathVariable("username") String username)throws Exception{
        try {
            username = username.replace('_', '|');
            Usuario userFound = userService.userbyID(username);
            Auth0DTO userProccesed = new Auth0DTO();
            return ResponseEntity.status(HttpStatus.OK).body(userProccesed.toDTO(userFound));
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }

    }

    @PutMapping("/addDireccion/{id}")
    public ResponseEntity<?> addDireccionToUser(@RequestBody Direccion direccion,@PathVariable("id") String userSub) throws Exception{
        try {
            userSub = userSub.replace('_', '|');
            Direccion newDireccion = direccionService.saveDireccion(direccion);
            Usuario userFound = userService.addAddressToUser(userSub, newDireccion);
            return ResponseEntity.status(HttpStatus.OK).body(userFound);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
}

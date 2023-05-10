package com.example.demo.Controllers;

import com.example.demo.Entitys.Usuario;
import com.example.demo.Services.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("api/v1/user")
public class UserController {
    private final UserService userService;

    @GetMapping("")
    public ResponseEntity<Page<Usuario>> getAllUsuarios(@PageableDefault(size = 10, page = 0)Pageable page) throws Exception {
        Page<Usuario> listActual = userService.verTodosUsuarios(page);
        if(!listActual.isEmpty()){
            return ResponseEntity.status(HttpStatus.OK).body(userService.verTodosUsuarios(page));
        }else{
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
    }
    @PostMapping("")
    public ResponseEntity<Usuario> crearNuevoUsuario(Usuario newUser) throws Exception{
        return null;
    }
}

package com.example.demo.Controllers;


import com.example.demo.Controllers.DTOS.CarritoDTO;
import com.example.demo.Entitys.Carrito;
import com.example.demo.Entitys.Usuario;
import com.example.demo.Services.CarritoService;
import com.example.demo.Services.UserService;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RequestMapping("v1/api/cart")
@RequiredArgsConstructor
@Slf4j
@RestController
public class CarritoController {
    private final CarritoService carritoService;
    private final UserService userService;

    @PostMapping("")
    public ResponseEntity<?> saveCarrito(@RequestBody CarritoDTO carritoDTO, @RequestHeader("Authorization") String token) throws Exception {
        String jwtToken = token.substring(7);
        try {
            JWTClaimsSet decodedJWT = JWTParser.parse(jwtToken).getJWTClaimsSet();
            String sub = decodedJWT.getSubject();
            Usuario userFound = userService.userbyID(sub);
            Carrito newCart = carritoDTO.toEntity(carritoDTO.getProductosComprados() , userFound);
            carritoService.cartSave(newCart);
            return ResponseEntity.status(HttpStatus.OK).body(newCart);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }


}

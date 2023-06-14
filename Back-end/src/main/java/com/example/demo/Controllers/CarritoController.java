package com.example.demo.Controllers;


import com.example.demo.Controllers.DTOS.CarritoDTO;
import com.example.demo.Controllers.DTOS.ProductosCarritoDTO;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


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
            Carrito carritoCreado = carritoService.cartSave(newCart);
            return ResponseEntity.status(HttpStatus.OK).body(carritoCreado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    @GetMapping("/getCarrito")
    public ResponseEntity<?> getCarritoUser (@RequestHeader("Authorization") String token) throws Exception{
        String jwtToken = token.substring(7);
        ProductosCarritoDTO newDTO = new ProductosCarritoDTO();
        try{
            JWTClaimsSet decodedJWT = JWTParser.parse(jwtToken).getJWTClaimsSet();
            String sub = decodedJWT.getSubject();
            Carrito carritoFound = carritoService.getCarritobyUserID(sub);
            List<ProductosCarritoDTO> dtoList = newDTO.toDTO(carritoFound.getProductosComprados());
            return ResponseEntity.status(HttpStatus.OK).body(dtoList);
        }catch (Exception error){
            String mensajeError = error.getMessage();
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(
                    Map.of("success", false, "msg", mensajeError)
            );
        }
    }
}

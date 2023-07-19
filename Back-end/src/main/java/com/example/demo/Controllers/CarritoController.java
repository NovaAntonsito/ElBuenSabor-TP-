package com.example.demo.Controllers;


import com.example.demo.Controllers.DTOS.CarritoDTO;
import com.example.demo.Controllers.DTOS.ProductosCarritoDTO;
import com.example.demo.Entitys.Carrito;
import com.example.demo.Entitys.Producto;
import com.example.demo.Entitys.Usuario;
import com.example.demo.Services.CarritoService;
import com.example.demo.Services.ConfigLocalService;
import com.example.demo.Services.UserService;
import com.example.demo.Services.ProductoService;

import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
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
    private final ProductoService productoService;
    private final UserService userService;
    private final ConfigLocalService configService;

    @PutMapping("/addProduct/{productoId}")
    public ResponseEntity<?> addProduct(@PathVariable("productoId") Long productoId, @RequestHeader("Authorization") String token) throws Exception {
        String jwtToken = token.substring(7);
        try {
            JWTClaimsSet decodedJWT = JWTParser.parse(jwtToken).getJWTClaimsSet();
            String sub = decodedJWT.getSubject();
            Usuario userFound = userService.userbyID(sub);
            Carrito cart = carritoService.getCarritobyUserID(userFound.getId());
            Producto nuevoProducto = productoService.findbyID(productoId);
            cart.getProductosComprados().add(nuevoProducto);
            carritoService.cartSave(cart);
            ProductosCarritoDTO newDTO = new ProductosCarritoDTO();
            List<ProductosCarritoDTO> dtoList = newDTO.toDTO(cart.getProductosComprados());
            Double precioTotal = 0D;
            Double tiempoEnCocina = 0D;
            for (ProductosCarritoDTO productosCarritoDTO : dtoList) {
                precioTotal += productosCarritoDTO.getPrecioTotal();
                tiempoEnCocina += productoService
                        .findbyID(productosCarritoDTO.getProductoId())
                        .getTiempoCocina();
            }
            precioTotal += configService.getPrecioPorTiempo(tiempoEnCocina);
            CarritoDTO carritoDTO = new CarritoDTO(dtoList,precioTotal);
            return ResponseEntity.status(HttpStatus.OK).body(carritoDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    @PutMapping("/delProduct/{productoId}")
    public ResponseEntity<?> delProduct(@PathVariable("productoId") Long productoId, @RequestHeader("Authorization") String token) throws Exception {
        String jwtToken = token.substring(7);
        try {
            JWTClaimsSet decodedJWT = JWTParser.parse(jwtToken).getJWTClaimsSet();
            String sub = decodedJWT.getSubject();
            Usuario userFound = userService.userbyID(sub);
            Carrito cart = carritoService.getCarritobyUserID(userFound.getId());

            Producto nuevoProducto = productoService.findbyID(productoId);
            cart.getProductosComprados().remove(nuevoProducto);
            carritoService.cartSave(cart);
            ProductosCarritoDTO newDTO = new ProductosCarritoDTO();
            List<ProductosCarritoDTO> dtoList = newDTO.toDTO(cart.getProductosComprados());
            Double precioTotal = 0D;
            for (ProductosCarritoDTO productosCarritoDTO : dtoList) {
                precioTotal += productosCarritoDTO.getPrecioTotal();
            }
            CarritoDTO carritoDTO = new CarritoDTO(dtoList,precioTotal);

            return ResponseEntity.status(HttpStatus.OK).body(carritoDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    @PutMapping("/clearCart")

    public ResponseEntity<?> clearCart(@RequestHeader("Authorization") String token) throws Exception {
        String jwtToken = token.substring(7);
        try {
            JWTClaimsSet decodedJWT = JWTParser.parse(jwtToken).getJWTClaimsSet();
            String sub = decodedJWT.getSubject();
            Usuario userFound = userService.userbyID(sub);
            Carrito cart = carritoService.getCarritobyUserID(userFound.getId());
            cart.getProductosComprados().clear();
            carritoService.cartSave(cart);
            ProductosCarritoDTO newDTO = new ProductosCarritoDTO();
            List<ProductosCarritoDTO> dtoList = new ArrayList<ProductosCarritoDTO>();
            Double precioTotal = 0D;
            CarritoDTO carritoDTO = new CarritoDTO(dtoList,precioTotal);
            return ResponseEntity.status(HttpStatus.OK).body(carritoDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @GetMapping("/getCarrito")
    public ResponseEntity<?> getCarritoUser (@RequestHeader("Authorization") String token) throws Exception{
        String jwtToken = "";
        if (token.length() > 7) jwtToken = token.substring(7);
        ProductosCarritoDTO newDTO = new ProductosCarritoDTO();
        try{
            JWTClaimsSet decodedJWT = JWTParser.parse(jwtToken).getJWTClaimsSet();
            String sub = decodedJWT.getSubject();
            Carrito carritoFound = carritoService.getCarritobyUserID(sub);
            if (carritoFound == null){
                carritoFound = new Carrito();
                List<Producto> productos = new ArrayList<>();
                carritoFound.setProductosComprados(productos);
                carritoFound.setUsuarioAsignado(userService.userbyID(sub));
                carritoService.cartSave(carritoFound);
                List<ProductosCarritoDTO> dtoList = new ArrayList<ProductosCarritoDTO>();
                Double precioTotal = 0D;
                Double tiempoEnCocina = 0D;
                for (ProductosCarritoDTO productosCarritoDTO : dtoList) {
                    precioTotal += productosCarritoDTO.getPrecioTotal();
                    tiempoEnCocina += productoService
                            .findbyID(productosCarritoDTO.getProductoId())
                            .getTiempoCocina();
                }
                precioTotal += configService.getPrecioPorTiempo(tiempoEnCocina);
                CarritoDTO carritoDTO = new CarritoDTO();
                carritoDTO.setTotalCompra(precioTotal);
                return ResponseEntity.status(HttpStatus.OK).body(carritoDTO);
            }else{
                List<ProductosCarritoDTO> dtoList = newDTO.toDTO(carritoFound.getProductosComprados());
                Double precioTotal = 0D;
                Double tiempoEnCocina = 0D;
                for (ProductosCarritoDTO productosCarritoDTO : dtoList) {
                    precioTotal += productosCarritoDTO.getPrecioTotal();
                    tiempoEnCocina += productoService
                            .findbyID(productosCarritoDTO.getProductoId())
                            .getTiempoCocina();
                }
                log.info(tiempoEnCocina.toString());
                precioTotal += configService.getPrecioPorTiempo(tiempoEnCocina);
                CarritoDTO carritoDTO = new CarritoDTO(dtoList,precioTotal);
                return ResponseEntity.status(HttpStatus.OK).body(carritoDTO);
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
}

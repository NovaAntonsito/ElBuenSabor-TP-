package com.example.demo.Controllers;


import com.example.demo.Controllers.DTOS.*;
import com.example.demo.Entitys.Carrito;
import com.example.demo.Entitys.Insumo;
import com.example.demo.Entitys.Producto;
import com.example.demo.Entitys.Usuario;
import com.example.demo.Services.*;

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
    private final InsumoService insumoService;
    private final ProductoService productoService;
    private final UserService userService;
    private final ConfigLocalService configService;

    @PutMapping("/editProduct/{productoId}")
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
            RespuestaCarrito respuestaCarrito =
                    new RespuestaCarrito(cart.getProductosComprados(),
                            cart.getProductosAdicionales());
            return ResponseEntity.status(HttpStatus.OK).body(respuestaCarrito);
//            CarritoDTO _carritoDTO = null;
//            if (carritoDTO != null  ){
//                _carritoDTO = carritoService.editarCarrito(carritoDTO,productoId);
//            }else {
//                _carritoDTO = carritoService.generarCarrito(cart);
//            }

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @PutMapping("/editCompleto/{productoAgregadoID}")
    public ResponseEntity<?> addComplemento(@PathVariable("productoAgregadoID") Long complementoID, @RequestHeader("Authorization")String token) throws Exception{
        String jwtToken = token.substring(7);
        try {
            JWTClaimsSet decodedJWT = JWTParser.parse(jwtToken).getJWTClaimsSet();
            String sub = decodedJWT.getSubject();
            Usuario userFound = userService.userbyID(sub);
            Carrito cart = carritoService.getCarritobyUserID(userFound.getId());
            Insumo complemento = insumoService.findByID(complementoID);
            if (!complemento.getEs_complemento()){
                throw new RuntimeException("No se puede agregar un insumo que no sea un complemento");
            }
            cart.getProductosAdicionales().add(complemento);
            carritoService.cartSave(cart);
            RespuestaCarrito respuestaCarrito =
                    new RespuestaCarrito(cart.getProductosComprados(),
                            cart.getProductosAdicionales());
            return ResponseEntity.status(HttpStatus.OK).body(respuestaCarrito);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
    @DeleteMapping("/delComplemento/{productoAgregadoID}")
    public ResponseEntity<?> delComplemento(@PathVariable("productoAgregadoID") Long complementoID, @RequestHeader("Authorization")String token) throws Exception{
        String jwtToken = token.substring(7);
        try {
            JWTClaimsSet decodedJWT = JWTParser.parse(jwtToken).getJWTClaimsSet();
            String sub = decodedJWT.getSubject();
            Usuario userFound = userService.userbyID(sub);
            Carrito cart = carritoService.getCarritobyUserID(userFound.getId());
            Insumo complemento = insumoService.findByID(complementoID);
            if (!complemento.getEs_complemento()){
                throw new RuntimeException("No se puede agregar un insumo que no sea un complemento");
            }
            cart.getProductosAdicionales().remove(complemento);
            carritoService.cartSave(cart);
            RespuestaCarrito respuestaCarrito =
                    new RespuestaCarrito(cart.getProductosComprados(),
                            cart.getProductosAdicionales());
            return ResponseEntity.status(HttpStatus.OK).body(respuestaCarrito);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @DeleteMapping("/delComplementoCompleto/{productoAgregadoID}")
    public ResponseEntity<?> delComplementoCompleto(@PathVariable("productoAgregadoID") Long complementoID, @RequestHeader("Authorization")String token) throws Exception{
        String jwtToken = token.substring(7);
        try {
            JWTClaimsSet decodedJWT = JWTParser.parse(jwtToken).getJWTClaimsSet();
            String sub = decodedJWT.getSubject();
            Usuario userFound = userService.userbyID(sub);
            Carrito cart = carritoService.getCarritobyUserID(userFound.getId());
            Insumo complemento = insumoService.findByID(complementoID);
            cart.getProductosAdicionales().removeIf(producto -> producto.getID().equals(complementoID));
            carritoService.cartSave(cart);
            RespuestaCarrito respuestaCarrito =
                    new RespuestaCarrito(cart.getProductosComprados(),
                            cart.getProductosAdicionales());
            return ResponseEntity.status(HttpStatus.OK).body(respuestaCarrito);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @DeleteMapping("/editProduct/{productoId}")
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
            RespuestaCarrito respuestaCarrito =
                    new RespuestaCarrito(cart.getProductosComprados(),
                    cart.getProductosAdicionales());
            return ResponseEntity.status(HttpStatus.OK).body(respuestaCarrito);
//            CarritoDTO carritoDTO = carritoService.generarCarrito(cart);
//
//            return ResponseEntity.status(HttpStatus.OK).body(carritoDTO);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @DeleteMapping("/editCompleto/{productoId}")
    public ResponseEntity<?> delProductCompleto(@PathVariable("productoId") Long productoId, @RequestHeader("Authorization") String token) throws Exception {
        String jwtToken = token.substring(7);
        try {
            JWTClaimsSet decodedJWT = JWTParser.parse(jwtToken).getJWTClaimsSet();
            String sub = decodedJWT.getSubject();
            Usuario userFound = userService.userbyID(sub);
            Carrito cart = carritoService.getCarritobyUserID(userFound.getId());

            cart.getProductosComprados().removeIf(producto -> producto.getID().equals(productoId));
            carritoService.cartSave(cart);
            RespuestaCarrito respuestaCarrito =
                    new RespuestaCarrito(cart.getProductosComprados(),
                            cart.getProductosAdicionales());
            return ResponseEntity.status(HttpStatus.OK).body(respuestaCarrito);
//            CarritoDTO carritoDTO = carritoService.generarCarrito(cart);
//
//            return ResponseEntity.status(HttpStatus.OK).body(carritoDTO);
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
            cart.getProductosAdicionales().clear();
            carritoService.cartSave(cart);
            RespuestaCarrito respuestaCarrito =
                    new RespuestaCarrito(cart.getProductosComprados(),
                            cart.getProductosAdicionales());
            return ResponseEntity.status(HttpStatus.OK).body(respuestaCarrito);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @GetMapping("/getCarrito")
    public ResponseEntity<?> getCarritoUser (@RequestHeader("Authorization") String token) throws Exception{
        //String jwtToken = "";
        //if (token.length() > 7) jwtToken = token.substring(7);
        String jwtToken = token.substring(7);
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
                return ResponseEntity.status(HttpStatus.OK).body(carritoFound);
//                List<ProductosCarritoDTO> dtoList = new ArrayList<ProductosCarritoDTO>();
//
//                CarritoDTO carritoDTO = new CarritoDTO();
//                carritoDTO.setTotalCompra(0D);
            }else{
//                CarritoDTO carritoDTO = carritoService.generarCarrito(carritoFound);
                RespuestaCarrito respuestaCarrito = new RespuestaCarrito(carritoFound.getProductosComprados(), carritoFound.getProductosAdicionales());
                return ResponseEntity.status(HttpStatus.OK).body(respuestaCarrito);

            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }


}

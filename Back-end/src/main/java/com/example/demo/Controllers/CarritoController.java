package com.example.demo.Controllers;


import com.example.demo.Controllers.DTOS.CarritoDTO;
import com.example.demo.Controllers.DTOS.ProductosCarritoDTO;
import com.example.demo.Entitys.Carrito;
import com.example.demo.Entitys.Producto;
import com.example.demo.Entitys.Usuario;
import com.example.demo.Services.CarritoService;
import com.example.demo.Services.UserService;
import com.example.demo.Services.ProductoService;

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
    private final ProductoService productoService;
    private final UserService userService;

    /*@PostMapping("")
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
    */
    @PutMapping("/addProducto/{productoId}")
    public ResponseEntity<?> addProducto(@PathVariable("productoId") Long productoId, @RequestHeader("Authorization") String token) throws Exception {
        String jwtToken = token.substring(7);
        try {
            JWTClaimsSet decodedJWT = JWTParser.parse(jwtToken).getJWTClaimsSet();
            String sub = decodedJWT.getSubject();
            Usuario userFound = userService.userbyID(sub);
            Carrito cart = carritoService.getCarritobyUserID(userFound.getId());
            List<Producto> listaProductos = cart.getProductosComprados();
            Producto nuevoProducto = productoService.findbyID(productoId);
            listaProductos.add(nuevoProducto);
            cart.setProductosComprados(listaProductos);
            carritoService.cartSave(cart);
            ProductosCarritoDTO newDTO = new ProductosCarritoDTO();
            List<ProductosCarritoDTO> dtoList = newDTO.toDTO(cart.getProductosComprados());
            int precioTotal = 0;
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
    @GetMapping("/getCarrito")
    public ResponseEntity<?> getCarritoUser (@RequestHeader("Authorization") String token) throws Exception{
        String jwtToken = token.substring(7);
        ProductosCarritoDTO newDTO = new ProductosCarritoDTO();
        try{
            JWTClaimsSet decodedJWT = JWTParser.parse(jwtToken).getJWTClaimsSet();
            String sub = decodedJWT.getSubject();
            Carrito carritoFound = carritoService.getCarritobyUserID(sub);
            if (carritoFound == null){
                carritoFound = new Carrito();
                carritoFound.setUsuarioAsignado(userService.userbyID(sub));
                List<ProductosCarritoDTO> dtoList = new ArrayList<ProductosCarritoDTO>();
                int precioTotal = 0;
                carritoService.cartSave(carritoFound);
                CarritoDTO carritoDTO = new CarritoDTO(dtoList,precioTotal);
                return ResponseEntity.status(HttpStatus.OK).body(carritoDTO);
            }
            List<ProductosCarritoDTO> dtoList = newDTO.toDTO(carritoFound.getProductosComprados());
            int precioTotal = 0;
            for (ProductosCarritoDTO productosCarritoDTO : dtoList) {
                precioTotal += productosCarritoDTO.getPrecioTotal();
            }
            CarritoDTO carritoDTO = new CarritoDTO(dtoList,precioTotal);
            return ResponseEntity.status(HttpStatus.OK).body(carritoDTO);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
}

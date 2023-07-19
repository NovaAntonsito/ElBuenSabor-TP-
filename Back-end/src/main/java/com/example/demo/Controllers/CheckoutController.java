package com.example.demo.Controllers;

import com.example.demo.Controllers.DTOS.ProductosCarritoDTO;
import com.example.demo.Entitys.Carrito;
import com.example.demo.Entitys.Pedido;
import com.example.demo.Entitys.Producto;
import com.example.demo.Entitys.Usuario;
import com.example.demo.Services.CarritoService;
import com.example.demo.Services.ProductoService;
import com.example.demo.Services.UserService;
import com.mercadopago.client.preference.PreferenceClient;
import com.mercadopago.client.preference.PreferenceItemRequest;
import com.mercadopago.client.preference.PreferenceRequest;
import com.mercadopago.exceptions.MPException;
import com.mercadopago.resources.preference.Preference;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/checkout")
@RequiredArgsConstructor
@Slf4j
public class CheckoutController {

    private final UserService userService;
    private final CarritoService carritoService;
    private final ProductoService productoService;



    @SneakyThrows
    @PostMapping()
    public ResponseEntity<?> pagarPedido (@RequestHeader("Authorization") String token) throws MPException{
        String jwtToken = token.substring(7);
        try {
            JWTClaimsSet decodedJWT = JWTParser.parse(jwtToken).getJWTClaimsSet();
            String sub = decodedJWT.getSubject();
            Usuario userFound = userService.userbyID(sub);
            Carrito carritoFound = carritoService.getCarritobyUserID(sub);
            List<PreferenceItemRequest> productosPorComprar = new ArrayList<>();
            ProductosCarritoDTO newDTO = new ProductosCarritoDTO();
            List<ProductosCarritoDTO> dtoList = newDTO.toDTO(carritoFound.getProductosComprados());
            int precioTotal = 0;
            for (ProductosCarritoDTO productosCarritoDTO : dtoList) {
                precioTotal += productosCarritoDTO.getPrecioTotal();
            }
            for (ProductosCarritoDTO productosCarritoDTO: dtoList) {
                PreferenceItemRequest itemRequest = PreferenceItemRequest.builder()
                        .id(productosCarritoDTO.getProductoId().toString())
                        .title(productosCarritoDTO.getProducto())
                        .description(productoService.findbyID(productosCarritoDTO.getProductoId()).getDescripcion())
                        .pictureUrl(productoService.findbyID(productosCarritoDTO.getProductoId()).getImgURL())
                        .categoryId(productoService.findbyID(productosCarritoDTO.getProductoId()).getProductoCategoria().getNombre())
                        .quantity(Math.toIntExact(productosCarritoDTO.getCantidad()))
                        .currencyId("ARS")
                        .unitPrice(BigDecimal.valueOf(precioTotal))
                        .build();
                productosPorComprar.add(itemRequest);
            }
            PreferenceRequest preferenceRequest = PreferenceRequest.builder()
                    .items(productosPorComprar).build();
            PreferenceClient client = new PreferenceClient();
            Preference preference = client.create(preferenceRequest);
            return ResponseEntity.status(HttpStatus.OK).body(preference.getExternalReference());
        }catch (MPException e){
            log.info("Lo impensable paso "+ e.getMessage());
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Algo muy malo paso LPM");
        }

    }
}

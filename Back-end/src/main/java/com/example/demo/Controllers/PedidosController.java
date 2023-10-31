package com.example.demo.Controllers;

import com.example.demo.Controllers.DTOS.CarritoDTO;
import com.example.demo.Controllers.DTOS.PedidoDTO;
import com.example.demo.Controllers.DTOS.ProductoDTO;
import com.example.demo.Entitys.*;
import com.example.demo.Services.CarritoService;
import com.example.demo.Services.PedidoService;
import com.example.demo.Services.UserService;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("v1/api/pedidos")
@RequiredArgsConstructor
@Slf4j
public class PedidosController {
    private final PedidoService pedidoService;
    private final UserService userService;
    private final CarritoService carritoService;

    @GetMapping("")
    public ResponseEntity<?> getAllPedidos (@PageableDefault(page = 0, size = 10) Pageable page) throws Exception{
        try{
            Page<Pedido> allPedidos = pedidoService.getAllPedidos(page);
            return ResponseEntity.status(HttpStatus.OK).body(allPedidos);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }



    @GetMapping("/getPedido/{id}")
    public ResponseEntity<?> getOnePedido (@PathVariable("id") Long ID) throws Exception{
        try {
            Pedido p = pedidoService.getPedido(ID);
            PedidoDTO pedidoDTO = new PedidoDTO();
            Carrito carrito = new Carrito();
            carrito.setProductosComprados(p.getProductosManufacturados());
            carrito.setProductosAdicionales(p.getProductosAdicionales());
            pedidoDTO.setCarritoDTO(carritoService.generarCarrito(carrito));
            pedidoDTO.setDireccionPedido(p.getDireccionPedido());
            pedidoDTO.setEstado(p.getEstado());
            pedidoDTO.setEsDelivery(p.getEsDelivery());
            pedidoDTO.setEsMercadoPago(p.getEsMercadoPago());
            pedidoDTO.setDescuentoAplicado(p.getDescuentoTotal());
            pedidoDTO.setCosteEnvio(p.getCostoEnvio());
            pedidoDTO.setTotal(p.getTotal());
            pedidoDTO.setFechaInicio(p.getFechaInicio());
            pedidoDTO.setPedidoID(p.getID());
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(pedidoDTO);
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @GetMapping("/getUserPedidos")
    public ResponseEntity<?> getUserPedidos (@RequestHeader("Authorization") String token) throws Exception{
        String jwtToken = token.substring(7);
        try {
            JWTClaimsSet decodedJWT = JWTParser.parse(jwtToken).getJWTClaimsSet();
            String sub = decodedJWT.getSubject();
            Usuario userFound = userService.userbyID(sub);
            List<PedidoDTO> pedidoDTOList = new ArrayList<>();
            for (Pedido p:  pedidoService.getPedidosUsuario(userFound.getId())) {
                PedidoDTO pedidoDTO = new PedidoDTO();
                pedidoDTO.setEstado(p.getEstado());
                pedidoDTO.setTotal(p.getTotal());
                pedidoDTO.setFechaInicio(p.getFechaInicio());
                pedidoDTO.setPedidoID(p.getID());
                pedidoDTOList.add(pedidoDTO);
            }


            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(pedidoDTOList);
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

}

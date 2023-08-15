package com.example.demo.Controllers;

import com.example.demo.Controllers.DTOS.PedidoDTO;
import com.example.demo.Entitys.Carrito;
import com.example.demo.Entitys.Pedido;
import com.example.demo.Entitys.Usuario;
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

import java.util.Map;

@RestController
@RequestMapping("v1/api/pedidos")
@RequiredArgsConstructor
@Slf4j
public class PedidosController {
    private final PedidoService pedidoService;
    private final UserService userService;

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
    @GetMapping("/getPedido")
    public ResponseEntity<?> getOnePedido (@RequestHeader("Authorization") String token) throws Exception{
        String jwtToken = token.substring(7);
        try {
            JWTClaimsSet decodedJWT = JWTParser.parse(jwtToken).getJWTClaimsSet();
            String sub = decodedJWT.getSubject();
            Usuario userFound = userService.userbyID(sub);
            return ResponseEntity
                    .status(HttpStatus.OK)
                    .body(pedidoService.getPedidoByUsuario(userFound.getId()));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
}

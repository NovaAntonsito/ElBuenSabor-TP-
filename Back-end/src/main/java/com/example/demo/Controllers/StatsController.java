package com.example.demo.Controllers;

import com.example.demo.Services.PedidoService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/stats")
public class StatsController {

    private final PedidoService pedidoService;

    @GetMapping("/perMonth")
    public ResponseEntity<?> getPedidosPorMes() throws Exception{
        try {
            return ResponseEntity.status(HttpStatus.OK).body(pedidoService.cantidadPedidosporMes());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of(
                    "success" , false,
                    "message" , e.getMessage()
            ));
        }
    }

    @GetMapping("/pedidosUntil")
    public ResponseEntity<?> getPedidosEntreMeses(@RequestParam("fechaInicial")Date fechaInicial, @RequestParam("fechaFinal")Date fechaFinal) throws Exception{
        try{
            return ResponseEntity.status(HttpStatus.OK).body(pedidoService.cuentaPedidos(fechaInicial, fechaFinal));
        }catch (Exception e){
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(Map.of(
                            "success", false,
                            "message", e.getMessage()
                    ));
        }
    }
}

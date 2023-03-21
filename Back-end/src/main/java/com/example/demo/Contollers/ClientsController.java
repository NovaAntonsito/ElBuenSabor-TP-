package com.example.demo.Contollers;

import com.example.demo.Entitys.Cliente;
import com.example.demo.Services.ClientServicesImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin(origins = "*")
@RequestMapping(path = "api/v1/clients")
public class ClientsController extends BaseControllerImpl<Cliente,ClientServicesImpl>{

    private final ClientServicesImpl clientServicesImpl;

    public ClientsController(ClientServicesImpl clientServicesImpl) {
        this.clientServicesImpl = clientServicesImpl;
    }

    @GetMapping("/searchC")
    public ResponseEntity<?> searchArtista(@RequestParam(required = false) String name) {
        try {
            if(clientServicesImpl.searchClientes(name).isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Ups, no existe ese cliente");
            }else {
                return ResponseEntity.status(HttpStatus.OK).body(clientServicesImpl.searchClientes(name));
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("{\"error\": \"" + e.getMessage() + "\"}");
        }
    }
}

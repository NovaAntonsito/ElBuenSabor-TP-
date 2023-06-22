package com.example.demo.Controllers;

import com.example.demo.Entitys.Direccion;
import com.example.demo.Entitys.Usuario;
import com.example.demo.Services.DireccionService;
import com.example.demo.Services.UserService;
import com.nimbusds.jwt.JWTClaimsSet;
import com.nimbusds.jwt.JWTParser;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/direcciones")
@CrossOrigin(origins = "*")
public class DireccionesController {
    private final UserService userService;
    private final DireccionService direccionService;
    @PostMapping("/addDireccion")
    public ResponseEntity<?> addDireccionToUser(@RequestBody Direccion direccion, @RequestHeader("Authorization") String token) throws Exception{
        String jwtToken = token.substring(7);
        try {

            JWTClaimsSet decodedJWT = JWTParser.parse(jwtToken).getJWTClaimsSet();
            String sub = decodedJWT.getSubject();
            System.out.println(direccion);
            Direccion newDireccion = direccionService.saveDireccion(direccion);
            Usuario userFound = userService.addAddressToUser(sub,newDireccion);
            return ResponseEntity.status(HttpStatus.OK).body(userFound.getDireccionList());
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }

    @GetMapping("/getDirecciones")
    public ResponseEntity<?> getUserDirecctions(@RequestHeader("Authorization") String token) throws Exception{
        String jwtToken = token.substring(7);
        try {

            JWTClaimsSet decodedJWT = JWTParser.parse(jwtToken).getJWTClaimsSet();
            String sub = decodedJWT.getSubject();
            Usuario userFound = userService.userbyID(sub);
            List<Direccion> direccionList;
            if (userFound != null){
                direccionList  = userFound.getDireccionList();
            }else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                        .body(Map.of("success", false,"message" , "No se encontro un usuario"));
            }

            return ResponseEntity.status(HttpStatus.OK).body(direccionList);
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("success", false, "message", e.getMessage()));
        }
    }
}

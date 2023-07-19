package com.example.demo.Controllers;

import com.example.demo.Entitys.Configuracion;
import com.example.demo.Services.ConfigLocalService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/api/config")
public class ConfigController {
    private final ConfigLocalService configLocalService;

    @PostMapping("/saveConfig")
    public ResponseEntity<?> saveConfiguration(@RequestBody Configuracion configuracion) throws Exception {
        try {
            configLocalService.SaveConfig(configuracion);
            return ResponseEntity.status(HttpStatus.OK).body(Map.of("success", true, "message", "La configuracion fue guardada"));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Map.of("success", false, "message", ex.getMessage()));
        }
    }

}

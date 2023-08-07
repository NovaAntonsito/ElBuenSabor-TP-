package com.example.demo.Services;

import com.example.demo.Entitys.Configuracion;
import com.example.demo.Repository.ConfigRepository;
import com.example.demo.Services.Interfaces.ConfigLocalServiceInterface;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;


@Service
@Transactional
@RequiredArgsConstructor
@Slf4j
public class ConfigLocalService implements ConfigLocalServiceInterface {

    private final ConfigRepository configRepository;

    @Override
    public void SaveConfig(Configuracion config) throws Exception {
        Long configuracionCount = configRepository.count();

        if (configuracionCount == 0) {
            configRepository.save(config);
        } else {
            throw new RuntimeException("Ya existe una configuración en la base de datos");
        }
    }

    @Override
    public Double getPrecioPorTiempo(Double tiempoEnCocina) throws Exception {
        Configuracion configuracion = configRepository.findByID(1L);
        Double tiempoEnCocinaPorCocinero = tiempoEnCocina / configuracion.getCantCocineros();
        Double costoPorTiempoEnCocina = tiempoEnCocinaPorCocinero * configuracion.getPrecioPorTiempo();
        return costoPorTiempoEnCocina;
    }
}

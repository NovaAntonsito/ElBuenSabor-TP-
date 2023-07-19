package com.example.demo;

import com.mercadopago.MercadoPagoConfig;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;



@SpringBootApplication()

public class DemoApplication {


    @Value("${MP_Access_Key}")
    private String MPAccessKey;

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(DemoApplication.class);
        app.run(args);
    }
    @PostConstruct
    public void init() {
        try {
            MercadoPagoConfig.setAccessToken(MPAccessKey);
        } catch (Exception e) {
            throw new IllegalStateException("Error al configurar el token de acceso de MercadoPago.", e);
        }
    }
}

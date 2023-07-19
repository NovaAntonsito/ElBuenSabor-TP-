package com.example.demo;



import com.mercadopago.MercadoPagoConfig;
import org.springframework.boot.SpringApplication;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;


@SpringBootApplication()

public class DemoApplication {

    public static void main(String[] args) {
        MercadoPagoConfig.setAccessToken("TEST-8272760350171751-071819-25984858eda3a978a6d45725363e7021-715685796");
        SpringApplication.run(DemoApplication.class, args);
    }
}

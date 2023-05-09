package com.example.demo.Controllers;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SwaggerController {

    @RequestMapping("/dev")
    public String getRedirectUrl() {
        return "redirect:swagger-ui.html";
    }
}